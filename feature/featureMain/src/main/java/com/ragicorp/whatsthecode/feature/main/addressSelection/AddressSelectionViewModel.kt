package com.ragicorp.whatsthecode.feature.main.addressSelection

import androidx.lifecycle.ViewModel
import com.ragicorp.whatsthecode.feature.main.AddressViewModel
import com.ragicorp.whatsthecode.library.libContact.LibContact
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.sample

class AddressSelectionViewModel(
    libContact: LibContact,
    addressViewModel: AddressViewModel
) : ViewModel() {
    @OptIn(FlowPreview::class)
    val contactSuggestions = addressViewModel
        .address
        .sample(1000)
        .map {
            val text = it.text
            if (text.length >= 4) {
                libContact.getAddressSuggestion(text)
            } else {
                emptyList()
            }
        }
}