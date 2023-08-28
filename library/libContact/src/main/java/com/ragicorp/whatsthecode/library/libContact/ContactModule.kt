package com.ragicorp.whatsthecode.library.libContact

import androidx.room.Room
import com.ragicorp.whatsthecode.library.libContact.db.ContactDatabase
import org.koin.dsl.module
import org.koin.android.ext.koin.androidContext

val contactModule = module {
    single(createdAtStart = true) {
        Room.databaseBuilder(
            androidContext(),
            ContactDatabase::class.java, "contacts"
        ).build()
    }

    factory { get<ContactDatabase>().contactDao() }
}