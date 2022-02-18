package androidx.lifecycle

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import java.io.Closeable
import kotlin.coroutines.CoroutineContext

private const val ERROR_FLOW_KEY = "com.idapgroup.kvmx.ERROR_FLOW_KEY"
private const val JOB_KEY = "com.idapgroup.kvmx.JOB_KEY"

private val ViewModel._errorFlow: MutableSharedFlow<Throwable>
    get() = getTag(ERROR_FLOW_KEY) ?: setTagIfAbsent(
        ERROR_FLOW_KEY,
        MutableSharedFlow(extraBufferCapacity = 1)
    )

val ViewModel.errorFlow: SharedFlow<Throwable> get() = _errorFlow

val ViewModel.handlerScope: CoroutineScope
    get() = getTag(JOB_KEY) ?: setTagIfAbsent(
        JOB_KEY,
        ClosableScope(SupervisorJob() + Dispatchers.Main.immediate + CoroutineExceptionHandler { _, error ->
            error.printStackTrace()
            _errorFlow.tryEmit(error)
        })
    )

internal class ClosableScope(context: CoroutineContext) : Closeable, CoroutineScope {
    override val coroutineContext: CoroutineContext = context

    override fun close() {
        coroutineContext.cancel()
    }
}

