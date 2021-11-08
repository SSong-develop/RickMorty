package com.ssong_develop.rickmorty.network

import com.ssong_develop.rickmorty.vo.Resource
import kotlinx.coroutines.flow.*

abstract class NetworkBoundResource<ResultType, RequestType> {

    private fun loadResource() = flow {
        emit(Resource.loading(null))
        val initialLocalData = fetchFromLocal().first()

        if (shouldFetchFromNetwork(initialLocalData)) {
            emit(Resource.loading(initialLocalData))
            fetchFromNetwork().collect { apiResponse ->
                when (apiResponse) {
                    is ApiSuccessResponse -> {
                        processResponse(apiResponse)
                        apiResponse.body?.let { saveRemoteData(it) }
                        val localData = fetchFromLocal().map { dbData ->
                            Resource.success(dbData)
                        }
                        emitAll(localData)
                    }
                    is ApiEmptyResponse -> {
                        emit(Resource.success(null))
                    }
                    is ApiErrorResponse -> {
                        onFetchFailed(apiResponse.errorMessage, apiResponse.statusCode)
                        emitAll(fetchFromLocal().map { dbData ->
                            Resource.error(
                                apiResponse.errorMessage,
                                dbData
                            )
                        })
                    }
                }
            }
        } else {
            emitAll(fetchFromLocal().map { Resource.success(it) })
        }
    }

    protected open suspend fun onFetchFailed(errorBody: String?, statusCode: Int) {}

    protected open suspend fun processResponse(response: ApiSuccessResponse<RequestType>) {}

    protected abstract suspend fun saveRemoteData(item: RequestType)

    protected abstract suspend fun shouldFetchFromNetwork(data: ResultType?): Boolean

    protected abstract suspend fun fetchFromLocal(): Flow<ResultType>

    protected abstract suspend fun fetchFromNetwork(): Flow<ApiResponse<RequestType>>

    fun asFlow() = loadResource()
}