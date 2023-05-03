package com.ssong_develop.core_data.calladapter.networkresponse

import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

@Suppress("unused")
class NetworkResponseAdapter<S : Any>(
    private val responseType: Type
) : CallAdapter<S, Call<NetworkResponse<S>>> {
    override fun responseType(): Type = responseType

    override fun adapt(call: Call<S>): Call<NetworkResponse<S>> {
        return NetworkResponseCall(call)
    }
}