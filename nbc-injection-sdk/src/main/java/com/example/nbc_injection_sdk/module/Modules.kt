package com.example.nbc_injection_sdk.module

import android.content.Context
import androidx.annotation.AnyThread
import androidx.annotation.MainThread

class Modules(modules: List<Module>) : Iterable<Modules.EntryModule> {

    private val modules: MutableList<EntryModule> = modules.map { EntryModule(it) }.toMutableList()

    class EntryModule(internal val module: Module) {
        var state: State = State.Uninitialized

        @MainThread
        @Synchronized
        fun startOrContinue(context: Context) {
            if (state == State.Created) {
                start(context)
            }
        }

        @AnyThread
        @Synchronized
        fun isStarted(): Boolean = state == State.Started

        @MainThread
        @Synchronized
        fun create(context: Context, reg: Module.Registry) {
            module.onCreate(context, reg)
            state = State.Created
        }

        @MainThread
        @Synchronized
        fun start(context: Context) {
            state = State.Starting
            module.onStart(context)
            state = State.Started
        }
    }

    override fun iterator(): Iterator<EntryModule> = modules.iterator()

    enum class State {
        Uninitialized,
        Created,
        Starting,
        Started
    }
}