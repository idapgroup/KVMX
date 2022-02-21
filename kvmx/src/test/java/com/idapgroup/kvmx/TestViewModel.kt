package com.idapgroup.kvmx

import androidx.lifecycle.ViewModel
import androidx.lifecycle.errorHandlerScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TestViewModel : ViewModel() {
    fun successfulTask() = errorHandlerScope.launch {
        delay(300)
    }

    fun failingTask() = errorHandlerScope.launch {
        delay(300)
        throw HandlerException("From failing task")
    }
}