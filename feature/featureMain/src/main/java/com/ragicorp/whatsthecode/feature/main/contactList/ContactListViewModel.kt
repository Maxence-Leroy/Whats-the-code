package com.ragicorp.whatsthecode.feature.main.contactList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ragicorp.whatsthecode.library.libContact.ContactDomain
import com.ragicorp.whatsthecode.library.libContact.LibContact
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class ContactListViewModel(libContact: LibContact) : ViewModel() {
    val contacts: Flow<List<ContactDomain>> = libContact
        .getContacts()
        .map { contacts ->
            contacts.sortedBy { it.address }.sortedBy { it.name }
        }

    private val _contactSearch = MutableStateFlow("")
    val contactSearch = _contactSearch.asStateFlow()
    val setContactSearch: (text: String) -> Unit = { text ->
        viewModelScope.launch {
            _contactSearch.emit(text)
        }
    }
}