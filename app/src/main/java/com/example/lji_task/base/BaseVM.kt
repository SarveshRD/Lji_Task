package com.example.lji_task.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

abstract class BaseVM : ViewModel() {

    internal val apiError = MutableSharedFlow<ApiResult<Any>>()
    protected val onApiError: (ApiResult<Any>) -> Unit = { error ->
        viewModelScope.launch {
            apiError.emit(error)
        }
    }

    protected val state = MutableSharedFlow<ApiRenderState>()
    internal fun state(): SharedFlow<ApiRenderState> = state

    fun <T> asyncScope(
        dispatcher: CoroutineDispatcher = IO,
        executable: suspend CoroutineScope.() -> T
    ): Deferred<T> {
        return viewModelScope.async(dispatcher) {
            executable.invoke(this)
        }
    }

    fun <T> scope(
        dispatcher: CoroutineDispatcher = IO,
        executable: suspend CoroutineScope.() -> T
    ): Job {
        return viewModelScope.launch(dispatcher) {
            executable.invoke(this)
        }
    }
}