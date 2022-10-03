package com.example.nbc_injection_sdk

import android.content.Context
import com.example.nbc_injection_sdk.api.App
import com.example.nbc_injection_sdk.api.NbcPlatform
import com.example.nbc_injection_sdk.exceptions.NbcApiException
import com.example.nbc_injection_sdk.module.Module
import com.example.nbc_injection_sdk.module.Modules
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicReference

class PlatformApp(modules: List<Module>) : NbcPlatform {

    private lateinit var context: Context
    private lateinit var mainThread: Thread
    private val apis: ConcurrentHashMap<Class<*>, ImplementationContainer<*>> = ConcurrentHashMap()
    private val modules: Modules = Modules(modules)

    @Suppress("UNCHECKED_CAST")
    override fun <ApiType> getApi(apiClass: Class<ApiType>): ApiType? {
        val api = apis[apiClass] ?: return null

        if(isMainThread()) {
            api.module.startOrContinue(context)
        } else {
            if (!api.isStarted()) {
                runBlocking(Dispatchers.Main) { api.module.startOrContinue(context) }
            }
        }
        return api.instance() as? ApiType
    }

    private fun isMainThread() = Thread.currentThread() == mainThread

    fun create(context: Context, mainThread: Thread) {
        App.init(this)
        this.mainThread = mainThread
        this.context = context

        for (module in modules) {
            if(module.state == Modules.State.Uninitialized) {
                val newApis = createModule(module, context)
                apis.putAll(newApis)
            }
        }
    }

    private fun createModule(module: Modules.EntryModule, context: Context): Map<Class<*>, ImplementationContainer<*>> {
        val newApis = hashMapOf<Class<*>, ImplementationContainer<*>>()
        module.create(
            context,
            object : Module.Registry {
                override fun <T> registerApi(apiClass: Class<T>, creatorFunction: () -> T) {
                    if (apis.contains(apiClass)) throw NbcApiException("${module.module} is redefining an existing API $apiClass")
                    newApis[apiClass] = ImplementationContainer(creatorFunction, module)
                }
            }
        )
        return newApis
    }

    internal class ImplementationContainer<T>(
        private val creatorFunction: () -> T,
        internal val module: Modules.EntryModule
    ) {

        private var _implReference: AtomicReference<T>? = null

        @Synchronized
        fun instance(): T {
            var value = _implReference?.get()
            return if(value == null) {
                value = creatorFunction.invoke()
                _implReference = AtomicReference(value)
                value
            } else {
                value
            }
        }

        fun isStarted(): Boolean = module.isStarted()
    }
}