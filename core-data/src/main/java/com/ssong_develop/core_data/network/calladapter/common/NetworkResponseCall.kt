package com.ssong_develop.core_data.network.calladapter.common

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.Request
import okhttp3.ResponseBody
import okio.IOException
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * TODO(ERROR Handling)
 */
internal class NetworkResponseCall<S : Any>(
    private val delegate: Call<S>
) : Call<NetworkResponse<S>> {

    override fun enqueue(callback: Callback<NetworkResponse<S>>) {
        return delegate.enqueue(object : Callback<S> {
            override fun onResponse(call: Call<S>, response: Response<S>) {
                val body = response.body()

                if (response.isSuccessful) {
                    if (body != null) {
                        callback.onResponse(
                            this@NetworkResponseCall,
                            Response.success(NetworkResponse.ApiSuccessResponse(body))
                        )
                    } else {
                        // Response is successful but the body is null
                        callback.onResponse(
                            this@NetworkResponseCall,
                            Response.success(NetworkResponse.ApiEmptyResponse(null))
                        )
                    }
                } else {
                    val message = response.errorBody()?.string()
                    val errorMessage = if (message.isNullOrEmpty()) {
                        response.message()
                    } else {
                        message
                    }
                    callback.onResponse(
                        this@NetworkResponseCall,
                        Response.success(NetworkResponse.ApiFailureResponse(errorMessage))
                    )
                }
            }

            override fun onFailure(call: Call<S>, throwable: Throwable) {
                val networkResponse = when (throwable) {
                    is IOException -> {
                        NetworkResponse.NetworkError(throwable)
                    }
                    else -> {
                        NetworkResponse.UnKnownError(throwable)
                    }
                }
                callback.onResponse(this@NetworkResponseCall, Response.success(networkResponse))
            }
        })
    }

    override fun execute(): Response<NetworkResponse<S>> {
        throw UnsupportedOperationException("NetworkResponseCall doesn't support execute")
    }

    override fun clone(): Call<NetworkResponse<S>> = NetworkResponseCall(delegate.clone())

    override fun isExecuted(): Boolean = delegate.isExecuted

    override fun cancel() = delegate.cancel()

    override fun isCanceled(): Boolean = delegate.isCanceled

    override fun request(): Request = delegate.request()

    override fun timeout(): Timeout = delegate.timeout()
}