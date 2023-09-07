package com.ragicorp.whatsthecode.feature.main.editContact

import androidx.compose.ui.text.input.TextFieldValue
import com.ragicorp.whatsthecode.feature.main.MainDispatcherRule
import com.ragicorp.whatsthecode.library.libContact.LibContact
import com.ragicorp.whatsthecode.library.libContact.stubContact
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import java.util.UUID

class EditContactViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val contactId = UUID.randomUUID()

    private val libContact = mockk<LibContact> {
        every { getContactById(any()) } returns flowOf(stubContact)
        coEvery { editContact(any()) } returns Unit
    }

    @Test
    fun `contact is retrieved from library at start`() {
        // Given
        val viewModel = EditContactViewModel(contactId, libContact)

        // When

        // Then
        verify(exactly = 1) { libContact.getContactById(contactId) }
        assertEquals(stubContact.name, viewModel.name.value.text)
        assertEquals(stubContact.phoneNumber, viewModel.phoneNumber.value.text)
        assertEquals(stubContact.address, viewModel.address.value.text)
        assertEquals(stubContact.codes, viewModel.codes.value)
        assertEquals(stubContact.apartmentDescription, viewModel.apartmentDescription.value.text)
        assertEquals(stubContact.freeText, viewModel.freeText.value.text)
    }

    @Test
    fun `By default, nothing has changed and it can't be saved`() = runTest {
        // Given
        val viewModel = EditContactViewModel(contactId, libContact)

        // When

        // Then
        TestCase.assertFalse(viewModel.hasSomethingChanged.value)
        TestCase.assertFalse(viewModel.isButtonSaveEnabled.value)
    }

    @Test
    fun `if the name changes with things other than spaces, then something has changed`() =
        runTest {
            // Given
            val viewModel = EditContactViewModel(contactId, libContact)

            // When
            viewModel.setName(TextFieldValue("Hello"))

            // Then
            TestCase.assertTrue(viewModel.hasSomethingChanged.value)
            TestCase.assertTrue(viewModel.isButtonSaveEnabled.value)
        }

    @Test
    fun `if the name address with things other than spaces, then something has changed`() =
        runTest {
            // Given
            val viewModel = EditContactViewModel(contactId, libContact)

            // When
            viewModel.setAddress(TextFieldValue("Hello"))

            // Then
            TestCase.assertTrue(viewModel.hasSomethingChanged.value)
            TestCase.assertTrue(viewModel.isButtonSaveEnabled.value)
        }

    @Test
    fun `if spaces are added, nothing has changed and it can't be save`() = runTest {
        // Given
        val viewModel = EditContactViewModel(contactId, libContact)

        // When
        viewModel.setName(TextFieldValue("${stubContact.name}        "))
        viewModel.setPhoneNumber(TextFieldValue("      ${stubContact.phoneNumber}"))
        viewModel.setAddress(TextFieldValue("  ${stubContact.address}    "))
        viewModel.setApartmentDescription(TextFieldValue("    ${stubContact.apartmentDescription} "))
        viewModel.setFreeText(TextFieldValue("  ${stubContact.freeText}  "))

        // Then
        TestCase.assertFalse(viewModel.hasSomethingChanged.value)
        TestCase.assertFalse(viewModel.isButtonSaveEnabled.value)
    }

    @Test
    fun `if spaces or empty codes are added, nothing has changed`() = runTest {
        // Given
        val viewModel = EditContactViewModel(contactId, libContact)

        // When
        viewModel.addCode()
        viewModel.addCode()
        viewModel.setCodes(3, Pair("     ", "      "))

        // Then
        TestCase.assertFalse(viewModel.hasSomethingChanged.value)
        TestCase.assertFalse(viewModel.isButtonSaveEnabled.value)
    }

    @Test
    fun `if real code is added something has changed and it is savable`() = runTest {
        // Given
        val viewModel = EditContactViewModel(contactId, libContact)

        // When
        viewModel.setCodes(0, Pair("a", "b"))

        // Then
        TestCase.assertTrue(viewModel.hasSomethingChanged.value)
        TestCase.assertTrue(viewModel.isButtonSaveEnabled.value)
    }

    @Test
    fun `if real content other than name or address is added, something has changed and it can be saved`() =
        runTest {
            // Given
            val viewModel = EditContactViewModel(contactId, libContact)

            // When
            viewModel.setPhoneNumber(TextFieldValue("YOLO"))

            // Then
            TestCase.assertTrue(viewModel.hasSomethingChanged.value)
            TestCase.assertTrue(viewModel.isButtonSaveEnabled.value)
        }

    @Test
    fun `contact is saved according to what has been filled but trimmed`() = runTest {
        // Given
        val viewModel = EditContactViewModel(contactId, libContact)

        // When
        viewModel.setName(TextFieldValue("  a   "))
        viewModel.save()

        // Then
        coVerify(exactly = 1) {
            libContact.editContact(stubContact.copy(name = "a"))
        }
    }
}