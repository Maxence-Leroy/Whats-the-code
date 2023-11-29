package com.ragicorp.whatsthecode.feature.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ragicorp.whatsthecode.library.libContact.PlaceDomain
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AddressViewModel : ViewModel() {
    private val _address = MutableStateFlow(PlaceDomain("", null, null))
    val address = _address.asStateFlow()
    val setAddress: (PlaceDomain) -> Unit = { viewModelScope.launch { _address.emit(it) } }
}