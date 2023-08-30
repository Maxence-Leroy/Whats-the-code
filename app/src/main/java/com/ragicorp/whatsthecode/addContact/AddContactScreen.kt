package com.ragicorp.whatsthecode.addContact

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
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
                    title = {
                        Text(text = stringResource(R.string.addContact_titleScreen))
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
                        value = addContactViewModel.name,
                        label = stringResource(R.string.contact_name)
                    )
                    WtcTextField(
                        value = addContactViewModel.phoneNumber,
                        label = stringResource(R.string.contact_phoneNumber),
                        keyboardType = KeyboardType.Phone
                    )
                    WtcTextField(
                        value = addContactViewModel.address,
                        label = stringResource(R.string.contact_address)
                    )
                    WtcTextField(
                        value = addContactViewModel.apartmentDescription,
                        label = stringResource(R.string.contact_apartmentDescription)
                    )
                    WtcTextField(
                        value = addContactViewModel.freeText,
                        label = stringResource(R.string.contact_freeText),
                        singleLine = false
                    )
                }
            }
        )
    }
}