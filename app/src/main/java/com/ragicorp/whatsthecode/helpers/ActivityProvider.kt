package com.ragicorp.whatsthecode.helpers

import android.app.Activity
import org.koin.core.scope.Scope

class ActivityProvider {
    private var activity: Activity? = null

    fun getActivity(): Activity {
        return activity ?: throw IllegalStateException("ActivityProvider: activity null")
    }

    fun setActivity(activity: Activity) {
        this.activity = activity
    }
}

fun Scope.getActivity(): Activity {
    return get<ActivityProvider>().getActivity()
}
