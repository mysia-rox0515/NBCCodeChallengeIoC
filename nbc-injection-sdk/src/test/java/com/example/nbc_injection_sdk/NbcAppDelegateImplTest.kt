package com.example.nbc_injection_sdk

import android.app.Application
import com.example.nbc_injection_sdk.api.NbcAppDelegate
import com.example.nbc_injection_sdk.api.NbcAppDelegateImpl
import com.example.nbc_injection_sdk.module.Module
import io.mockk.*
import org.junit.After
import org.junit.Before
import org.junit.Test

class NbcAppDelegateImplTest {

    private val mockApplication = mockk<Application> {
        every { applicationContext } returns mockk()
    }

    private val mockPlatformApp = mockk<PlatformApp>(relaxed = true) {
        every { create(mockApplication.applicationContext, any()) } returns Unit
    }
    private val mockFactory = mockk<(List<Module>) -> PlatformApp> {
        every { this@mockk.invoke(any()) } returns mockPlatformApp
    }
    private val mockModulesFactory = mockk<() -> List<Module>>(relaxed = true) {
        every { this@mockk.invoke() } returns listOf(mockk(), mockk())
    }

    private val mockDelegate = mockk<NbcAppDelegateImpl.NbcAppConfig> {
        every { appFactory } returns mockFactory
        every { featureModulesFactory } returns mockModulesFactory
    }

    private lateinit var testObject: NbcAppDelegate

    @Before
    fun startup() {
        testObject = NbcAppDelegateImpl(mockDelegate)
    }

    @After
    fun shutdown() {
        clearAllMocks()
    }

    @Test
    fun `create modules factory from an application delegate`() {
        testObject.onCreate(mockApplication)

        verify(exactly = 1) { mockFactory.invoke(any()) }
        verify(exactly = 1) { mockModulesFactory.invoke() }
        verify(exactly = 1) { mockPlatformApp.create(mockApplication.applicationContext, any()) }
    }
}