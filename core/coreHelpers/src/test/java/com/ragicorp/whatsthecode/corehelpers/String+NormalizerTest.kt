package com.ragicorp.whatsthecode.corehelpers

import org.junit.Assert.assertEquals
import org.junit.Test

class StringNormalizerTest {
    @Test
    fun `doesn't change a normal text`() {
        // Given
        val original = "Abcdef"

        // When
        val normalized = original.removeDiacritics()

        // Then
        assertEquals(normalized, original)
    }

    @Test
    fun `can remove all french accent and diacritics on lower case`() {
        // Given
        val original = "aàâä eéèêë iîï nñ oôö uùûü"

        // When
        val normalized = original.removeDiacritics()

        // Then
        assertEquals(normalized, "aaaa eeeee iii nn ooo uuuu")
    }

    @Test
    fun `can remove all french accent and diacritics on upper case`() {
        // Given
        val original = "AÀÂÄ EÉÈÊË IÎÏ NÑ OÔÖ UÙÛÜ"

        // When
        val normalized = original.removeDiacritics()

        // Then
        assertEquals(normalized, "AAAA EEEEE III NN OOO UUUU")
    }
}