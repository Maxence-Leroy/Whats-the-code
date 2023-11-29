package com.ragicorp.whatsthecode.library.libContact

import com.ragicorp.whatsthecode.library.libContact.api.AddressApiService
import com.ragicorp.whatsthecode.library.libContact.api.AddressDomainApiConverters
import com.ragicorp.whatsthecode.library.libContact.db.ContactDao
import com.ragicorp.whatsthecode.library.libContact.db.ContactDbDomainAdapter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID

internal class ContactRepository(
    private val contactDao: ContactDao,
    private val addressApiService: AddressApiService
) {
    fun getContacts(): Flow<List<ContactDomain>> {
        return contactDao
            .getContacts()
            .map { contactsDb ->
                contactsDb.map { ContactDbDomainAdapter.contactFromDb(it) }
            }
    }

    fun getContactById(contactId: UUID): Flow<ContactDomain> {
        return contactDao
            .getContactById(contactId.toString())
            .map { contactDb -> ContactDbDomainAdapter.contactFromDb(contactDb) }
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

    suspend fun deleteContact(contact: ContactDomain) {
        contactDao.deleteContact(
            ContactDbDomainAdapter.contactDb(contact)
        )
    }

    suspend fun getAddressSuggestion(query: String): List<PlaceDomain> {
        val response = addressApiService.searchAddressNoCoordinates(query)
        return AddressDomainApiConverters.addressResultFromApi(response)
    }
}