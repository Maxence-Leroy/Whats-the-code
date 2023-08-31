package com.ragicorp.whatsthecode.contactDetail

import android.os.Bundle
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.ragicorp.whatsthecode.R
import com.ragicorp.whatsthecode.contactDetail.views.DeleteContactConfirmationDialog
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf
import java.util.UUID

object ContactDetail {
    private const val RouteBase = "contactDetail"
    private const val ContactArgument = "contact"
    private const val Route = "$RouteBase?$ContactArgument={$ContactArgument}"

    // Wrapper for type-safety
    fun NavGraphBuilder.contactDetailNavigationEntry(
        navigateBack: () -> Unit
    ) {
        composable(
            Route,
            enterTransition = { slideInHorizontally { it } },
            exitTransition = { slideOutHorizontally { it } },
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
                contactDetailViewModel = getViewModel(
                    parameters = { parametersOf(UUID.fromString(contactId)) }
                ),
                navigateBack = navigateBack
            )
        }
    }


    // Wrapper for type-safety
    fun NavHostController.navigateToContactDetail(
        contactId: String,
        builder: (NavOptionsBuilder.() -> Unit)? = null
    ) {
        navigate("$RouteBase?$ContactArgument=$contactId", builder = builder ?: {})
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun Screen(
        contactDetailViewModel: ContactDetailViewModel,
        navigateBack: () -> Unit
    ) {
        val contact = contactDetailViewModel.contact.collectAsStateWithLifecycle(null)
        var showDeleteContactDialog: Boolean by remember { mutableStateOf(false) }

        if (showDeleteContactDialog) {
            DeleteContactConfirmationDialog(
                onConfirm = {
                    showDeleteContactDialog = false
                    contactDetailViewModel.deleteContact(contact.value)
                    navigateBack()
                },
                onDismiss = { showDeleteContactDialog = false })
        }

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    ),
                    navigationIcon = {
                        IconButton(onClick = { navigateBack() }) {
                            Icon(
                                Icons.Default.ArrowBack,
                                contentDescription = stringResource(R.string.contactDetail_leaveButtonDescription)
                            )
                        }
                    },
                    title = {},
                    actions = {
                        IconButton(onClick = { showDeleteContactDialog = true }) {
                            Icon(
                                Icons.Default.Delete,
                                contentDescription = stringResource(R.string.contactDetail_deleteButtonDescription)
                            )
                        }
                    }
                )
            }
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                color = MaterialTheme.colorScheme.background
            ) {
                Text(text = contact.value?.name ?: "")
            }
        }
    }
}
