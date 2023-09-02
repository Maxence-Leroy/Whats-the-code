package com.ragicorp.whatsthecode.ui

import androidx.activity.compose.BackHandler
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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ragicorp.whatsthecode.R
import com.ragicorp.whatsthecode.ui.theme.Spacing
import kotlinx.coroutines.flow.StateFlow
import java.util.UUID

interface ContactModificationViewModel {
    val name: StateFlow<String>
    val setName: (String) -> Unit
    val phoneNumber: StateFlow<String>
    val setPhoneNumber: (String) -> Unit
    val address: StateFlow<String>
    val setAddress: (String) -> Unit
    val codes: StateFlow<List<Pair<String, String>>>
    val addCode: () -> Unit
    val removeCode: (index: Int) -> Unit
    val setCodes: (index: Int, code: Pair<String, String>) -> Unit
    val apartmentDescription: StateFlow<String>
    val setApartmentDescription: (String) -> Unit
    val freeText: StateFlow<String>
    val setFreeText: (String) -> Unit

    val isButtonSaveEnabled: StateFlow<Boolean>
    val hasSomethingChanged: StateFlow<Boolean>

    fun save(color: Int? = null): UUID
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactModificationScreen(
    title: String,
    viewModel: ContactModificationViewModel,
    navigateBack: () -> Unit,
    navigateToContactDetail: (contactId: UUID) -> Unit
) {
    val codes = viewModel.codes.collectAsStateWithLifecycle()
    val hasSomethingBeenFilled: Boolean by viewModel.hasSomethingChanged.collectAsStateWithLifecycle()
    var showAlertDialog: Boolean by remember { mutableStateOf(false) }

    fun onBack() {
        if (hasSomethingBeenFilled) {
            showAlertDialog = true
        } else {
            navigateBack()
        }
    }

    if (showAlertDialog) {
        AlertDialog(
            onDismissRequest = { showAlertDialog = false },
            title = { Text(text = stringResource(R.string.unsavedChangesDialog_title)) },
            text = { Text(text = stringResource(R.string.unsavedChangesDialog_text)) },
            confirmButton = {
                Button(onClick = {
                    showAlertDialog = false
                    navigateBack()
                }) {
                    Text(text = stringResource(R.string.unsavedChangesDialog_confirmButton))
                }
            },
            dismissButton = {
                Button(onClick = { showAlertDialog = false }) {
                    Text(text = stringResource(R.string.unsavedChangesDialog_dismissButton))
                }
            },
        )
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
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
                    val isButtonEnabled: Boolean by viewModel.isButtonSaveEnabled.collectAsStateWithLifecycle()
                    val color = contactAvailableColors().map { it.toArgb() }.random()
                    FilledIconButton(
                        onClick = {
                            val contactId = viewModel.save(color)
                            navigateToContactDetail(contactId)
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
            val previousMaxValue = remember { mutableIntStateOf(scrollState.maxValue) }

            LaunchedEffect(scrollState.maxValue) {
                val newMaxValue = scrollState.maxValue
                val scrollAmount = newMaxValue - previousMaxValue.intValue
                previousMaxValue.intValue = newMaxValue
                scrollState.animateScrollTo(scrollState.value + scrollAmount)
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
                    value = viewModel.name.collectAsStateWithLifecycle().value,
                    onValueChanged = viewModel.setName,
                    label = stringResource(R.string.contact_name),
                    capitalization = KeyboardCapitalization.Words
                )
                WtcTextField(
                    value = viewModel.phoneNumber.collectAsStateWithLifecycle().value,
                    onValueChanged = viewModel.setPhoneNumber,
                    label = stringResource(R.string.contact_phoneNumber),
                    keyboardType = KeyboardType.Phone
                )
                WtcTextField(
                    value = viewModel.address.collectAsStateWithLifecycle().value,
                    onValueChanged = viewModel.setAddress,
                    label = stringResource(R.string.contact_address)
                )

                Surface(
                    color = MaterialTheme.colorScheme.surfaceVariant
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(Spacing.single),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = stringResource(R.string.contact_codes),
                            style = MaterialTheme.typography.titleMedium
                        )
                        for ((index, value) in codes.value.withIndex()) {
                            CodeTextField(
                                index = index,
                                value = value,
                                onValueChanged = { code -> viewModel.setCodes(index, code) },
                                onDeletePressed = { viewModel.removeCode(index) }
                            )
                        }
                        FilledIconButton(
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            onClick = { viewModel.addCode() }
                        ) {
                            Icon(
                                Icons.Default.Add,
                                contentDescription = stringResource(R.string.modifyContact_code_addIconDescription)
                            )
                        }
                    }
                }

                WtcTextField(
                    value = viewModel.apartmentDescription.collectAsStateWithLifecycle().value,
                    onValueChanged = viewModel.setApartmentDescription,
                    label = stringResource(R.string.contact_apartmentDescription)
                )
                WtcTextField(
                    value = viewModel.freeText.collectAsStateWithLifecycle().value,
                    onValueChanged = viewModel.setFreeText,
                    label = stringResource(R.string.contact_freeText),
                    singleLine = false
                )
            }
        }
    )

    BackHandler {
        onBack()
    }
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