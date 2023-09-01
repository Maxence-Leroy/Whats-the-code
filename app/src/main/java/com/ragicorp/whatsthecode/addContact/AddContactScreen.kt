package com.ragicorp.whatsthecode.addContact

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.ragicorp.whatsthecode.R
import com.ragicorp.whatsthecode.ui.ContactModificationScreen
import org.koin.androidx.compose.getViewModel
import java.util.UUID

internal object AddContact {
    const val Route = "addContact"

    @Composable
    fun Screen(
        addContactViewModel: AddContactViewModel = getViewModel(),
        navigateBack: () -> Unit,
        navigateToContactDetail: (contactId: UUID) -> Unit
    ) {
        ContactModificationScreen(
            title = stringResource(R.string.addContact_titleScreen),
            viewModel = addContactViewModel,
            navigateBack = navigateBack,
            navigateToContactDetail = navigateToContactDetail
        )
    }
}
