package com.ragicorp.whatsthecode.editContact

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

class EditContactViewModel(contactId: UUID, val contactRepository: ContactRepository) :
    ContactModificationViewModel, ViewModel() {
    private val contact = contactRepository.getContactById(contactId)
        .stateIn(viewModelScope, SharingStarted.Eagerly, null)

    init {
        viewModelScope.launch {
            contact.collect { contact ->
                if (contact != null) {
                    _name.emit(contact.name)
                    _phoneNumber.emit(contact.phoneNumber)
                    _address.emit(contact.address)
                    _apartmentDescription.emit(contact.apartmentDescription)
                    _freeText.emit(contact.freeText)
                }
            }
        }
    }

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

    override val hasSomethingChanged =
        combine(contact, name, phoneNumber, address, apartmentDescription, freeText) { array ->
            val mContact = array[0] as ContactDomain?
            val mName = array[1] as String
            val mPhoneNumber = array[2] as String
            val mAddress = array[3] as String
            val mApartmentDescription = array[4] as String
            val mFreeText = array[5] as String

            ((mContact?.name ?: "") != mName) || ((mContact?.phoneNumber
                ?: "") != mPhoneNumber) || ((mContact?.address
                ?: "") != mAddress) || ((mContact?.apartmentDescription
                ?: "") != mApartmentDescription) || ((mContact?.freeText ?: "") != mFreeText)
        }.stateIn(viewModelScope, SharingStarted.Eagerly, false)

    override val isButtonSaveEnabled =
        combine(hasSomethingChanged, name, address) { mHasSomethingChanged, mName, mAddress ->
            mHasSomethingChanged && (mName.isNotBlank() || mAddress.isNotBlank())
        }.stateIn(viewModelScope, SharingStarted.Eagerly, false)

    override fun save(color: Int?): UUID {
        val contactValue = contact.value ?: throw IllegalStateException()

        val newContact = contactValue.copy(
            name = _name.value.trim(),
            phoneNumber = _phoneNumber.value.trim(),
            address = _address.value.trim(),
            apartmentDescription = _apartmentDescription.value.trim(),
            freeText = _freeText.value.trim()
        )

        viewModelScope.launch {
            contactRepository.editContact(newContact)
        }

        return contactValue.id
    }
}