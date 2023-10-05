package com.ragicorp.whatsthecode.feature.main.contactModification

import androidx.annotation.VisibleForTesting
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID

abstract class ContactModificationViewModel : ViewModel() {
    private val _name = MutableStateFlow(TextFieldValue(""))
    val name = _name.asStateFlow()
    val setName: (TextFieldValue) -> Unit = { viewModelScope.launch { _name.emit(it) } }

    private val _phoneNumber = MutableStateFlow(TextFieldValue(""))
    val phoneNumber = _phoneNumber.asStateFlow()
    val setPhoneNumber: (TextFieldValue) -> Unit =
        { viewModelScope.launch { _phoneNumber.emit(it) } }

    private val _codes = MutableStateFlow(listOf(Pair("", "")))
    val codes = _codes.asStateFlow()
    val addCode: () -> Unit = {
        viewModelScope.launch { _codes.emit(_codes.value + Pair("", "")) }
    }
    val removeCode: (Int) -> Unit = { index ->
        val list = _codes.value.toMutableList()
        list.removeAt(index)
        viewModelScope.launch { _codes.emit(list) }
    }

    protected suspend fun setCodes(codes: List<Pair<String, String>>) {
        _codes.emit(codes)
    }

    val setCodes: (Int, Pair<String, String>) -> Unit = { index, code ->
        val list = _codes.value.toMutableList()
        list[index] = code
        viewModelScope.launch { _codes.emit(list) }
    }

    private val _apartmentDescription = MutableStateFlow(TextFieldValue(""))
    val apartmentDescription = _apartmentDescription.asStateFlow()
    val setApartmentDescription: (TextFieldValue) -> Unit =
        { viewModelScope.launch { _apartmentDescription.emit(it) } }

    private val _freeText = MutableStateFlow(TextFieldValue(""))
    val freeText = _freeText.asStateFlow()
    val setFreeText: (TextFieldValue) -> Unit = { viewModelScope.launch { _freeText.emit(it) } }

    open val isButtonSaveEnabled: StateFlow<Boolean>
        get() = throw NotImplementedError()

    open val hasSomethingChanged: StateFlow<Boolean>
        get() = throw NotImplementedError()

    open fun save(color: Int? = null): UUID {
        throw NotImplementedError()
    }

    @VisibleForTesting(VisibleForTesting.PROTECTED)
    internal fun trimCodes(codesValue: (List<Pair<String, String>>)? = null): List<Pair<String, String>> {
        return (codesValue ?: codes.value)
            .map { Pair(it.first.trim(), it.second.trim()) }
            .filter { it.first.isNotBlank() || it.second.isNotBlank() }
    }
}