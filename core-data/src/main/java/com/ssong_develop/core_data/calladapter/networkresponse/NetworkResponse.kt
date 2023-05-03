package com.ssong_develop.core_data.calladapter.networkresponse

@Suppress("unused")
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
