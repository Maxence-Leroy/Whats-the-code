package com.ragicorp.whatsthecode.library.libContact.db

import androidx.room.Entity
import androidx.room.PrimaryKey

typealias Codes = List<Pair<String, String>>

@Entity(tableName = "contact")
internal data class ContactDb(
    @PrimaryKey val id: String,
    val name: String,
    val phoneNumber: String,
    val address: String,
    val codes: Codes,
    val apartmentDescription: String,
    val freeText: String,
    val color: Int
)