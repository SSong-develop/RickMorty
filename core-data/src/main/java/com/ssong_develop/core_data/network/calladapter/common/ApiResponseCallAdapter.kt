package com.ssong_develop.core_data.network.calladapter.common

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Converter
import java.lang.reflect.Type

class NetworkResponseAdapter<S : Any>(
    private val responseType: Type
) : CallAdapter<S, Call<NetworkResponse<S>>> {
    override fun responseType(): Type = responseType

    override fun adapt(call: Call<S>): Call<NetworkResponse<S>> {
        return NetworkResponseCall(call)
    }
}