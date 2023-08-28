package com.ragicorp.whatsthecode.contactList

import androidx.lifecycle.ViewModel
import com.ragicorp.whatsthecode.library.libContact.ContactDomain
import com.ragicorp.whatsthecode.library.libContact.ContactRepository
import kotlinx.coroutines.flow.Flow

class ContactListViewModel(contactRepository: ContactRepository) : ViewModel() {
    val contacts: Flow<List<ContactDomain>> = contactRepository.getContacts()
}