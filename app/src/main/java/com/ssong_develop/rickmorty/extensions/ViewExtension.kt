package com.ssong_develop.rickmorty.extensions

import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.google.android.material.snackbar.Snackbar
import com.ssong_develop.rickmorty.utils.Event

fun View.showSnackBar(snackBarText: String, timeLength: Int) {
    Snackbar.make(this, snackBarText, timeLength).show()
}

fun View.setUpSnackBar(
    lifecycleOwner: LifecycleOwner,
    snackBarEvent: MutableLiveData<Event<String>>,
    snackBarMessage: String,
    timeLength: Int
) {
    snackBarEvent.observe(lifecycleOwner) { event ->
        event.getContentIfNotHandled()?.let {
            showSnackBar(snackBarMessage, timeLength)
        }
    }
}