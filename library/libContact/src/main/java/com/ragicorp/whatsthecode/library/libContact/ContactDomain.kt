package com.ragicorp.whatsthecode.library.libContact

import java.util.UUID

data class ContactDomain(
    val id: UUID,
    val name: String,
    val phoneNumber: String,
    val address: String,
    val apartmentDescription: String,
    val freeText: String
)