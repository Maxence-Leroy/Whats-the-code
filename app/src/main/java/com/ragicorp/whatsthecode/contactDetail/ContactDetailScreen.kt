package com.ragicorp.whatsthecode.contactDetail

import android.os.Bundle
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf
import java.util.UUID

object ContactDetail {
    private const val RouteBase = "contactDetail"
    private const val ContactArgument = "contact"
    private const val Route = "$RouteBase?$ContactArgument={$ContactArgument}"

    // Wrapper for type-safety
    fun NavGraphBuilder.contactDetailNavigationEntry() {
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
                )
            )
        }
    }


    // Wrapper for type-safety
    fun NavHostController.navigateToContactDetail(
        contactId: String,
    ) {
        navigate("$RouteBase?$ContactArgument=$contactId")
    }

    @Composable
    private fun Screen(
        contactDetailViewModel: ContactDetailViewModel
    ) {
        val contact = contactDetailViewModel.contact.collectAsStateWithLifecycle(null)
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Text(text = contact.value?.name ?: "")
        }
    }
}
