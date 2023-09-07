package com.ragicorp.whatsthecode.feature.main.editContact

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.viewModelScope
import com.ragicorp.whatsthecode.feature.main.contactModification.ContactModificationViewModel
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
                    setName(TextFieldValue(contact.name))
                    setPhoneNumber(TextFieldValue(contact.phoneNumber))
                    setAddress(TextFieldValue(contact.address))
                    setCodes(contact.codes)
                    if (contact.codes.isEmpty()) {
                        addCode()
                    }
                    setApartmentDescription(TextFieldValue(contact.apartmentDescription))
                    setFreeText(TextFieldValue(contact.freeText))
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
                val mName = (array[1] as TextFieldValue).text.trim()
                val mPhoneNumber = (array[2] as TextFieldValue).text.trim()
                val mAddress = (array[3] as TextFieldValue).text.trim()
                val mApartmentDescription = (array[4] as TextFieldValue).text.trim()
                val mFreeText = (array[5] as TextFieldValue).text.trim()

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
            mHasSomethingChanged && (mName.text.isNotBlank() || mAddress.text.isNotBlank())
        }.stateIn(viewModelScope, SharingStarted.Eagerly, false)

    override fun save(color: Int?): UUID {
        val contactValue = contact.value ?: throw IllegalStateException()

        val newContact = contactValue.copy(
            name = name.value.text.trim(),
            phoneNumber = phoneNumber.value.text.trim(),
            address = address.value.text.trim(),
            codes = trimCodes(),
            apartmentDescription = apartmentDescription.value.text.trim(),
            freeText = freeText.value.text.trim()
        )

        viewModelScope.launch {
            libContact.editContact(newContact)
        }

        return contactValue.id
    }
}