package com.ragicorp.whatsthecode

import android.app.Application
import com.ragicorp.whatsthecode.corehelpers.helpersModule
import com.ragicorp.whatsthecode.feature.main.featureMainModule
import com.ragicorp.whatsthecode.library.libContact.contactModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module

class MainApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        val versionModule = module {
            single(named("appVersion")) { BuildConfig.VERSION_NAME }
        }

        startKoin {
            // Log Koin into Android logger
            androidLogger()
            // Reference Android context
            androidContext(this@MainApplication)
            // Load modules
            modules(helpersModule, contactModule, featureMainModule, versionModule)
        }

        val inAppUpdateService = InAppUpdateServiceImpl()
        inAppUpdateService.start(this)
    }
}