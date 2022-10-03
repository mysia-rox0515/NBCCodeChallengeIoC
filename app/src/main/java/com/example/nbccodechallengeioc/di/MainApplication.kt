package com.example.nbccodechallengeioc.di

import android.app.Application
import com.example.nbc_injection_sdk.api.NbcAppDelegateImpl
import com.example.nbc_injection_sdk.module.Module

class MainApplication : Application() {

    private val nbcAppDelegate by lazy {
        NbcAppDelegateImpl(
            NbcAppDelegateImpl.NbcAppConfig(
                featureModulesFactory = ::getFeatureModules
            )
        )
    }

    override fun onCreate() {
        super.onCreate()
        nbcAppDelegate.onCreate(this)
    }
}

/**
 * Here you can add more modules to your application
 *
 * Modules are defining what are the dependencies to be provided by the library
 * This will help when you have a modularised application
 */
fun getFeatureModules(): List<Module> = listOf(
    NbcAppModule()
)

