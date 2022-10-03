package com.example.nbccodechallengeioc.rest

import com.example.nbc_injection_sdk.api.getApi
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface RestApi {
    fun getBuilder(): Retrofit.Builder
    fun getService(baseUrl: String, gson: Gson = GsonBuilder().create()): ServiceApi
}

class RestApiImpl(
    private val builder: Retrofit.Builder? = getApi<Retrofit.Builder>(),
    private val okHttpClient: OkHttpClient? = getApi<OkHttpClient.Builder>()?.build()
) : RestApi {

    override fun getBuilder(): Retrofit.Builder = builder ?: Retrofit.Builder()

    override fun getService(baseUrl: String, gson: Gson): ServiceApi {

        return synchronized(this) {
                getBuilder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(okHttpClient)
                    .build()
                    .create(ServiceApi::class.java)
        }
    }
}