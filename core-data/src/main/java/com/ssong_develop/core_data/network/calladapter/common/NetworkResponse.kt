package com.ssong_develop.core_data.network.calladapter.common

import java.io.IOException

/**
 * TODO(ERROR Handling)
 */
sealed class NetworkResponse<T> {
    /**
     * Success response with body
     */
    data class ApiSuccessResponse<T>(val body: T) : NetworkResponse<T>()

    /**
     * empty response with body
     */
    data class ApiEmptyResponse<T>(val body: T? = null) : NetworkResponse<T>()

    /**
     * failed response with body
     */
    data class ApiFailureResponse<T>(val errorMessage : String) : NetworkResponse<T>()

    /**
     * Network Error , not good to get Any
     */
    data class NetworkError(val error: IOException) : NetworkResponse<Any>()

    /**
     * For example, json parsing Error... , not good to get Any
     */
    data class UnKnownError(val error: Throwable?) : NetworkResponse<Any>()
}
