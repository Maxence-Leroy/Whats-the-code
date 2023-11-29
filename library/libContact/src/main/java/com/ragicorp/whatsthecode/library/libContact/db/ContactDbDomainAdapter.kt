package com.ragicorp.whatsthecode.library.libContact.db

import com.ragicorp.whatsthecode.library.libContact.ContactDomain
import com.ragicorp.whatsthecode.library.libContact.PlaceDomain
import java.util.UUID

internal object ContactDbDomainAdapter {
    internal fun contactFromDb(contactDb: ContactDb): ContactDomain =
        ContactDomain(
            id = UUID.fromString(contactDb.id),
            name = contactDb.name,
            phoneNumber = contactDb.phoneNumber,
            address = PlaceDomain(contactDb.address, contactDb.addressLong, contactDb.addressLat),
            codes = contactDb.codes,
            apartmentDescription = contactDb.apartmentDescription,
            freeText = contactDb.freeText,
            color = contactDb.color
        )

    internal fun contactDb(contact: ContactDomain): ContactDb =
        ContactDb(
            id = contact.id.toString(),
            name = contact.name,
            phoneNumber = contact.phoneNumber,
            address = contact.address.address,
            addressLong = contact.address.long,
            addressLat = contact.address.lat,
            codes = contact.codes,
            apartmentDescription = contact.apartmentDescription,
            freeText = contact.freeText,
            color = contact.color
        )
}