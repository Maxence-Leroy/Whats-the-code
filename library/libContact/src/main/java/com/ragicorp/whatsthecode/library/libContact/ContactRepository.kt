package com.ragicorp.whatsthecode.library.libContact

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.ragicorp.whatsthecode.library.libContact.api.AddressApiService
import com.ragicorp.whatsthecode.library.libContact.api.AddressDomainApiConverters
import com.ragicorp.whatsthecode.library.libContact.db.ContactDao
import com.ragicorp.whatsthecode.library.libContact.db.ContactDbDomainAdapter
import com.ragicorp.whatsthecode.library.libContact.file.FileDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import java.net.URI
import java.util.UUID

internal class ContactRepository(
    private val contactDao: ContactDao,
    private val addressApiService: AddressApiService,
    private val fileDataSource: FileDataSource
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

    @SuppressLint("MissingPermission")
    suspend fun getDistanceFromCurrentPosition(contact: ContactDomain, context: Context): Float? {
        if (contact.address.long == null || contact.address.lat == null || GoogleApiAvailability.getInstance()
                .isGooglePlayServicesAvailable(context) != ConnectionResult.SUCCESS
        ) {
            return null
        }

        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        val location =
            fusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null).await()
        val result = FloatArray(1)
        Location.distanceBetween(
            contact.address.lat.toDouble(),
            contact.address.long.toDouble(),
            location.latitude,
            location.longitude,
            result
        )
        return result[0]
    }

    suspend fun exportContactInFile(contact: ContactDomain): URI =
        fileDataSource.exportContactInFile(contact)
}