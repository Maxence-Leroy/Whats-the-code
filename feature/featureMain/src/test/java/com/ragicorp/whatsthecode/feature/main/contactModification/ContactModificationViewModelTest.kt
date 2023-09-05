package com.ragicorp.whatsthecode.feature.main.contactModification

import com.ragicorp.whatsthecode.feature.main.MainDispatcherRule
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class NotAbstractContactModificationViewModel : ContactModificationViewModel()

class ContactModificationViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `set functions are changing the value of the state flow`() = runTest {
        // Given
        val viewModel = NotAbstractContactModificationViewModel()

        assertEquals("", viewModel.name.value)
        assertEquals("", viewModel.phoneNumber.value)
        assertEquals("", viewModel.address.value)
        assertEquals(listOf(Pair("", "")), viewModel.codes.value)
        assertEquals("", viewModel.apartmentDescription.value)
        assertEquals("", viewModel.freeText.value)

        // When
        val newName = "Emmanuel Macron"
        val newPhoneNumber = "+33123456789"
        val newAddress = "55 Rue du Faubourg Saint-Honoré, 75008 Paris"
        val newApartmentDescription = "Biggest room of the palace"
        val newFreeText = "Because he is the boss"

        viewModel.setName(newName)
        viewModel.setPhoneNumber(newPhoneNumber)
        viewModel.setAddress(newAddress)
        viewModel.setApartmentDescription(newApartmentDescription)
        viewModel.setFreeText(newFreeText)

        // Then
        assertEquals(newName, viewModel.name.value)
        assertEquals(newPhoneNumber, viewModel.phoneNumber.value)
        assertEquals(newAddress, viewModel.address.value)
        assertEquals(listOf(Pair("", "")), viewModel.codes.value)
        assertEquals(newApartmentDescription, viewModel.apartmentDescription.value)
        assertEquals(newFreeText, viewModel.freeText.value)
    }

    @Test
    fun `code functions are handled correctly`() {
        // Given
        val viewModel = NotAbstractContactModificationViewModel()
        assertEquals(listOf(Pair("", "")), viewModel.codes.value)

        val code1 = Pair("Fence", "1234")
        val code2 = Pair("Nuclear weapons", "5678")

        // When
        viewModel.addCode()
        viewModel.addCode()
        viewModel.addCode()
        viewModel.setCodes(0, code1)
        viewModel.setCodes(2, code2)
        viewModel.removeCode(1)

        // Then
        assertEquals(listOf(code1, code2, Pair("", "")), viewModel.codes.value)
    }

    @Test
    fun `trim codes is trimming empty values`() {
        // Given
        val codes = listOf(
            Pair("           ", ""),
            Pair("", "           "),
            Pair("a", "           "),
            Pair("b", ""),
            Pair("           ", "           "),
            Pair("", "c"),
            Pair("           ", "d"),
            Pair("  e  ", "  f  ")
        )
        val viewModel = NotAbstractContactModificationViewModel()

        // When
        val trimmedCodes = viewModel.trimCodes(codes)

        // Then
        assertEquals(
            listOf(
                Pair("a", ""),
                Pair("b", ""),
                Pair("", "c"),
                Pair("", "d"),
                Pair("e", "f")
            ), trimmedCodes
        )
    }

    @Test
    fun `trimming codes is working with codes state flow`() = runTest {
        // Given
        val viewModel = NotAbstractContactModificationViewModel()
        for (i in 0..6) {
            viewModel.addCode()
        }
        viewModel.setCodes(0, Pair("           ", ""))
        viewModel.setCodes(1, Pair("", "           "))
        viewModel.setCodes(2, Pair("a", "           "))
        viewModel.setCodes(3, Pair("b", ""))
        viewModel.setCodes(4, Pair("           ", "           "))
        viewModel.setCodes(5, Pair("", "c"))
        viewModel.setCodes(6, Pair("           ", "d"))
        viewModel.setCodes(7, Pair("  e  ", "  f  "))

        // When
        val trimmedCodes = viewModel.trimCodes()

        // Then
        assertEquals(
            listOf(
                Pair("a", ""),
                Pair("b", ""),
                Pair("", "c"),
                Pair("", "d"),
                Pair("e", "f")
            ), trimmedCodes
        )
    }
}