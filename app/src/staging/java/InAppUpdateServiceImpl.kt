package com.ragicorp.whatsthecode

import android.app.Application
import com.microsoft.appcenter.AppCenter
import com.microsoft.appcenter.distribute.Distribute

class InAppUpdateServiceImpl : InAppUpdateService {
    override fun start(application: Application) {
        if (!AppCenter.isConfigured()) {
            AppCenter.start(
                application,
                BuildConfig.APPCENTER_SECRET,
                Distribute::class.java
            )
            Distribute.checkForUpdate()
        }
    }
}