package com.ragicorp.whatsthecode.contactList

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.ragicorp.whatsthecode.R
import com.ragicorp.whatsthecode.contactList.views.ContactItem
import com.ragicorp.whatsthecode.contactList.views.NoContactPlaceholder
import com.ragicorp.whatsthecode.ui.theme.WhatsTheCodeTheme
import org.koin.androidx.compose.getViewModel

internal object ContactList {
    const val Route = "contactList"

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun Screen(
        contactListViewModel: ContactListViewModel = getViewModel()
    ) {
        val contactList = contactListViewModel.contacts.collectAsState(emptyList())
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { /*TODO*/ }) {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = stringResource(R.string.contactList_addButtonDescription)
                    )
                }
            },
            content = {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it)
                ) {
                    if (contactList.value.isEmpty()) {
                        NoContactPlaceholder()
                    } else {
                        LazyColumn(modifier = Modifier.fillMaxSize()) {
                            items(contactList.value) { contact ->
                                ContactItem(contact = contact)
                            }
                        }
                    }
                }
            }
        )
    }
}

@Composable
@Preview(device = Devices.PIXEL_4, showSystemUi = true)
private fun ContactListScreenPreview() {
    WhatsTheCodeTheme {
        ContactList.Screen()
    }
}