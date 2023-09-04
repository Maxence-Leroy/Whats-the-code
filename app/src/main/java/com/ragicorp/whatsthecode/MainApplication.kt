package com.ragicorp.whatsthecode

import android.app.Application
import com.ragicorp.whatsthecode.corehelpers.helpersModule
import com.ragicorp.whatsthecode.feature.main.featureMainModule
import com.ragicorp.whatsthecode.library.libContact.contactModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            // Log Koin into Android logger
            androidLogger()
            // Reference Android context
            androidContext(this@MainApplication)
            // Load modules
            modules(helpersModule, contactModule, featureMainModule)
        }

    }
}