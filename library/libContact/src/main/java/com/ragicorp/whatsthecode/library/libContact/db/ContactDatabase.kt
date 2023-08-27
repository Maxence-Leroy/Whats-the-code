package com.ragicorp.whatsthecode.library.libContact.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ContactDb::class], version = 1)
abstract class ContactDatabase : RoomDatabase() {
    abstract fun contactDao(): ContactDao
}
