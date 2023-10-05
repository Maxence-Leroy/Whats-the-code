package com.ragicorp.whatsthecode.feature.main.contactList

import android.content.Intent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.ragicorp.whatsthecode.feature.main.R
import com.ragicorp.whatsthecode.feature.main.contactList.views.ContactItem
import com.ragicorp.whatsthecode.feature.main.contactList.views.NoContactPlaceholder
import com.ragicorp.whatsthecode.feature.main.ui.theme.Spacing
import com.ragicorp.whatsthecode.library.libContact.ContactDomain
import org.koin.androidx.compose.getViewModel

internal object ContactList {
    const val Route = "contactList"

    @OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
    @Composable
    fun Screen(
        contactListViewModel: ContactListViewModel = getViewModel(),
        navigateToAddContact: () -> Unit,
        navigateToContactDetail: (ContactDomain) -> Unit,
        navigateToAboutScreen: () -> Unit
    ) {
        val contactList = contactListViewModel.contacts.collectAsStateWithLifecycle(emptyList())
        val contactSearch = contactListViewModel.contactSearch.collectAsStateWithLifecycle("")
        var isDropDownMenuExpanded by remember { mutableStateOf(false) }
        val context = LocalContext.current

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBar(
                    modifier = Modifier.padding(vertical = Spacing.single),
                    title = {
                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = contactSearch.value,
                            onValueChange = contactListViewModel.setContactSearch,
                            textStyle = MaterialTheme.typography.bodyLarge,
                            placeholder = { Text(text = stringResource(R.string.contactList_filterPlaceholder)) },
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant
                            )
                        )
                    },
                    actions = {
                        IconButton(onClick = { isDropDownMenuExpanded = !isDropDownMenuExpanded }) {
                            Icon(
                                Icons.Default.MoreVert,
                                contentDescription = stringResource(R.string.contactList_dropDownMenu_iconDescription)
                            )
                        }
                    }
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { navigateToAddContact() }) {
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
                            items(
                                contactList.value,
                                key = { contact -> contact.id }
                            ) { contact ->
                                ContactItem(
                                    modifier = Modifier.animateItemPlacement(),
                                    contact = contact,
                                    onClick = { navigateToContactDetail(contact) }
                                )
                            }
                        }
                    }
                    Box(modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(end = Spacing.single)) {
                        DropdownMenu(
                            expanded = isDropDownMenuExpanded,
                            onDismissRequest = { isDropDownMenuExpanded = false },
                        ) {
                            DropdownMenuItem(
                                text = {
                                    Text(text = stringResource(R.string.contactList_dropDownMenu_ossLicenses))
                                }, onClick = {
                                    context.startActivity(
                                        Intent(
                                            context,
                                            OssLicensesMenuActivity::class.java
                                        )
                                    )
                                }
                            )
                            DropdownMenuItem(
                                text = {
                                    Text(text = stringResource(R.string.contactList_dropDownMenu_about))
                                },
                                onClick = {
                                    isDropDownMenuExpanded = false
                                    navigateToAboutScreen()
                                }
                            )
                        }
                    }
                }
            }
        )
    }
}
