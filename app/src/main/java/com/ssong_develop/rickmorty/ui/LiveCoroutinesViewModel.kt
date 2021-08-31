package com.ssong_develop.rickmorty.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers

/**
 * @author skydoves
 */

abstract class LiveCoroutinesViewModel : ViewModel() {
    inline fun <T> launchOnViewModelScope(crossinline block : suspend () -> LiveData<T>) : LiveData<T> {
        return liveData(viewModelScope.coroutineContext + Dispatchers.IO) {
            emitSource(block())
        }
    }
}