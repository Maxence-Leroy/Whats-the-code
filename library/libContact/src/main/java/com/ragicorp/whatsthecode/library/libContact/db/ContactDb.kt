package com.ragicorp.whatsthecode.library.libContact.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contact")
internal data class ContactDb(
    @PrimaryKey val id: String,
    val name: String,
    val phoneNumber: String,
    val address: String,
    val apartmentDescription: String,
    val freeText: String
)