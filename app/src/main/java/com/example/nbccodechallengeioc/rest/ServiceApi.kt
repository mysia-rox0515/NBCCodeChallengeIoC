package com.example.nbccodechallengeioc.rest

import com.example.nbccodechallengeioc.model.Fruit
import retrofit2.Response
import retrofit2.http.GET

interface ServiceApi {

    @GET(ALL_FRUITS)
    suspend fun retrieveAllFruits(): Response<List<Fruit>>

    companion object {
        const val BASE_URL = "https://www.fruityvice.com/"

        private const val ALL_FRUITS = "api/fruit/all"
    }
}