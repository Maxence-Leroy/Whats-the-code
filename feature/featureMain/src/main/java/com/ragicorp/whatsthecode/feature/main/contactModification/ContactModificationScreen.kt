package com.ragicorp.whatsthecode.feature.main.contactModification

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ragicorp.whatsthecode.feature.main.AddressViewModel
import com.ragicorp.whatsthecode.feature.main.contactModification.views.ContactModificationScaffold
import com.ragicorp.whatsthecode.feature.main.contactModification.views.LeaveConfirmationDialog
import java.util.UUID

@Composable
fun ContactModificationScreen(
    title: String,
    viewModel: ContactModificationViewModel,
    addressViewModel: AddressViewModel,
    navigateBack: () -> Unit,
    navigateToContactDetail: (contactId: UUID) -> Unit,
    navigateToAddressSelection: () -> Unit
) {
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
        LeaveConfirmationDialog(
            onDismiss = { showAlertDialog = false },
            onConfirm = {
                showAlertDialog = false
                navigateBack()
            }
        )
    }

    ContactModificationScaffold(
        title = title,
        onBack = { onBack() },
        onSave = { color ->
            val contactId = viewModel.save(color)
            navigateToContactDetail(contactId)
        },
        isButtonSaveEnabled = viewModel.isButtonSaveEnabled,
        name = viewModel.name,
        setName = viewModel.setName,
        phoneNumber = viewModel.phoneNumber,
        setPhoneNumber = viewModel.setPhoneNumber,
        address = addressViewModel.address,
        setAddress = addressViewModel.setAddress,
        openAddressSelection = navigateToAddressSelection,
        codes = viewModel.codes,
        addCode = viewModel.addCode,
        removeCode = viewModel.removeCode,
        setCodes = viewModel.setCodes,
        apartmentDescription = viewModel.apartmentDescription,
        setApartmentDescription = viewModel.setApartmentDescription,
        freeText = viewModel.freeText,
        setFreeText = viewModel.setFreeText
    )

    BackHandler {
        onBack()
    }
}