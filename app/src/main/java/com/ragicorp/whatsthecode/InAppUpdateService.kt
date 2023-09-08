package com.ragicorp.whatsthecode

import android.app.Application

interface InAppUpdateService {
    fun start(application: Application)
}