package com.ragicorp.whatsthecode

import android.app.Application

class InAppUpdateServiceImpl : InAppUpdateService {
    override fun start(application: Application) {
        // Nothing to do in prod
    }
}