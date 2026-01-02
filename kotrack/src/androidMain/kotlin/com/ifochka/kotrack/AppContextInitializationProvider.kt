package com.ifochka.kotrack

import android.app.Application
import android.content.Context
import androidx.startup.Initializer

object AppContext {
    private lateinit var application: Application

    internal fun setUp(context: Context) {
        application = context.applicationContext as Application
    }

    fun get(): Context {
        if (!::application.isInitialized) throw IllegalStateException("App context isn't initialised.")
        return application.applicationContext
    }
}

internal class AppContextInitializationProvider : Initializer<Context> {
    override fun create(context: Context): Context {
        AppContext.setUp(context)
        return AppContext.get()
    }

    override fun dependencies(): List<Class<out Initializer<*>?>?> = emptyList()
}
