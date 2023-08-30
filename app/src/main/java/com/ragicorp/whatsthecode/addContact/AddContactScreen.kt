package com.ragicorp.whatsthecode.addContact

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ragicorp.whatsthecode.R
import com.ragicorp.whatsthecode.ui.WtcTextField
import com.ragicorp.whatsthecode.ui.theme.Spacing
import org.koin.androidx.compose.getViewModel

internal object AddContact {
    const val Route = "addContact"

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun Screen(addContactViewModel: AddContactViewModel = getViewModel()) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    ),
                    title = {
                        Text(text = stringResource(R.string.addContact_titleScreen))
                    },
                    navigationIcon = {
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(
                                Icons.Default.Close,
                                contentDescription = stringResource(R.string.addContact_leaveButtonDescription)
                            )
                        }
                    },
                    actions = {
                        FilledIconButton(
                            onClick = { /*TODO*/ },
                        ) {
                            Icon(
                                Icons.Default.Done,
                                contentDescription = stringResource(R.string.addContact_saveButtonDescription)
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
                        value = addContactViewModel.name.collectAsStateWithLifecycle().value,
                        onValueChanged = addContactViewModel.setName,
                        label = stringResource(R.string.contact_name)
                    )
                    WtcTextField(
                        value = addContactViewModel.phoneNumber.collectAsStateWithLifecycle().value,
                        onValueChanged = addContactViewModel.setPhoneNumber,
                        label = stringResource(R.string.contact_phoneNumber),
                        keyboardType = KeyboardType.Phone
                    )
                    WtcTextField(
                        value = addContactViewModel.address.collectAsStateWithLifecycle().value,
                        onValueChanged = addContactViewModel.setAddress,
                        label = stringResource(R.string.contact_address)
                    )
                    WtcTextField(
                        value = addContactViewModel.apartmentDescription.collectAsStateWithLifecycle().value,
                        onValueChanged = addContactViewModel.setApartmentDescription,
                        label = stringResource(R.string.contact_apartmentDescription)
                    )
                    WtcTextField(
                        value = addContactViewModel.freeText.collectAsStateWithLifecycle().value,
                        onValueChanged = addContactViewModel.setFreeText,
                        label = stringResource(R.string.contact_freeText),
                        singleLine = false
                    )
                }
            }
        )
    }
}