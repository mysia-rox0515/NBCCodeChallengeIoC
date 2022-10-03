package com.example.nbc_injection_sdk.module

import android.content.Context
import androidx.annotation.MainThread

interface Module {

    @MainThread
    fun onCreate(context: Context, registry: Registry)

    @MainThread
    fun onStart(context: Context) {}

    interface Registry {
        fun <T> registerApi(apiClass: Class<T>, creatorFunction: () -> T)
    }
}

inline fun <reified Clazz> Module.Registry.registerApi(noinline factoryFunction: () -> Clazz) =
    registerApi(Clazz::class.java, factoryFunction)