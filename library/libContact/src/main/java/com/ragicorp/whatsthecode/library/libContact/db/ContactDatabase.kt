package com.ragicorp.whatsthecode.library.libContact.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [ContactDb::class], version = 1, exportSchema = false)
@TypeConverters(ContactConverters::class)
internal abstract class ContactDatabase : RoomDatabase() {
    internal abstract fun contactDao(): ContactDao
}
