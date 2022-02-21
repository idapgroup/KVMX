package com.idapgroup.sample

import androidx.lifecycle.ViewModel
import androidx.lifecycle.getTag
import androidx.lifecycle.errorHandlerScope
import androidx.lifecycle.setTagIfAbsent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

private const val TAG_KEY = "tag key"

class MainViewModel : ViewModel() {

    val successEvent = MutableSharedFlow<String>()

    var tag: String?
        get() = getTag(TAG_KEY)
        set(value) { setTagIfAbsent(TAG_KEY, value) }

    fun startSuccessfulTask() = errorHandlerScope.launch {
        successEvent.emit("Data loaded")
    }

    fun startFailingTask() = errorHandlerScope.launch {
        throw AppException("Something got wrong")
    }
}