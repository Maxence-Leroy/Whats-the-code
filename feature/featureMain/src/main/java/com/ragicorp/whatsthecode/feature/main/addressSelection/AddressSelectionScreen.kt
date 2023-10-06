package com.ragicorp.whatsthecode.feature.main.addressSelection

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ragicorp.whatsthecode.feature.main.AddressViewModel
import com.ragicorp.whatsthecode.feature.main.R
import com.ragicorp.whatsthecode.feature.main.addressSelection.views.AddressSuggestion
import com.ragicorp.whatsthecode.feature.main.ui.theme.Spacing
import org.koin.androidx.compose.getViewModel

object AddressSelection {
    const val Route = "addressSelection"

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun Screen(
        onBack: () -> Unit,
        addressViewModel: AddressViewModel,
        addressSelectionViewModel: AddressSelectionViewModel = getViewModel()
    ) {
        val focusRequester = remember { FocusRequester() }
        var enteredAddress: TextFieldValue by remember { mutableStateOf(addressViewModel.address.value) }
        val suggestions = addressSelectionViewModel.contactSuggestions.collectAsStateWithLifecycle(
            emptyList()
        )

        LaunchedEffect(null) {
            focusRequester.requestFocus()
        }
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {},
                    navigationIcon = {
                        IconButton(onClick = onBack) {
                            Icon(
                                Icons.Default.ArrowBack,
                                contentDescription = stringResource(R.string.addressSelection_backButtonDescription)
                            )
                        }
                    }
                )
            }
        ) {
            Column(
                modifier = Modifier
                    .padding(it)
                    .padding(Spacing.screen),
                verticalArrangement = Arrangement.spacedBy(Spacing.single * 2)
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusRequester),
                    value = enteredAddress,
                    onValueChange = { value ->
                        enteredAddress = value
                        addressViewModel.setAddress(value)
                    },
                    label = { Text(stringResource(R.string.contact_address)) },
                    textStyle = MaterialTheme.typography.bodyLarge,
                    singleLine = true
                )

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(Spacing.single)
                ) {
                    items(suggestions.value) { suggestion ->
                        AddressSuggestion(
                            address = suggestion,
                            onClick = { address ->
                                enteredAddress = TextFieldValue(address)
                                addressViewModel.setAddress(TextFieldValue(address))
                                onBack()
                            }
                        )
                    }
                }
            }
        }
    }
}
