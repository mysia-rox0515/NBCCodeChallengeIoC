package com.example.nbc_injection_sdk.api

import android.app.Application
import com.example.nbc_injection_sdk.PlatformApp
import com.example.nbc_injection_sdk.module.Module

interface NbcAppDelegate {
    fun onCreate(application: Application)
}

class NbcAppDelegateImpl(
    private val configuration: NbcAppConfig
) : NbcAppDelegate {

    override fun onCreate(application: Application) {
        configuration.appFactory.invoke(
            configuration.featureModulesFactory.invoke()
        ).create(application.applicationContext, Thread.currentThread())
    }

    data class NbcAppConfig(
        val appFactory: (List<Module>) -> PlatformApp = { PlatformApp(it) },
        val featureModulesFactory: () -> List<Module> = { emptyList() }
    )
}