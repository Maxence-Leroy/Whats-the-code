package com.ragicorp.whatsthecode.feature.main.contactList

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.ragicorp.whatsthecode.corehelpers.PermissionsManager
import com.ragicorp.whatsthecode.corehelpers.removeDiacritics
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

class ContactListViewModel(
    libContact: LibContact,
    application: Application,
    private val permissionManager: PermissionsManager
) :
    AndroidViewModel(application) {
    private val _order = MutableStateFlow(OrderBy.Name)
    val order = _order.asStateFlow()

    val changeOrder: () -> Unit = {
        viewModelScope.launch {
            when (_order.value) {
                OrderBy.Name -> tryToSortByDistance()
                OrderBy.Distance -> _order.emit(OrderBy.Name)
            }
        }
    }

    private suspend fun tryToSortByDistance() {
        if (permissionManager.requestFineLocationPermission()) {
            _order.emit(OrderBy.Distance)
        }
    }

    private val contacts: Flow<List<ContactWithDistance>> = libContact.getContacts()
        .combine(order) { contacts, order ->
            when (order) {
                OrderBy.Name -> contacts.sortedBy { it.address.address }.sortedBy { it.name }
                    .map { ContactWithDistance(it, null) }

                OrderBy.Distance -> contacts.map {
                    val distance = libContact.getDistanceFromCurrentPosition(
                        it,
                        application.applicationContext
                    )
                    ContactWithDistance(it, distance)
                }.sortedWith(compareBy(nullsLast()) { it.distance })
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
            it.contact.name.lowercase().removeDiacritics().contains(lowerCasedSearch) ||
                    it.contact.address.address.lowercase().removeDiacritics()
                        .contains(lowerCasedSearch)
        }
    }
}