package com.idapgroup.kvmx

import androidx.lifecycle.ViewModel
import androidx.lifecycle.errorFlow
import androidx.lifecycle.handlerScope
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.*
import org.junit.Before
import org.junit.Test
import kotlin.random.Random

@ExperimentalCoroutinesApi
class HandlerScopeTest {
    private val dispatcher = UnconfinedTestDispatcher()
    private val viewModel = object : ViewModel() {}

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
    }

    @Test
    fun `job started`() {
        val job = viewModel.handlerScope.launch { }
        assert(!job.start())
    }

    @Test
    fun `synchronous job completes successfully`() = runTest {
        var result = 0
        viewModel.viewModelScope.launch {
            result = 1 + 1
        }
        delay(100)
        assert(result == 2)
    }

    @Test
    fun `asynchronous job completes successfully`() = runTest {
        var result = 0
        viewModel.viewModelScope.launch {
            result = `successful asynchronous task`()
        }
        delay(200)
        assert(result == 1)
    }

    @Test
    fun `error emitted from synchronous job`() = runTest {
        launch {
            delay(100)
            viewModel.handlerScope.launch {
                throw Throwable()
            }
        }
        assert(viewModel.errorFlow.firstOrNull() != null)
    }

    @Test
    fun `error emitted from asynchronous job`() = runTest {
        viewModel.handlerScope.launch {
            `failing asynchronous task`()
        }
        assert(viewModel.errorFlow.firstOrNull() != null)
    }

    @Test
    fun `all errors emitted`() = runTest {
        val times = 10
        repeat(times) {
            viewModel.handlerScope.launch {
                delay(Random.nextLong(100, 300))
                throw Throwable()
            }
        }
        assert(viewModel.errorFlow.take(times).toList().size == times)
    }

    private suspend fun `failing asynchronous task`(): Int {
        delay(100)
        throw Throwable()
    }

    private suspend fun `successful asynchronous task`(): Int {
        delay(100)
        return 1
    }
}