package com.ragicorp.whatsthecode

import android.app.Application
import com.microsoft.appcenter.AppCenter
import com.microsoft.appcenter.distribute.Distribute

class InAppUpdateServiceImpl : InAppUpdateService {
    override fun start(application: Application) {
        AppCenter.start(application, BuildConfig.APPCENTER_APP_SECRET, Distribute::class.java)
    }
}