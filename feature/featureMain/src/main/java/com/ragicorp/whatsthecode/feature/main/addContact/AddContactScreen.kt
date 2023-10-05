package com.ragicorp.whatsthecode.feature.main.addContact

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.ragicorp.whatsthecode.feature.main.R
import com.ragicorp.whatsthecode.feature.main.contactModification.ContactModificationScreen
import org.koin.androidx.compose.getViewModel
import java.util.UUID

internal object AddContact {
    const val Route = "addContact"

    @Composable
    fun Screen(
        addContactViewModel: AddContactViewModel = getViewModel(),
        navigateBack: () -> Unit,
        navigateToContactDetail: (contactId: UUID) -> Unit,
        navigateToAddressSelection: () -> Unit
    ) {
        ContactModificationScreen(
            title = stringResource(R.string.addContact_titleScreen),
            viewModel = addContactViewModel,
            navigateBack = navigateBack,
            navigateToContactDetail = navigateToContactDetail,
            navigateToAddressSelection = navigateToAddressSelection
        )
    }
}
