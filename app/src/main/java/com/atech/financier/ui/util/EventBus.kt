package com.atech.financier.ui.util

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

object EventBus {

    private val _actions = MutableSharedFlow<GlobalAction>(extraBufferCapacity = 1)
    val actions = _actions.asSharedFlow()

    suspend fun invokeAction(action: GlobalAction) = _actions.emit(action)

    sealed interface GlobalAction {
        data object UpdateAccount: GlobalAction
        data object AccountUpdated: GlobalAction
    }

}
