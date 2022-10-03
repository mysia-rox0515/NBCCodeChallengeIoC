package com.example.nbc_injection_sdk.api

interface NbcPlatform {
    fun <ApiType> getApi(apiClass: Class<ApiType>): ApiType?
}

object App {

    lateinit var platform: NbcPlatform
        private set

    fun init(instance: NbcPlatform) {
        platform = instance
    }

    fun <ApiType> getApi(apiClass: Class<ApiType>) = platform.getApi(apiClass)
}

inline fun <reified ApiType> getApi(): ApiType? = App.getApi(ApiType::class.java)