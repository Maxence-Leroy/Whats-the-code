package com.ragicorp.whatsthecode.feature.main.editContact

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.ragicorp.whatsthecode.feature.main.AddressViewModel
import com.ragicorp.whatsthecode.feature.main.R
import com.ragicorp.whatsthecode.feature.main.contactModification.ContactModificationScreen
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import java.util.UUID

internal object EditContact {
    private const val RouteBase = "editContact"
    private const val ContactArgument = "contact"
    private const val Route = "$RouteBase?$ContactArgument={$ContactArgument}"

    // Wrapper for type-safety
    fun NavGraphBuilder.editContactNavigationEntry(
        navigateBack: () -> Unit,
        navigateToAddressSelection: () -> Unit,
        addressViewModel: AddressViewModel
    ) {
        composable(
            Route,
            arguments = listOf(
                navArgument(ContactArgument) {
                    type = NavType.StringType
                    defaultValue = ""
                },
            )
        ) {
            val args: Bundle = it.arguments ?: throw IllegalArgumentException()
            val contactId = args.getString(ContactArgument) ?: throw IllegalStateException()
            Screen(
                editContactViewModel = koinViewModel(
                    parameters = { parametersOf(UUID.fromString(contactId)) }
                ),
                navigateBack = navigateBack,
                navigateToAddressSelection = navigateToAddressSelection,
                addressViewModel = addressViewModel
            )
        }
    }


    // Wrapper for type-safety
    fun NavHostController.navigateToEditContact(
        contactId: String
    ) {
        navigate("${RouteBase}?${ContactArgument}=$contactId")
    }


    @Composable
    fun Screen(
        editContactViewModel: EditContactViewModel,
        navigateBack: () -> Unit,
        navigateToAddressSelection: () -> Unit,
        addressViewModel: AddressViewModel
    ) {
        ContactModificationScreen(
            title = stringResource(R.string.editContact_titleScreen),
            viewModel = editContactViewModel,
            navigateBack = navigateBack,
            navigateToContactDetail = { _ -> navigateBack() },
            navigateToAddressSelection = navigateToAddressSelection,
            addressViewModel = addressViewModel
        )
    }
}