package com.ragicorp.whatsthecode.library.libContact.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
internal interface ContactDao {
    @Query("SELECT * FROM contact")
    fun getContacts(): Flow<List<ContactDb>>

    @Insert
    suspend fun addContact(contact: ContactDb)

    @Update
    suspend fun editContact(contact: ContactDb)

    @Delete
    suspend fun deleteContact(contactDb: ContactDb)
}