package com.ragicorp.whatsthecode.library.libContact

import androidx.room.Room
import com.ragicorp.whatsthecode.corehelpers.MasterKey
import com.ragicorp.whatsthecode.library.libContact.db.ContactDatabase
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val contactModule = module {
    single(createdAtStart = true) {
        val context = androidContext()

        val passphrase = SQLiteDatabase.getBytes(MasterKey.getDBPassword(context))
        val encryptionFactory = SupportFactory(passphrase)

        Room
            .databaseBuilder(
                context,
                ContactDatabase::class.java, "contacts"
            )
            .openHelperFactory(encryptionFactory)
            .build()
    }

    factory { get<ContactDatabase>().contactDao() }

    factory { ContactRepository(get()) }

    factory { LibContact() }
}