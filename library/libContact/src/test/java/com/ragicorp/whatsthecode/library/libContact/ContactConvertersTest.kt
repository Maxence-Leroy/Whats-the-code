package com.ragicorp.whatsthecode.library.libContact

import com.ragicorp.whatsthecode.library.libContact.db.ContactConverters
import org.junit.Assert.assertEquals
import org.junit.Test

class ContactConvertersTest {
    @Test
    fun `Operation is giving back the same result`() {
        // Given
        val codes = stubContact.codes
        val converter = ContactConverters()

        // When
        val transformedCodes = converter.fromString(converter.codesToString(codes))

        // Then
        assertEquals(codes, transformedCodes)
    }
}