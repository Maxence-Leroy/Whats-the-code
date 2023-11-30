package com.ragicorp.whatsthecode.feature.main.contactList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ragicorp.whatsthecode.corehelpers.removeDiacritics
import com.ragicorp.whatsthecode.library.libContact.ContactDomain
import com.ragicorp.whatsthecode.library.libContact.LibContact
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

enum class OrderBy {
    Name,
    Distance
}

class ContactListViewModel(libContact: LibContact) : ViewModel() {
    private val _order = MutableStateFlow(OrderBy.Name)
    val order = _order.asStateFlow()

    val changeOrder: () -> Unit = {
        viewModelScope.launch {
            when (_order.value) {
                OrderBy.Name -> _order.emit(OrderBy.Distance)
                OrderBy.Distance -> _order.emit(OrderBy.Name)
            }
        }
    }

    private val contacts: Flow<List<ContactDomain>> = libContact.getContacts()
        .combine(order) { contacts, order ->
            when (order) {
                OrderBy.Name -> contacts.sortedBy { it.address.address }.sortedBy { it.name }
                OrderBy.Distance -> contacts // TODO
            }
        }

    private val _contactSearch = MutableStateFlow("")
    val contactSearch = _contactSearch.asStateFlow()
    val setContactSearch: (text: String) -> Unit = { text ->
        viewModelScope.launch {
            _contactSearch.emit(text)
        }
    }

    val filteredContacts = contacts.combine(contactSearch) { contacts, search ->
        val lowerCasedSearch = search.lowercase().removeDiacritics()
        contacts.filter {
            it.name.lowercase().removeDiacritics().contains(lowerCasedSearch) ||
                    it.address.address.lowercase().removeDiacritics().contains(lowerCasedSearch)
        }
    }
}