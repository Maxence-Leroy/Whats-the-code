package com.ragicorp.whatsthecode.contactDetail

import androidx.lifecycle.ViewModel
import com.ragicorp.whatsthecode.library.libContact.ContactRepository
import java.util.UUID

class ContactDetailViewModel(contactId: UUID, contactRepository: ContactRepository) : ViewModel() {
    val contact = contactRepository.getContactById(contactId)
}