package com.ragicorp.whatsthecode

import android.app.Application
import com.ragicorp.whatsthecode.addContact.AddContactViewModel
import com.ragicorp.whatsthecode.contactDetail.ContactDetailViewModel
import com.ragicorp.whatsthecode.contactList.ContactListViewModel
import com.ragicorp.whatsthecode.library.libContact.contactModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import java.util.UUID

class MainApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        val helpersModule = module {
            single { ActivityProvider() }
            single { PermissionsManager(getActivity()) }
        }

        val contactScreenModule = module {
            viewModel { ContactListViewModel(get()) }
            viewModel { AddContactViewModel(get()) }
            viewModel { (contactId: UUID) ->
                ContactDetailViewModel(
                    contactId = contactId,
                    contactRepository = get()
                )
            }
        }

        startKoin {
            // Log Koin into Android logger
            androidLogger()
            // Reference Android context
            androidContext(this@MainApplication)
            // Load modules
            modules(helpersModule, contactModule, contactScreenModule)
        }

    }
}