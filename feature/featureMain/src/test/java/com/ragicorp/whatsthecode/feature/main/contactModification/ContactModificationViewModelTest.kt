package com.ragicorp.whatsthecode.feature.main.contactModification

import androidx.compose.ui.text.input.TextFieldValue
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

        assertEquals("", viewModel.name.value.text)
        assertEquals("", viewModel.phoneNumber.value.text)
        assertEquals(listOf(Pair("", "")), viewModel.codes.value)
        assertEquals("", viewModel.apartmentDescription.value.text)
        assertEquals("", viewModel.freeText.value.text)

        // When
        val newName = "Emmanuel Macron"
        val newPhoneNumber = "+33123456789"
        val newApartmentDescription = "Biggest room of the palace"
        val newFreeText = "Because he is the boss"

        viewModel.setName(TextFieldValue(newName))
        viewModel.setPhoneNumber(TextFieldValue(newPhoneNumber))
        viewModel.setApartmentDescription(TextFieldValue(newApartmentDescription))
        viewModel.setFreeText(TextFieldValue(newFreeText))

        // Then
        assertEquals(newName, viewModel.name.value.text)
        assertEquals(newPhoneNumber, viewModel.phoneNumber.value.text)
        assertEquals(listOf(Pair("", "")), viewModel.codes.value)
        assertEquals(newApartmentDescription, viewModel.apartmentDescription.value.text)
        assertEquals(newFreeText, viewModel.freeText.value.text)
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