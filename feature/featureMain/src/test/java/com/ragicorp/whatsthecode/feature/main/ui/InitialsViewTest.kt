package com.ragicorp.whatsthecode.feature.main.ui

import org.junit.Assert.assertEquals
import org.junit.Test

class InitialsViewTest {
    @Test
    fun `An empty string returns an empty string`() {
        // Given
        val initialString = ""

        // When
        val returnedString = computeMaxThreeInitials(initialString)

        // Then
        assertEquals("", returnedString)
    }

    @Test
    fun `A string with only one word returns the first letter capitalized`() {
        // Given
        val initialString = "fiufezihfezohjfozjfeoezjfozef"

        // When
        val returnedString = computeMaxThreeInitials(initialString)

        // Then
        assertEquals("F", returnedString)
    }

    @Test
    fun `A string with only two words returns the two initials capitalized`() {
        // Given
        val initialString = "hello world"

        // When
        val returnedString = computeMaxThreeInitials(initialString)

        // Then
        assertEquals("HW", returnedString)
    }

    @Test
    fun `A string with only three words returns the three initials capitalized`() {
        // Given
        val initialString = "I am Groot"

        // When
        val returnedString = computeMaxThreeInitials(initialString)

        // Then
        assertEquals("IAG", returnedString)
    }

    @Test
    fun `A string with more than three words returns the first three initials capitalized`() {
        // Given
        val initialString = "my name is bond, james bond"

        // When
        val returnedString = computeMaxThreeInitials(initialString)

        // Then
        assertEquals("MNI", returnedString)
    }
}