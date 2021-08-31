package com.ssong_develop.rickmorty.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers

/**
 * @author skydoves
 *
 * SSong-develop
 * Coroutine Builder 함수인 launch는 LiveData를 반환하지 않는다.
 * switchMap을 통해서 통신한 값을 LiveData로 반환하고 싶을 때 이와 같은 방법을 사용한다.
 */

abstract class LiveCoroutinesViewModel : ViewModel() {
    inline fun <T> launchOnViewModelScope(crossinline block: suspend () -> LiveData<T>): LiveData<T> {
        return liveData(viewModelScope.coroutineContext + Dispatchers.IO) {
            emitSource(block())
        }
    }
}