package com.ragicorp.whatsthecode.library.libContact

import com.ragicorp.whatsthecode.library.libContact.db.ContactDao
import com.ragicorp.whatsthecode.library.libContact.db.ContactDbDomainAdapter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class ContactRepository : KoinComponent {
    private val contactDao: ContactDao = get()

    fun getContacts(): Flow<List<ContactDomain>> {
        return contactDao
            .getContacts()
            .map { contactsDb ->
                contactsDb.map { ContactDbDomainAdapter.contactFromDb(it) }
            }
    }

    suspend fun addContact(contact: ContactDomain) {
        contactDao.addContact(
            ContactDbDomainAdapter.contactDb(contact)
        )
    }

    suspend fun editContact(contact: ContactDomain) {
        contactDao.editContact(
            ContactDbDomainAdapter.contactDb(contact)
        )
    }
}