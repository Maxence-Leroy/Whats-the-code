package com.ragicorp.whatsthecode.feature.main.importContact

import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ragicorp.whatsthecode.library.libContact.ContactAlreadyExistingException
import com.ragicorp.whatsthecode.library.libContact.ContactDomain
import com.ragicorp.whatsthecode.library.libContact.LibContact
import kotlinx.coroutines.launch

sealed class DialogStatus {
    data object Hidden : DialogStatus()
    data class Visible(val contact: ContactDomain) : DialogStatus()
}

class ImportContactViewModel(private val libContact: LibContact) : ViewModel() {
    private val _alertDialogStatus = mutableStateOf<DialogStatus>(DialogStatus.Hidden)
    val alertDialogStatus: State<DialogStatus> = _alertDialogStatus

    fun handleIntent(
        intentData: Uri?,
        navigateToContactDetail: (String) -> Unit,
        showImportFailedToast: () -> Unit
    ) {
        if (intentData != null) {
            viewModelScope.launch {
                try {
                    val contactId = libContact.importContact(intentData)
                    if (contactId != null) {
                        navigateToContactDetail(contactId.toString())
                    } else {
                        showImportFailedToast()
                    }
                } catch (e: ContactAlreadyExistingException) {
                    _alertDialogStatus.value = DialogStatus.Visible(contact = e.contact)
                }
            }
        }
    }

    fun cancelImport() {
        _alertDialogStatus.value = DialogStatus.Hidden
    }

    fun replaceOldContact(navigateToContactDetail: (String) -> Unit) {
        val alertDialogStatusValue = alertDialogStatus.value
        if (alertDialogStatusValue !is DialogStatus.Visible) return
        val contact = alertDialogStatusValue.contact
        _alertDialogStatus.value = DialogStatus.Hidden
        viewModelScope.launch {
            libContact.replaceContact(contact)
            navigateToContactDetail(contact.id.toString())
        }
    }
}