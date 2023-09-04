package com.ragicorp.whatsthecode.feature.main.contactList

import androidx.lifecycle.ViewModel
import com.ragicorp.whatsthecode.library.libContact.ContactDomain
import com.ragicorp.whatsthecode.library.libContact.LibContact
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ContactListViewModel(libContact: LibContact) : ViewModel() {
    val contacts: Flow<List<ContactDomain>> = libContact
        .getContacts()
        .map { contacts ->
            contacts.sortedBy { it.address }.sortedBy { it.name }
        }
}