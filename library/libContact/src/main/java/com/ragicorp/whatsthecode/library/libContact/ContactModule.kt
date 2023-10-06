package com.ragicorp.whatsthecode.library.libContact

import androidx.room.Room
import com.ragicorp.whatsthecode.corehelpers.MasterKey
import com.ragicorp.whatsthecode.library.libContact.api.AddressApiService
import com.ragicorp.whatsthecode.library.libContact.db.ContactDatabase
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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

    single {
        Retrofit.Builder()
            .baseUrl("https://photon.komoot.io//")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AddressApiService::class.java)
    }

    factory { get<ContactDatabase>().contactDao() }

    factory { ContactRepository(get(), get()) }

    factory { LibContact() }
}