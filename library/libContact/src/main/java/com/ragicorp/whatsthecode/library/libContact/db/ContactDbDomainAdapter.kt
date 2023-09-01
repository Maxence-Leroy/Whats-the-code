package com.ragicorp.whatsthecode.library.libContact.db

import com.ragicorp.whatsthecode.library.libContact.ContactDomain
import java.util.UUID

internal object ContactDbDomainAdapter {
    internal fun contactFromDb(contactDb: ContactDb): ContactDomain =
        ContactDomain(
            id = UUID.fromString(contactDb.id),
            name = contactDb.name,
            phoneNumber = contactDb.phoneNumber,
            address = contactDb.address,
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
            address = contact.address,
            codes = contact.codes,
            apartmentDescription = contact.apartmentDescription,
            freeText = contact.freeText,
            color = contact.color
        )
}