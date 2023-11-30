package com.ragicorp.whatsthecode.feature.main

import com.ragicorp.whatsthecode.feature.main.addContact.AddContactViewModel
import com.ragicorp.whatsthecode.feature.main.addressSelection.AddressSelectionViewModel
import com.ragicorp.whatsthecode.feature.main.contactDetail.ContactDetailViewModel
import com.ragicorp.whatsthecode.feature.main.contactList.ContactListViewModel
import com.ragicorp.whatsthecode.feature.main.editContact.EditContactViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import java.util.UUID

val featureMainModule = module {
    viewModel { ContactListViewModel(get(), androidApplication()) }
    viewModel { AddContactViewModel(get(), get()) }
    viewModel { (contactId: UUID) ->
        ContactDetailViewModel(
            contactId = contactId,
            libContact = get()
        )
    }
    viewModel { (contactId: UUID) ->
        EditContactViewModel(
            contactId = contactId,
            libContact = get(),
            addressViewModel = get()
        )
    }
    viewModel { AddressSelectionViewModel(get(), get()) }
    single { AddressViewModel() }
}