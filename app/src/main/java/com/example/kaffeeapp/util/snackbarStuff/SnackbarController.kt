package com.example.kaffeeapp.util.snackbarStuff

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

data class SnackbsrEvent(
    val message: String,
    val action: SnackbarAction? = null
)

data class SnackbarAction(
    val name: String,
    val action: () -> Unit
)

object SnackbarController {
    private val _events = Channel<SnackbsrEvent>()
    val events = _events.receiveAsFlow()

    suspend fun sendEvent(event: SnackbsrEvent) {
        _events.send(event)
    }
}