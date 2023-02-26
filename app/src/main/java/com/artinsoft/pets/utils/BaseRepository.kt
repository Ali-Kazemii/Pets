package com.artinsoft.pets.utils

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

interface BaseRepository {

    suspend fun <T> networkRequest(
        call: suspend () -> Response<T>,
    ): Flow<NetworkResponse<T>>
}

open class BaseRepositoryDelegation: BaseRepository {

    override suspend fun <T> networkRequest(
        call: suspend () -> Response<T>
    ): Flow<NetworkResponse<T>> = flow{
        try {
            val response = call.invoke()
            when (response.isSuccessful) {
                true -> {
                    if(response.body() != null)
                        emit(NetworkResponse.Success(response.body()!!))
                    else
                        emit(NetworkResponse.Failure(Exception("Data is not valid")))
                }
                false -> emit(NetworkResponse.Failure(Exception(response.message())))
            }
        }catch (e: Exception){
            emit(NetworkResponse.Failure(e))
        }
    }
}