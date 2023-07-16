package com.ssong_develop.core_common.manager

import android.util.Log
import com.ssong_develop.core_common.di.ApplicationScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.LinkedList
import java.util.Queue
import javax.inject.Inject
import javax.inject.Singleton

class SnackbarMessageManager @Inject constructor(
    @ApplicationScope private val coroutineScope: CoroutineScope
) {
    private val messageQueue: MutableList<String> = mutableListOf()

    private val _currentSnackbar = MutableStateFlow<String?>(null)
    val currentSnackbar: StateFlow<String?> = _currentSnackbar

    fun addMessage(msg: String) {
        coroutineScope.launch {
            val sameMessage = messageQueue.find { it == msg }

            if (sameMessage == null) {
                messageQueue.add(msg)
            }

            loadNext()
        }
    }

    private fun loadNext() {
        if (_currentSnackbar.value == null) {
            _currentSnackbar.value = messageQueue.firstOrNull()
        }
    }

    fun removeMessageAndLoadNext(shownMsg: String?) {
        messageQueue.removeAll { it == shownMsg }
        if (_currentSnackbar.value == shownMsg) {
            _currentSnackbar.value = null
        }
        loadNext()
    }

    fun processDismissedMessage() {
        /** no - op **/
    }
}