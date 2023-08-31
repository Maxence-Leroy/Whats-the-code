package com.ragicorp.whatsthecode.addContact

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ragicorp.whatsthecode.library.libContact.ContactDomain
import com.ragicorp.whatsthecode.library.libContact.ContactRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.UUID

class AddContactViewModel(private val contactRepository: ContactRepository) : ViewModel() {
    private val _name = MutableStateFlow("")
    val name = _name.asStateFlow()
    val setName: (String) -> Unit = { viewModelScope.launch { _name.emit(it) } }

    private val _phoneNumber = MutableStateFlow("")
    val phoneNumber = _phoneNumber.asStateFlow()
    val setPhoneNumber: (String) -> Unit = { viewModelScope.launch { _phoneNumber.emit(it) } }

    private val _address = MutableStateFlow("")
    val address = _address.asStateFlow()
    val setAddress: (String) -> Unit = { viewModelScope.launch { _address.emit(it) } }

    private val _apartmentDescription = MutableStateFlow("")
    val apartmentDescription = _apartmentDescription.asStateFlow()
    val setApartmentDescription: (String) -> Unit =
        { viewModelScope.launch { _apartmentDescription.emit(it) } }

    private val _freeText = MutableStateFlow("")
    val freeText = _freeText.asStateFlow()
    val setFreeText: (String) -> Unit = { viewModelScope.launch { _freeText.emit(it) } }

    val isButtonSaveEnabled =
        combine(name, address) { mName, mAddress ->
            mName.isNotBlank() || mAddress.isNotBlank()
        }
            .stateIn(viewModelScope, SharingStarted.Eagerly, false)

    val hasSomethingChanged =
        combine(name, phoneNumber, address, apartmentDescription, freeText) { array ->
            array.any { it.isNotBlank() }
        }
            .stateIn(viewModelScope, SharingStarted.Eagerly, false)

    fun save(possibleColors: List<Int>): UUID {
        val contactId = UUID.randomUUID()

        val contact = ContactDomain(
            id = contactId,
            name = _name.value.trim(),
            phoneNumber = _phoneNumber.value.trim(),
            address = _address.value.trim(),
            apartmentDescription = _apartmentDescription.value.trim(),
            freeText = _freeText.value.trim(),
            color = possibleColors.random()
        )

        viewModelScope.launch {
            contactRepository.addContact(contact)
        }

        return contactId
    }
}
