package com.ragicorp.whatsthecode.library.libContact.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "contact")
internal data class ContactDb(
    @PrimaryKey val id: UUID,
    val name: String,
    val phoneNumber: String,
    val address: String,
    val apartmentDescription: String,
    val freeText: String
)