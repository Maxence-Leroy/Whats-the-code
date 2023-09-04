package com.ragicorp.whatsthecode.feature.main.contactDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ragicorp.whatsthecode.library.libContact.ContactDomain
import com.ragicorp.whatsthecode.library.libContact.LibContact
import kotlinx.coroutines.launch
import java.util.UUID

class ContactDetailViewModel(contactId: UUID, private val libContact: LibContact) :
    ViewModel() {
    val contact = libContact.getContactById(contactId)

    fun deleteContact(contact: ContactDomain?) {
        if (contact == null) throw IllegalStateException()

        viewModelScope.launch {
            libContact.deleteContact(contact)
        }
    }
}