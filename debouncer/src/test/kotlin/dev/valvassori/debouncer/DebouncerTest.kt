package dev.valvassori.debouncer

import kotlinx.coroutines.*
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class DebouncerTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    private val subject = Debouncer(coroutinesTestRule)

    @Test
    fun launch_beforeDelayDuration_doesNotRunIt() = coroutinesTestRule.runBlockingTest {
        var didRun = false

        subject.launch { didRun = true }
        advanceTimeBy(240)

        assert(!didRun)
    }

    @Test
    fun launch_withOneCall_runsIt() = coroutinesTestRule.runBlockingTest {
        var didRun = false

        subject.launch { didRun = true }
        advanceTimeBy(260)

        assert(didRun)
    }

    @Test
    fun launch_withManyCalls_runsOnce() = coroutinesTestRule.runBlockingTest {
        var runCount = 0

        repeat((10..20).toList().random()) {
            subject.launch { runCount += 1 }
            advanceTimeBy(100)
        }

        advanceTimeBy(260)

        assert(runCount == 1)
    }

    @Test
    fun launch_withManyContexts_runsAllInParallel() = coroutinesTestRule.runBlockingTest {
        var runCount = 0

        subject.launch(context = FirstContext) { runCount += 1 }
        advanceTimeBy(100)

        subject.launch(context = SecondContext) { runCount += 1 }

        advanceTimeBy(160)
        assert(runCount == 1) // First launch finishes

        advanceTimeBy(100)
        assert(runCount == 2) // Second launch finishes
    }

    object FirstContext
    object SecondContext
}
