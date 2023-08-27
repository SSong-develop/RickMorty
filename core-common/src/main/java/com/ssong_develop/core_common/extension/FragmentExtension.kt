package com.ssong_develop.core_common.extension

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.ssong_develop.core_common.manager.SnackbarMessageManager
import com.ssong_develop.core_common.widget.FadingSnackbar
import kotlinx.coroutines.launch

fun Fragment.setupSnackbarManager(
    snackbarMessageManager: SnackbarMessageManager,
    fadingSnackbar: FadingSnackbar
) {
    viewLifecycleOwner.lifecycleScope.launch {
        viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            snackbarMessageManager.currentSnackbar.collect { message ->
                if (message == null) {
                    return@collect
                }
                fadingSnackbar.show(
                    messageText = message,
                    dismissListener = {
                        snackbarMessageManager.removeMessageAndLoadNext(message)
                    }
                )
            }
        }
    }
}