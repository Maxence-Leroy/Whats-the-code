package com.ragicorp.whatsthecode.addContact

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import com.ragicorp.whatsthecode.library.libContact.ContactRepository

class AddContactViewModel(contactRepository: ContactRepository) : ViewModel() {
    val name = mutableStateOf(TextFieldValue(""))
    val phoneNumber = mutableStateOf(TextFieldValue(""))
    val address = mutableStateOf(TextFieldValue(""))
    val apartmentDescription = mutableStateOf(TextFieldValue(""))
    val freeText = mutableStateOf(TextFieldValue(""))
}
