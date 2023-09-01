package com.ragicorp.whatsthecode.editContact

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ragicorp.whatsthecode.library.libContact.ContactRepository
import com.ragicorp.whatsthecode.ui.ContactModificationViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID

class EditContactViewModel(private val contactId: UUID, val contactRepository: ContactRepository) :
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

    override val isButtonSaveEnabled = MutableStateFlow(false)

    override val hasSomethingChanged = MutableStateFlow(false)

    override fun save(color: Int?): UUID {
        return contactId
    }
}