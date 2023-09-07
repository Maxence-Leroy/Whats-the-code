package com.ragicorp.whatsthecode.feature.main.addContact

import androidx.lifecycle.viewModelScope
import com.ragicorp.whatsthecode.feature.main.contactModification.ContactModificationViewModel
import com.ragicorp.whatsthecode.library.libContact.ContactDomain
import com.ragicorp.whatsthecode.library.libContact.LibContact
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.UUID

class AddContactViewModel(private val libContact: LibContact) :
    ContactModificationViewModel() {
    override val isButtonSaveEnabled =
        combine(name, address) { mName, mAddress ->
            mName.text.isNotBlank() || mAddress.text.isNotBlank()
        }
            .stateIn(viewModelScope, SharingStarted.Eagerly, false)

    override val hasSomethingChanged: StateFlow<Boolean>
        get() {
            val allButCodesHasChanged =
                combine(name, phoneNumber, address, apartmentDescription, freeText) { array ->
                    array.any { it.text.isNotBlank() }
                }

            val hasCodesChanged = codes
                .map { trimCodes(it) }
                .map { it.isNotEmpty() }

            return combine(allButCodesHasChanged, hasCodesChanged) { (allButCodes, code) ->
                allButCodes || code
            }
                .stateIn(viewModelScope, SharingStarted.Eagerly, false)
        }


    override fun save(color: Int?): UUID {
        if (color == null) throw IllegalStateException()

        val contactId = UUID.randomUUID()

        val contact = ContactDomain(
            id = contactId,
            name = name.value.text.trim(),
            phoneNumber = phoneNumber.value.text.trim(),
            address = address.value.text.trim(),
            codes = trimCodes(),
            apartmentDescription = apartmentDescription.value.text.trim(),
            freeText = freeText.value.text.trim(),
            color = color
        )

        viewModelScope.launch {
            libContact.addContact(contact)
        }

        return contactId
    }
}
