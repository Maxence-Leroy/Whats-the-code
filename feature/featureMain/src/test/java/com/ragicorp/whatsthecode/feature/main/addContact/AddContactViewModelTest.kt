package com.ragicorp.whatsthecode.feature.main.addContact

import android.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import com.ragicorp.whatsthecode.feature.main.AddressViewModel
import com.ragicorp.whatsthecode.feature.main.MainDispatcherRule
import com.ragicorp.whatsthecode.library.libContact.ContactDomain
import com.ragicorp.whatsthecode.library.libContact.LibContact
import com.ragicorp.whatsthecode.library.libContact.PlaceDomain
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import java.util.UUID

class AddContactViewModelTest {
    private val contactId = UUID.randomUUID()

    init {
        mockkStatic(UUID::class)
        every { UUID.randomUUID() } returns contactId
    }

    private val mockLibContact = mockk<LibContact> {
        coEvery { addContact(any()) } returns Unit
    }

    private fun generateMockAddressViewModel() = AddressViewModel()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `By default, nothing has changed and it can't be saved`() = runTest {
        // Given
        val viewModel = AddContactViewModel(mockLibContact, generateMockAddressViewModel())

        // When

        // Then
        assertFalse(viewModel.hasSomethingChanged.value)
        assertFalse(viewModel.isButtonSaveEnabled.value)
    }

    @Test
    fun `if the name changes with things other than spaces, then something has changed`() =
        runTest {
            // Given
            val viewModel = AddContactViewModel(mockLibContact, generateMockAddressViewModel())

            // When
            viewModel.setName(TextFieldValue("Hello"))

            // Then
            assertTrue(viewModel.hasSomethingChanged.value)
            assertTrue(viewModel.isButtonSaveEnabled.value)
        }

    @Test
    fun `if the name address with things other than spaces, then something has changed`() =
        runTest {
            // Given
            val addressViewModel = generateMockAddressViewModel()
            val viewModel = AddContactViewModel(mockLibContact, addressViewModel)

            // When
            addressViewModel.setAddress(PlaceDomain("Hello", null, null))

            // Then
            assertTrue(viewModel.hasSomethingChanged.value)
            assertTrue(viewModel.isButtonSaveEnabled.value)
        }

    @Test
    fun `if spaces are added, nothing has changed and it can't be save`() = runTest {
        // Given
        val addressViewModel = generateMockAddressViewModel()
        val viewModel = AddContactViewModel(mockLibContact, addressViewModel)

        // When
        viewModel.setName(TextFieldValue("        "))
        viewModel.setPhoneNumber(TextFieldValue("      "))
        addressViewModel.setAddress(PlaceDomain("      ", null, null))
        viewModel.setApartmentDescription(TextFieldValue("     "))
        viewModel.setFreeText(TextFieldValue("    "))

        // Then
        assertFalse(viewModel.hasSomethingChanged.value)
        assertFalse(viewModel.isButtonSaveEnabled.value)
    }

    @Test
    fun `if spaces or empty codes are added, nothing has changed`() = runTest {
        // Given
        val viewModel = AddContactViewModel(mockLibContact, generateMockAddressViewModel())

        // When
        viewModel.addCode()
        viewModel.addCode()
        viewModel.setCodes(0, Pair("     ", "      "))

        // Then
        assertFalse(viewModel.hasSomethingChanged.value)
        assertFalse(viewModel.isButtonSaveEnabled.value)
    }

    @Test
    fun `if real code is added something has changed but it is not savable`() = runTest {
        // Given
        val viewModel = AddContactViewModel(mockLibContact, generateMockAddressViewModel())

        // When
        viewModel.setCodes(0, Pair("a", "b"))

        // Then
        assertTrue(viewModel.hasSomethingChanged.value)
        assertFalse(viewModel.isButtonSaveEnabled.value)
    }

    @Test
    fun `if real content other than name or address is added, something has changed, but it can't be saved`() =
        runTest {
            // Given
            val viewModel = AddContactViewModel(mockLibContact, generateMockAddressViewModel())

            // When
            viewModel.setPhoneNumber(TextFieldValue("+336123456789"))

            // Then
            assertTrue(viewModel.hasSomethingChanged.value)
            assertFalse(viewModel.isButtonSaveEnabled.value)
        }

    @Test
    fun `contact is saved according to what has been filled but trimmed`() = runTest {
        // Given
        val addressViewModel = generateMockAddressViewModel()
        val viewModel = AddContactViewModel(mockLibContact, addressViewModel)

        // When
        viewModel.setName(TextFieldValue("  a   "))
        viewModel.setPhoneNumber(TextFieldValue("           b"))
        addressViewModel.setAddress(PlaceDomain("c        ", null, null))
        viewModel.addCode()
        viewModel.setCodes(0, Pair("   g    ", "h      "))
        viewModel.setCodes(1, Pair("     i", "j"))
        viewModel.setApartmentDescription(TextFieldValue("d"))
        viewModel.setFreeText(TextFieldValue("   e  "))
        viewModel.save(Color.BLUE)

        // Then
        coVerify(exactly = 1) {
            mockLibContact.addContact(
                ContactDomain(
                    id = contactId,
                    name = "a",
                    phoneNumber = "b",
                    address = PlaceDomain("c", null, null),
                    codes = listOf(Pair("g", "h"), Pair("i", "j")),
                    apartmentDescription = "d",
                    freeText = "e",
                    color = Color.BLUE
                )
            )
        }
    }
}