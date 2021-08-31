package com.ssong_develop.rickmorty.network

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * @author skydoves
 */

/** transform [Call] to [ApiResponse] via enqueueing response callback. */
fun <T> Call<T>.transform(onResult: (response: ApiResponse<T>) -> Unit) {
    enqueue(object : Callback<T> {
        override fun onResponse(call: Call<T>, response: Response<T>) {
            onResult(ApiResponse.of { response })
        }

        override fun onFailure(call: Call<T>, throwable: Throwable) {
            onResult(ApiResponse.error(throwable))
        }
    })
}

/** gets the [ApiResponse.Failure.Error] message with a error code. */
fun <T> ApiResponse.Failure.Error<T>.message() = "$code : ${responseBody?.string()}"

/** gets the [ApiResponse.Failure.Exception] message. */
fun <T> ApiResponse.Failure.Exception<T>.message() = "$message"