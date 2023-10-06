package com.ragicorp.whatsthecode.library.libContact

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.UUID

class LibContact : KoinComponent {
    private val contactRepository: ContactRepository by inject()

    fun getContacts() = contactRepository.getContacts()
    fun getContactById(contactId: UUID) = contactRepository.getContactById(contactId)
    suspend fun addContact(contact: ContactDomain) = contactRepository.addContact(contact)
    suspend fun editContact(contact: ContactDomain) = contactRepository.editContact(contact)
    suspend fun deleteContact(contact: ContactDomain) = contactRepository.deleteContact(contact)
    suspend fun getAddressSuggestion(query: String) = contactRepository.getAddressSuggestion(query)
}