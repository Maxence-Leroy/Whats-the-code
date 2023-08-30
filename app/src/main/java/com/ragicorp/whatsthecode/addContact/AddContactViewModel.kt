package com.ragicorp.whatsthecode.addContact

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.ragicorp.whatsthecode.library.libContact.ContactRepository

class AddContactViewModel(contactRepository: ContactRepository) : ViewModel() {
    val name = mutableStateOf("")
    val phoneNumber = mutableStateOf("")
    val address = mutableStateOf("")
    val apartmentDescription = mutableStateOf("")
    val freeText = mutableStateOf("")
}
