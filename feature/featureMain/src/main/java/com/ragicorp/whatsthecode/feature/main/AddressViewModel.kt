package com.ragicorp.whatsthecode.feature.main

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AddressViewModel : ViewModel() {
    private val _address = MutableStateFlow(TextFieldValue(""))
    val address = _address.asStateFlow()
    val setAddress: (TextFieldValue) -> Unit = { viewModelScope.launch { _address.emit(it) } }
}