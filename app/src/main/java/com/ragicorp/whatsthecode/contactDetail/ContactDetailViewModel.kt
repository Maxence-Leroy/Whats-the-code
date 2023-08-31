package com.ragicorp.whatsthecode.contactDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ragicorp.whatsthecode.library.libContact.ContactDomain
import com.ragicorp.whatsthecode.library.libContact.ContactRepository
import kotlinx.coroutines.launch
import java.util.UUID

class ContactDetailViewModel(contactId: UUID, val contactRepository: ContactRepository) :
    ViewModel() {
    val contact = contactRepository.getContactById(contactId)

    fun deleteContact(contact: ContactDomain?) {
        if (contact == null) throw IllegalStateException()

        viewModelScope.launch {
            contactRepository.deleteContact(contact)
        }
    }
}