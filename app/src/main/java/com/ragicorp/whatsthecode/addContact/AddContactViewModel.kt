package com.ragicorp.whatsthecode.addContact

import androidx.lifecycle.viewModelScope
import com.ragicorp.whatsthecode.contactModification.ContactModificationViewModel
import com.ragicorp.whatsthecode.library.libContact.ContactDomain
import com.ragicorp.whatsthecode.library.libContact.ContactRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.UUID

class AddContactViewModel(private val contactRepository: ContactRepository) :
    ContactModificationViewModel() {
    override val isButtonSaveEnabled =
        combine(name, address) { mName, mAddress ->
            mName.isNotBlank() || mAddress.isNotBlank()
        }
            .stateIn(viewModelScope, SharingStarted.Eagerly, false)

    override val hasSomethingChanged: StateFlow<Boolean>
        get() {
            val allButCodesHasChanged =
                combine(name, phoneNumber, address, apartmentDescription, freeText) { array ->
                    array.any { it.isNotBlank() }
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
            name = name.value.trim(),
            phoneNumber = phoneNumber.value.trim(),
            address = address.value.trim(),
            codes = trimCodes(),
            apartmentDescription = apartmentDescription.value.trim(),
            freeText = freeText.value.trim(),
            color = color
        )

        viewModelScope.launch {
            contactRepository.addContact(contact)
        }

        return contactId
    }
}
