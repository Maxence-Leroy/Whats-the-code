package com.ragicorp.whatsthecode.addContact

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ragicorp.whatsthecode.library.libContact.ContactRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AddContactViewModel(contactRepository: ContactRepository) : ViewModel() {
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
}
