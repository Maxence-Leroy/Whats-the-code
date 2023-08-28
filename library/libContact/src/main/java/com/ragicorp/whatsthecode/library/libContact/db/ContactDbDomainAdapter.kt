package com.ragicorp.whatsthecode.library.libContact.db

import com.ragicorp.whatsthecode.library.libContact.ContactDomain

internal object ContactDbDomainAdapter {
    internal fun contactFromDb(contactDb: ContactDb): ContactDomain =
        ContactDomain(id = contactDb.id, name = contactDb.name)

    internal fun contactDb(contact: ContactDomain): ContactDb =
        ContactDb(id = contact.id, name = contact.name)
}