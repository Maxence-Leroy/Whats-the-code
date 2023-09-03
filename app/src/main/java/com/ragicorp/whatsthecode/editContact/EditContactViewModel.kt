package com.ragicorp.whatsthecode.editContact

import androidx.lifecycle.viewModelScope
import com.ragicorp.whatsthecode.contactModification.ContactModificationViewModel
import com.ragicorp.whatsthecode.library.libContact.ContactDomain
import com.ragicorp.whatsthecode.library.libContact.LibContact
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.UUID

class EditContactViewModel(contactId: UUID, private val libContact: LibContact) :
    ContactModificationViewModel() {
    private val contact = libContact.getContactById(contactId)
        .stateIn(viewModelScope, SharingStarted.Eagerly, null)

    init {
        viewModelScope.launch {
            contact.collect { contact ->
                if (contact != null) {
                    setName(contact.name)
                    setPhoneNumber(contact.phoneNumber)
                    setAddress(contact.address)
                    setCodes(contact.codes)
                    if (contact.codes.isEmpty()) {
                        addCode()
                    }
                    setApartmentDescription(contact.apartmentDescription)
                    setFreeText(contact.freeText)
                }
            }
        }
    }

    override val hasSomethingChanged: StateFlow<Boolean>
        get() {
            val allButCodesHasChanged = combine(
                contact,
                name,
                phoneNumber,
                address,
                apartmentDescription,
                freeText
            ) { array ->
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
            }

            val hasCodesChanged = combine(contact.mapNotNull { it }, codes) { mContact, mCodes ->
                trimCodes(mCodes) != trimCodes(mContact.codes)
            }

            return combine(allButCodesHasChanged, hasCodesChanged) { (allButCodes, code) ->
                allButCodes || code
            }
                .stateIn(viewModelScope, SharingStarted.Eagerly, false)
        }


    override val isButtonSaveEnabled =
        combine(hasSomethingChanged, name, address) { mHasSomethingChanged, mName, mAddress ->
            mHasSomethingChanged && (mName.isNotBlank() || mAddress.isNotBlank())
        }.stateIn(viewModelScope, SharingStarted.Eagerly, false)

    override fun save(color: Int?): UUID {
        val contactValue = contact.value ?: throw IllegalStateException()

        val newContact = contactValue.copy(
            name = name.value.trim(),
            phoneNumber = phoneNumber.value.trim(),
            address = address.value.trim(),
            codes = trimCodes(),
            apartmentDescription = apartmentDescription.value.trim(),
            freeText = freeText.value.trim()
        )

        viewModelScope.launch {
            libContact.editContact(newContact)
        }

        return contactValue.id
    }
}