package com.ragicorp.whatsthecode.library.libContact.db

import com.ragicorp.whatsthecode.library.libContact.ContactDomain

internal object ContactDbDomainAdapter {
    internal fun contactFromDb(contactDb: ContactDb): ContactDomain =
        ContactDomain(
            id = contactDb.id,
            name = contactDb.name,
            phoneNumber = contactDb.phoneNumber,
            address = contactDb.address,
            apartmentDescription = contactDb.apartmentDescription,
            freeText = contactDb.freeText
        )

    internal fun contactDb(contact: ContactDomain): ContactDb =
        ContactDb(
            id = contact.id,
            name = contact.name,
            phoneNumber = contact.phoneNumber,
            address = contact.address,
            apartmentDescription = contact.apartmentDescription,
            freeText = contact.freeText
        )
}