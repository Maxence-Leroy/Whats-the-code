package com.ragicorp.whatsthecode.library.libContact

import com.ragicorp.whatsthecode.library.libContact.db.ContactDbDomainAdapter
import org.junit.Assert
import org.junit.Test

class ContactDbDomainAdapterTest {
    @Test
    fun `Operation is giving back the same result`() {
        // Given
        val contact = stubContact

        // When
        val transformedContact =
            ContactDbDomainAdapter.contactFromDb(ContactDbDomainAdapter.contactDb(contact))

        // Then
        Assert.assertEquals(contact, transformedContact)
    }
}