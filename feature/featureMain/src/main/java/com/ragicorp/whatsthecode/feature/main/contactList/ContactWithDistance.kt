package com.ragicorp.whatsthecode.feature.main.contactList

import android.content.Context
import com.ragicorp.whatsthecode.feature.main.R
import com.ragicorp.whatsthecode.library.libContact.ContactDomain

data class ContactWithDistance(
    val contact: ContactDomain,
    val distance: Float?
) {
    fun printDistance(context: Context): String? {
        return if (distance == null) {
            null
        } else if (distance < 1000) {
            val distanceInMeters = "%.0f".format(distance)
            context.getString(R.string.contactDistance_meters, distanceInMeters)
        } else {
            val distanceInKm = "%.1f".format(distance / 1000)
            context.getString(R.string.contactDistance_kilometers, distanceInKm)
        }
    }
}