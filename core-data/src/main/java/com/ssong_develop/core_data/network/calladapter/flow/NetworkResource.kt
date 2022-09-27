package com.ssong_develop.core_data.network.calladapter.flow

import com.ssong_develop.core_common.Resource
import com.ssong_develop.core_data.ApiEmptyResponse
import com.ssong_develop.core_data.ApiErrorResponse
import com.ssong_develop.core_data.ApiResponse
import com.ssong_develop.core_data.ApiSuccessResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Network without Local Cache
 * Created by SSong-develop on 2021.11.09
 */
abstract class NetworkResource<RequestType> {

    private fun loadResource() = flow {
        emit(Resource.loading(null))
        fetchFromNetwork().collect { apiResponse ->
            when (apiResponse) {
                is ApiSuccessResponse -> {
                    apiResponse.body?.let {
                        emit(Resource.success(it))
                    }
                }
                is ApiErrorResponse -> {
                    onFetchFailed(apiResponse.errorMessage, apiResponse.statusCode)
                    emit(Resource.error(apiResponse.errorMessage, null))
                }
                is ApiEmptyResponse -> {
                    emit(Resource.success(null))
                }
                else -> {}
            }
        }
    }

    protected abstract suspend fun fetchFromNetwork(): Flow<ApiResponse<RequestType>>

    protected open suspend fun onFetchFailed(errorBody: String?, statusCode: Int) {}

    fun asFlow() = loadResource()
}