package com.example.nbc_injection_sdk

import android.content.Context
import com.example.nbc_injection_sdk.api.NbcPlatform
import com.example.nbc_injection_sdk.api.getApi
import com.example.nbc_injection_sdk.module.Module
import com.example.nbc_injection_sdk.module.registerApi
import com.google.common.truth.Truth.assertThat
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.manipulation.Ordering

class PlatformAppTest {

    private val mockContext = mockk<Context>()

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `create apis to be provided by the dependency injection`() {
        val realModule = TestModule()
        val secondModuleTest = TestModuleTwo()
        val testObject = PlatformApp(listOf(realModule, secondModuleTest))

        testObject.create(mockContext, Thread.currentThread())

        assertThat(getApi<TestModule.TestApi>()).isInstanceOf(TestModule.TestApi::class.java)
        assertThat(getApi<TestModule.TestApi>()?.message).isEqualTo("Hello first API")
        assertThat(getApi<TestModule.TestApi>()).isNotInstanceOf(TestModuleTwo.TestApiTwo::class.java)

        assertThat(getApi<TestModuleTwo.TestApiTwo>()).isInstanceOf(TestModuleTwo.TestApiTwo::class.java)
        assertThat(getApi<TestModuleTwo.TestApiTwo>()?.message).isEqualTo("Hello second API")
    }

    class TestModule : Module {
        override fun onCreate(context: Context, registry: Module.Registry) {
            registry.registerApi { TestApi() }
        }

        class TestApi(
            val message: String = "Hello first API"
        )
    }

    class TestModuleTwo : Module {
        override fun onCreate(context: Context, registry: Module.Registry) {
            registry.registerApi { TestApiTwo() }
        }

        class TestApiTwo(
            val message: String = "Hello second API"
        )
    }
}