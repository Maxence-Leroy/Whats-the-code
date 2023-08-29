package com.ragicorp.whatsthecode.addContact

import androidx.compose.runtime.Composable
import org.koin.androidx.compose.getViewModel

internal object AddContact {
    const val Route = "addContact"

    @Composable
    fun Screen(addContactViewModel: AddContactViewModel = getViewModel()) {

    }
}