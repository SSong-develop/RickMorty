package com.ssong_develop.core_data.network.calladapter.common

import java.io.IOException

/**
 * TODO(ERROR Handling)
 */
sealed class NetworkResponse<out T: Any> {
    /**
     * Success response with body
     */
    data class Success<T : Any>(val body: T) : NetworkResponse<T>()

    /**
     * Failure response with body
     */
    data class ApiError<T : Any>(val body: T, val code: Int) : NetworkResponse<Nothing>()

    /**
     * Network Error
     */
    data class NetworkError(val error: IOException): NetworkResponse<Nothing>()

    /**
     * For example, json parsing Error...
     */
    data class UnKnownError(val error: Throwable?): NetworkResponse<Nothing>()
}
