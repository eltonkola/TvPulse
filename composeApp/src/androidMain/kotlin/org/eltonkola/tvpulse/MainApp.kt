package org.eltonkola.tvpulse

import android.app.Application
import android.content.Context

class MainApp : Application() {
    override fun onCreate() {
        super.onCreate()
        AndroidContext.initialize(this)
    }
}

object AndroidContext {
    lateinit var applicationContext: Context
        private set

    fun initialize(context: Context) {
        applicationContext = context.applicationContext
    }

    fun get(): Context = applicationContext
}
