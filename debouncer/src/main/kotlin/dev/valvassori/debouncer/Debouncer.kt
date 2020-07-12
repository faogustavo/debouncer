package dev.valvassori.debouncer

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

class Debouncer(
    private val coroutineScope: CoroutineScope = DebounceCoroutineScope()
) {
    private val mutex = Mutex()
    private val hitsChecker = hashMapOf<Any, Int>()

    fun launch(
        delayDuration: Long = 250,
        context: Any = DefaultDebounceContext,
        coroutineContext: CoroutineContext = EmptyCoroutineContext,
        block: suspend () -> Unit
    ) = coroutineScope.launch(coroutineContext) {
        addHit(context)
        delay(delayDuration)
        if (shouldIRun(context)) {
            block()
        }
    }

    private suspend fun shouldIRun(context: Any) = mutex.withLock(owner = context) {
        val hits = hitsChecker.getOrElse(context) { 0 } - 1
        return@withLock when {
            hits <= 0 -> {
                hitsChecker.remove(context)
                true
            }
            else -> {
                hitsChecker[context] = hits
                false
            }
        }
    }

    private suspend fun addHit(context: Any) = mutex.withLock(owner = context) {
        val hits = hitsChecker.getOrElse(context) { 0 } + 1
        hitsChecker[context] = hits
        return@withLock hits
    }
}
