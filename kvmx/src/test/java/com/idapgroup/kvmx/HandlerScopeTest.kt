package com.idapgroup.kvmx

import androidx.lifecycle.errorEvents
import androidx.lifecycle.errorHandlerScope
import app.cash.turbine.test
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class HandlerScopeTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: TestViewModel

    @Before
    fun setup() {
        viewModel = TestViewModel()
    }

    @Test
    fun `job starts`() {
        val job = viewModel.errorHandlerScope.launch { }
        assert(!job.start())
    }

    @Test
    fun `job completes successfully`() = runTest {
        viewModel.successfulTask()
        viewModel.errorEvents.test {
            expectNoEvents()
        }
    }

    @Test
    fun `all errors emitted`() = runTest {
        val times = 100
        repeat(times) {
            viewModel.failingTask()
        }
        viewModel.errorEvents.test {
            repeat(times) {
                assertTrue(awaitItem() is HandlerException)
            }
            cancelAndIgnoreRemainingEvents()
        }
    }
}