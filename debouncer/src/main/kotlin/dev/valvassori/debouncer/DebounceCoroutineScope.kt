package dev.valvassori.debouncer

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class DebounceCoroutineScope(
    context: CoroutineDispatcher = Dispatchers.Main
) : CoroutineScope {
    private val job = Job()
    override val coroutineContext = context + job
}