package com.ragicorp.whatsthecode.library.libContact.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [ContactDb::class], version = 2, exportSchema = false)
@TypeConverters(ContactConverters::class)
internal abstract class ContactDatabase : RoomDatabase() {
    internal abstract fun contactDao(): ContactDao
}

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE contact ADD COLUMN addressLong FLOAT")
        db.execSQL("ALTER TABLE contact ADD COLUMN addressLat FLOAT")
    }
}
