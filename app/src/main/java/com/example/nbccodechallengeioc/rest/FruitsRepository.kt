package com.example.nbccodechallengeioc.rest

import com.example.nbc_injection_sdk.api.getApi
import com.example.nbccodechallengeioc.model.ResultState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface FruitsRepository {
    fun getAllFruits(): Flow<ResultState>
}

class FruitsRepositoryImpl : FruitsRepository {

    private val serviceApi = getApi<RestApi>()?.getService(ServiceApi.BASE_URL)

    override fun getAllFruits(): Flow<ResultState> = flow {
        emit(ResultState.LOADING)

        try {
            val response = serviceApi?.retrieveAllFruits()
            response?.let {
                if (it.isSuccessful) {
                    it.body()?.let { fruits ->
                        emit(ResultState.SUCCESS(fruits))
                    } ?: throw Exception("Response is null")
                } else {
                    throw Exception("Response is failure")
                }
            } ?: throw Exception("Rest Api is coming null: Fix DEPENDENCY INJECTION")

        } catch (e: Exception) {
            emit(ResultState.ERROR(e))
        }
    }

}