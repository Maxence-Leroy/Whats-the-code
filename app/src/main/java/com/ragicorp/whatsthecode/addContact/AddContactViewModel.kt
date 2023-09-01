package com.ragicorp.whatsthecode.addContact

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ragicorp.whatsthecode.library.libContact.ContactDomain
import com.ragicorp.whatsthecode.library.libContact.ContactRepository
import com.ragicorp.whatsthecode.ui.ContactModificationViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.UUID

class AddContactViewModel(private val contactRepository: ContactRepository) :
    ContactModificationViewModel, ViewModel() {
    private val _name = MutableStateFlow("")
    override val name = _name.asStateFlow()
    override val setName: (String) -> Unit = { viewModelScope.launch { _name.emit(it) } }

    private val _phoneNumber = MutableStateFlow("")
    override val phoneNumber = _phoneNumber.asStateFlow()
    override val setPhoneNumber: (String) -> Unit =
        { viewModelScope.launch { _phoneNumber.emit(it) } }

    private val _address = MutableStateFlow("")
    override val address = _address.asStateFlow()
    override val setAddress: (String) -> Unit = { viewModelScope.launch { _address.emit(it) } }

    private val _apartmentDescription = MutableStateFlow("")
    override val apartmentDescription = _apartmentDescription.asStateFlow()
    override val setApartmentDescription: (String) -> Unit =
        { viewModelScope.launch { _apartmentDescription.emit(it) } }

    private val _freeText = MutableStateFlow("")
    override val freeText = _freeText.asStateFlow()
    override val setFreeText: (String) -> Unit = { viewModelScope.launch { _freeText.emit(it) } }

    override val isButtonSaveEnabled =
        combine(name, address) { mName, mAddress ->
            mName.isNotBlank() || mAddress.isNotBlank()
        }
            .stateIn(viewModelScope, SharingStarted.Eagerly, false)

    override val hasSomethingChanged =
        combine(name, phoneNumber, address, apartmentDescription, freeText) { array ->
            array.any { it.isNotBlank() }
        }
            .stateIn(viewModelScope, SharingStarted.Eagerly, false)

    override fun save(color: Int?): UUID {
        if (color == null) throw IllegalStateException()

        val contactId = UUID.randomUUID()

        val contact = ContactDomain(
            id = contactId,
            name = _name.value.trim(),
            phoneNumber = _phoneNumber.value.trim(),
            address = _address.value.trim(),
            apartmentDescription = _apartmentDescription.value.trim(),
            freeText = _freeText.value.trim(),
            color = color
        )

        viewModelScope.launch {
            contactRepository.addContact(contact)
        }

        return contactId
    }
}
