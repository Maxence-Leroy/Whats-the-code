package com.ragicorp.whatsthecode.library.libContact

import com.ragicorp.whatsthecode.library.libContact.api.AddressApiService
import com.ragicorp.whatsthecode.library.libContact.db.ContactDao
import com.ragicorp.whatsthecode.library.libContact.db.ContactDbDomainAdapter
import com.ragicorp.whatsthecode.library.libContact.file.FileDataSource
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.UUID

class ContactRepositoryTestError : Throwable()

class ContactRepositoryTest {
    @Test
    fun `Can get the list of contacts`() = runTest {
        // Given
        val mockDao = generateMockDao(listContacts = listOf(stubContact))
        val mockAddressApi = mockk<AddressApiService>(relaxed = true)
        val fileDataSource = mockk<FileDataSource>(relaxed = true)
        val repository = ContactRepository(mockDao, mockAddressApi, fileDataSource)

        // When
        val contacts = repository.getContacts().take(1).toList()

        // Then
        assertEquals(contacts, listOf(listOf(stubContact)))
        verify(exactly = 1) {
            mockDao.getContacts()
        }
    }

    @Test
    fun `Can get single contact`() = runTest {
        // Given
        val mockDao = generateMockDao(singleContact = stubContact)
        val mockAddressApi = mockk<AddressApiService>(relaxed = true)
        val fileDataSource = mockk<FileDataSource>(relaxed = true)
        val repository = ContactRepository(mockDao, mockAddressApi, fileDataSource)
        val id = UUID.randomUUID()

        // When
        val contact = repository.getContactById(id).take(1).toList()

        // Then
        assertEquals(contact, listOf(stubContact))
        verify(exactly = 1) {
            mockDao.getContactById(id.toString())
        }
    }

    @Test
    fun `Contact addition success is propagated`() = runTest {
        // Given
        val mockDao = generateMockDao(addSuccess = true)
        val mockAddressApi = mockk<AddressApiService>(relaxed = true)
        val fileDataSource = mockk<FileDataSource>(relaxed = true)
        val repository = ContactRepository(mockDao, mockAddressApi, fileDataSource)

        // When
        repository.addContact(stubContact)

        // Then
        coVerify(exactly = 1) {
            mockDao.addContact(ContactDbDomainAdapter.contactDb(stubContact))
        }
    }

    @Test
    fun `Contact addition error is propagated`() = runTest {
        // Given
        val mockDao = generateMockDao(addSuccess = false)
        val mockAddressApi = mockk<AddressApiService>(relaxed = true)
        val fileDataSource = mockk<FileDataSource>(relaxed = true)
        val repository = ContactRepository(mockDao, mockAddressApi, fileDataSource)

        // When
        val error = try {
            repository.addContact(stubContact)
            null
        } catch (e: Throwable) {
            e
        }

        // Then
        coVerify(exactly = 1) {
            mockDao.addContact(ContactDbDomainAdapter.contactDb(stubContact))
        }
        assertEquals(error?.javaClass, ContactRepositoryTestError::class.java)
    }


    @Test
    fun `Contact edit success is propagated`() = runTest {
        // Given
        val mockDao = generateMockDao(editSuccess = true)
        val mockAddressApi = mockk<AddressApiService>(relaxed = true)
        val fileDataSource = mockk<FileDataSource>(relaxed = true)
        val repository = ContactRepository(mockDao, mockAddressApi, fileDataSource)

        // When
        repository.editContact(stubContact)

        // Then
        coVerify(exactly = 1) {
            mockDao.editContact(ContactDbDomainAdapter.contactDb(stubContact))
        }
    }

    @Test
    fun `Contact edit error is propagated`() = runTest {
        // Given
        val mockDao = generateMockDao(editSuccess = false)
        val mockAddressApi = mockk<AddressApiService>(relaxed = true)
        val fileDataSource = mockk<FileDataSource>(relaxed = true)
        val repository = ContactRepository(mockDao, mockAddressApi, fileDataSource)

        // When
        val error = try {
            repository.editContact(stubContact)
            null
        } catch (e: Throwable) {
            e
        }

        // Then
        coVerify(exactly = 1) {
            mockDao.editContact(ContactDbDomainAdapter.contactDb(stubContact))
        }
        assertEquals(error?.javaClass, ContactRepositoryTestError::class.java)
    }

    @Test
    fun `Contact deletion success is propagated`() = runTest {
        // Given
        val mockDao = generateMockDao(deleteSuccess = true)
        val mockAddressApi = mockk<AddressApiService>(relaxed = true)
        val fileDataSource = mockk<FileDataSource>(relaxed = true)
        val repository = ContactRepository(mockDao, mockAddressApi, fileDataSource)

        // When
        repository.deleteContact(stubContact)

        // Then
        coVerify(exactly = 1) {
            mockDao.deleteContact(ContactDbDomainAdapter.contactDb(stubContact))
        }
    }

    @Test
    fun `Contact deletion error is propagated`() = runTest {
        // Given
        val mockDao = generateMockDao(deleteSuccess = false)
        val mockAddressApi = mockk<AddressApiService>(relaxed = true)
        val fileDataSource = mockk<FileDataSource>(relaxed = true)
        val repository = ContactRepository(mockDao, mockAddressApi, fileDataSource)

        // When
        val error = try {
            repository.deleteContact(stubContact)
            null
        } catch (e: Throwable) {
            e
        }

        // Then
        coVerify(exactly = 1) {
            mockDao.deleteContact(ContactDbDomainAdapter.contactDb(stubContact))
        }
        assertEquals(error?.javaClass, ContactRepositoryTestError::class.java)
    }

    private fun generateMockDao(
        listContacts: List<ContactDomain> = listOf(stubContact),
        singleContact: ContactDomain = stubContact,
        addSuccess: Boolean = true,
        editSuccess: Boolean = true,
        deleteSuccess: Boolean = true,
    ): ContactDao {
        return mockk {
            every { getContacts() } returns flowOf(listContacts.map {
                ContactDbDomainAdapter.contactDb(
                    it
                )
            })
            every { getContactById(any()) } returns flowOf(
                ContactDbDomainAdapter.contactDb(
                    singleContact
                )
            )
            if (addSuccess) {
                coEvery { addContact(any()) } returns Unit
            } else {
                coEvery { addContact(any()) } throws ContactRepositoryTestError()
            }
            if (editSuccess) {
                coEvery { editContact(any()) } returns Unit
            } else {
                coEvery { editContact(any()) } throws ContactRepositoryTestError()
            }
            if (deleteSuccess) {
                coEvery { deleteContact(any()) } returns Unit
            } else {
                coEvery { deleteContact(any()) } throws ContactRepositoryTestError()
            }
        }
    }
}