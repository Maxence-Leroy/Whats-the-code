package com.ragicorp.whatsthecode.library.libContact.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "contact")
data class ContactDb(
    @PrimaryKey val id: UUID,
    val name: String
)