package com.ragicorp.whatsthecode.feature.main.contactModification.views

import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ragicorp.whatsthecode.feature.main.R
import com.ragicorp.whatsthecode.feature.main.ui.CodeTextField
import com.ragicorp.whatsthecode.feature.main.ui.WtcTextField
import com.ragicorp.whatsthecode.feature.main.ui.theme.Spacing
import com.ragicorp.whatsthecode.feature.main.ui.theme.WhatsTheCodeTheme
import com.ragicorp.whatsthecode.library.libContact.PlaceDomain
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactModificationScaffold(
    modifier: Modifier = Modifier,
    title: String,
    onBack: () -> Unit,
    onSave: (Int) -> Unit,
    isButtonSaveEnabled: StateFlow<Boolean>,
    name: StateFlow<TextFieldValue>,
    setName: (TextFieldValue) -> Unit,
    phoneNumber: StateFlow<TextFieldValue>,
    setPhoneNumber: (TextFieldValue) -> Unit,
    address: StateFlow<PlaceDomain>,
    setAddress: (PlaceDomain) -> Unit,
    openAddressSelection: () -> Unit,
    codes: StateFlow<List<Pair<String, String>>>,
    addCode: () -> Unit,
    removeCode: (Int) -> Unit,
    setCodes: (Int, Pair<String, String>) -> Unit,
    apartmentDescription: StateFlow<TextFieldValue>,
    setApartmentDescription: (TextFieldValue) -> Unit,
    freeText: StateFlow<TextFieldValue>,
    setFreeText: (TextFieldValue) -> Unit
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                ),
                title = {
                    Text(text = title)
                },
                navigationIcon = {
                    IconButton(onClick = { onBack() }) {
                        Icon(
                            Icons.Default.Close,
                            contentDescription = stringResource(R.string.modifyContact_leaveButtonDescription)
                        )
                    }
                },
                actions = {
                    val isButtonEnabled: Boolean by isButtonSaveEnabled.collectAsStateWithLifecycle()
                    val color = contactAvailableColors().map { it.toArgb() }.random()
                    FilledIconButton(
                        onClick = {
                            onSave(color)
                        },
                        enabled = isButtonEnabled
                    ) {
                        Icon(
                            Icons.Default.Done,
                            contentDescription = stringResource(R.string.modifyContact_saveButtonDescription)
                        )
                    }
                }
            )
        },
        content = {
            val scrollState = rememberScrollState()
            val coroutineScope = rememberCoroutineScope()
            val beforeLastTextFieldPosition = remember { mutableFloatStateOf(0f) }
            val lastTextFieldPosition = remember { mutableFloatStateOf(0f) }

            fun scrollTo(offset: Float) {
                coroutineScope.launch {
                    scrollState.animateScrollTo(offset.toInt())
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .imePadding()
                    .padding(it)
                    .verticalScroll(scrollState)
                    .padding(Spacing.screen),
                verticalArrangement = Arrangement.spacedBy(Spacing.single)
            ) {
                WtcTextField(
                    value = name.collectAsStateWithLifecycle().value,
                    onValueChanged = setName,
                    label = stringResource(R.string.contact_name),
                    capitalization = KeyboardCapitalization.Words
                )
                WtcTextField(
                    value = phoneNumber.collectAsStateWithLifecycle().value,
                    onValueChanged = setPhoneNumber,
                    label = stringResource(R.string.contact_phoneNumber),
                    keyboardType = KeyboardType.Phone
                )
                WtcTextField(
                    modifier = Modifier.onFocusChanged {
                        if (it.isFocused) {
                            openAddressSelection()
                        }
                    },
                    value = TextFieldValue(address.collectAsStateWithLifecycle().value.address),
                    onValueChanged = { setAddress(PlaceDomain(it.text, null, null)) },
                    label = stringResource(R.string.contact_address)
                )

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(Spacing.single),
                        verticalArrangement = Arrangement.spacedBy(Spacing.single * 2),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = stringResource(R.string.contact_codes),
                            style = MaterialTheme.typography.titleMedium
                        )
                        for ((index, value) in codes.collectAsStateWithLifecycle().value.withIndex()) {
                            CodeTextField(
                                index = index,
                                value = value,
                                onValueChanged = { code -> setCodes(index, code) },
                                onDeletePressed = { removeCode(index) }
                            )
                        }
                        FilledIconButton(
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            onClick = { addCode() }
                        ) {
                            Icon(
                                Icons.Default.Add,
                                contentDescription = stringResource(R.string.modifyContact_code_addIconDescription)
                            )
                        }
                    }
                }

                WtcTextField(
                    modifier = Modifier.onGloballyPositioned { layout ->
                        beforeLastTextFieldPosition.floatValue = layout.positionInParent().y
                    },
                    value = apartmentDescription.collectAsStateWithLifecycle().value,
                    onValueChanged = setApartmentDescription,
                    label = stringResource(R.string.contact_apartmentDescription),
                    onCursorRectChange = { rect ->
                        scrollTo(beforeLastTextFieldPosition.floatValue + rect.top)
                    }
                )
                WtcTextField(
                    modifier = Modifier.onGloballyPositioned { layout ->
                        lastTextFieldPosition.floatValue = layout.positionInParent().y
                    },
                    value = freeText.collectAsStateWithLifecycle().value,
                    onValueChanged = setFreeText,
                    label = stringResource(R.string.contact_freeText),
                    singleLine = false,
                    onCursorRectChange = { rect ->
                        scrollTo(lastTextFieldPosition.floatValue + rect.top)
                    }
                )
            }
        }
    )
}

@Composable
private fun contactAvailableColors(): List<Color> =
    listOf(
        MaterialTheme.colorScheme.primary,
        MaterialTheme.colorScheme.secondary,
        MaterialTheme.colorScheme.tertiary,
        MaterialTheme.colorScheme.error,
        MaterialTheme.colorScheme.errorContainer,
        MaterialTheme.colorScheme.surfaceVariant
    )

@VisibleForTesting(VisibleForTesting.PRIVATE)
@Composable
@Preview
fun FilledContactModificationScaffoldPreview() {
    WhatsTheCodeTheme {
        ContactModificationScaffold(
            title = "Full Preview",
            onBack = {},
            onSave = {},
            isButtonSaveEnabled = MutableStateFlow(true),
            name = MutableStateFlow(TextFieldValue("Emmanuel Macron")),
            setName = {},
            phoneNumber = MutableStateFlow(TextFieldValue("+33123456789")),
            setPhoneNumber = {},
            address = MutableStateFlow(
                PlaceDomain(
                    "55 Rue du Faubourg Saint-Honoré, 75008 Paris",
                    null,
                    null
                )
            ),
            setAddress = {},
            openAddressSelection = {},
            codes = MutableStateFlow(
                listOf(
                    Pair("Fence", "1234"),
                    Pair("Nuclear weapons", "5678")
                )
            ),
            addCode = {},
            removeCode = {},
            setCodes = { _, _ -> },
            apartmentDescription = MutableStateFlow(TextFieldValue("Biggest room of the palace")),
            setApartmentDescription = {},
            freeText = MutableStateFlow(TextFieldValue("Because he is the boss")),
            setFreeText = {}
        )
    }
}

@VisibleForTesting(VisibleForTesting.PRIVATE)
@Composable
@Preview
fun EmptyContactModificationScaffoldPreview() {
    WhatsTheCodeTheme {
        ContactModificationScaffold(
            title = "Empty Preview",
            onBack = {},
            onSave = {},
            isButtonSaveEnabled = MutableStateFlow(false),
            name = MutableStateFlow(TextFieldValue("")),
            setName = {},
            phoneNumber = MutableStateFlow(TextFieldValue("")),
            setPhoneNumber = {},
            address = MutableStateFlow(PlaceDomain("", null, null)),
            setAddress = {},
            openAddressSelection = {},
            codes = MutableStateFlow(emptyList()),
            addCode = {},
            removeCode = {},
            setCodes = { _, _ -> },
            apartmentDescription = MutableStateFlow(TextFieldValue("")),
            setApartmentDescription = {},
            freeText = MutableStateFlow(TextFieldValue("")),
            setFreeText = {}
        )
    }
}