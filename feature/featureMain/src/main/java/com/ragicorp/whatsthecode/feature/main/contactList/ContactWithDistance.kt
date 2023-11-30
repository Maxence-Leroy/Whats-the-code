package com.ragicorp.whatsthecode.feature.main.contactList

import com.ragicorp.whatsthecode.library.libContact.ContactDomain

data class ContactWithDistance(
    val contact: ContactDomain,
    val distance: Float?
)