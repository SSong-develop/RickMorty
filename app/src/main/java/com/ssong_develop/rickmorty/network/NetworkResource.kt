package com.ssong_develop.rickmorty.network

import com.ssong_develop.rickmorty.vo.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

/**
 * Network
 * Created by SSong-develop on 2021.11.09
 */
abstract class NetworkResource<RequestType> {

    private fun loadResource() = flow {
        emit(Resource.loading(null))
        fetchFromNetwork().collect { apiResponse ->
            when(apiResponse) {
                is ApiSuccessResponse -> {
                    apiResponse.body?.let {
                        emit(Resource.success(it))
                    }
                }
                is ApiErrorResponse -> {
                    onFetchFailed(apiResponse.errorMessage,apiResponse.statusCode)
                    emit(Resource.error(apiResponse.errorMessage,null))
                }
                is ApiEmptyResponse -> {
                    emit(Resource.success(null))
                }
            }
        }
    }

    protected abstract suspend fun fetchFromNetwork(): Flow<ApiResponse<RequestType>>

    protected open suspend fun onFetchFailed(errorBody: String?, statusCode: Int) {}

    fun asFlow() = loadResource()
}