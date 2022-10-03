package com.example.nbccodechallengeioc.di

import android.content.Context
import com.example.nbc_injection_sdk.module.Module
import com.example.nbc_injection_sdk.module.registerApi
import com.example.nbccodechallengeioc.rest.FruitsRepository
import com.example.nbccodechallengeioc.rest.FruitsRepositoryImpl
import com.example.nbccodechallengeioc.rest.RestApi
import com.example.nbccodechallengeioc.rest.RestApiImpl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

/**
 * The module class should always extend the [Module] interface in order to register all the dependencies that are needed using [Module.Registry]
 *
 * All objects and interfaces you need to provide to your project can be registered right here
 */
class NbcAppModule : Module {

    override fun onCreate(context: Context, registry: Module.Registry) {
        registry.registerApi { Retrofit.Builder() }
        registry.registerApi { OkHttpClient.Builder().addInterceptor(
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
        ) }
        registry.registerApi<RestApi> { RestApiImpl() }
        registry.registerApi<FruitsRepository> { FruitsRepositoryImpl() }
    }
}
