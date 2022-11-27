package com.ssong_develop.core_data.network.calladapter.common

import okhttp3.ResponseBody

sealed class NetworkResponse<ResponseBody> {
    /**
     * Success response with body
     */
    data class ApiSuccessResponse<ResponseBody>(val body: ResponseBody) :
        NetworkResponse<ResponseBody>()

    /**
     * empty response with body
     */
    data class ApiEmptyResponse<ResponseBody>(val body: ResponseBody? = null) :
        NetworkResponse<ResponseBody>()

    /**
     * failed response with body
     */
    data class ApiFailureResponse<ResponseBody>(val errorMessage: String) :
        NetworkResponse<ResponseBody>()
}
