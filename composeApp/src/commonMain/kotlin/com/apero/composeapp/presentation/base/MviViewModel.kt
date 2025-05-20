package com.apero.composeapp.presentation.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.onFailure
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow

abstract class MviViewModel<I : MviIntent, S : MviViewState, E : MviSingleEvent> : ViewModel() {
    protected val _viewState = MutableStateFlow(this.initState())
    val viewState: StateFlow<S> = _viewState.asStateFlow()

    protected abstract fun initState(): S
    abstract fun processIntent(intent: I)

    open fun restoreState(state: S) = Unit

    // Send and Receive E
    private val eventChannel = Channel<E>(Channel.UNLIMITED)

    // transform eventChannel to Flow for provides a one-time event stream
    val singleEvent: Flow<E> = eventChannel.receiveAsFlow()

    protected fun sendEvent(event: E) {
        eventChannel
            .trySend(event)
            .onFailure {
//                Timber.d("Failed to send event: $event")
            }.getOrNull()
    }

    override fun onCleared() {
        super.onCleared()
        eventChannel.close()
    }

    open fun resetState() = Unit

}