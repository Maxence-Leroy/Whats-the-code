package com.ragicorp.whatsthecode.feature.main

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ragicorp.whatsthecode.corehelpers.ActivityProvider
import com.ragicorp.whatsthecode.corehelpers.PermissionsManager
import com.ragicorp.whatsthecode.feature.main.about.About
import com.ragicorp.whatsthecode.feature.main.addContact.AddContact
import com.ragicorp.whatsthecode.feature.main.contactDetail.ContactDetail.contactDetailNavigationEntry
import com.ragicorp.whatsthecode.feature.main.contactDetail.ContactDetail.navigateToContactDetail
import com.ragicorp.whatsthecode.feature.main.contactList.ContactList
import com.ragicorp.whatsthecode.feature.main.editContact.EditContact.editContactNavigationEntry
import com.ragicorp.whatsthecode.feature.main.editContact.EditContact.navigateToEditContact
import com.ragicorp.whatsthecode.feature.main.ui.theme.WhatsTheCodeTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.get

class MainActivity : ComponentActivity() {
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val activityProvider: ActivityProvider = get()
        activityProvider.setActivity(this)

        setContent {
            WhatsTheCodeTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = ContactList.Route,
                    enterTransition = { slideInHorizontally { it } },
                    exitTransition = { fadeOut(animationSpec = tween(700)) },
                    popEnterTransition = { fadeIn(animationSpec = tween(700)) },
                    popExitTransition = { slideOutHorizontally { it } },
                ) {
                    composable(ContactList.Route) {
                        ContactList.Screen(
                            navigateToAddContact = {
                                navController.navigate(AddContact.Route)
                            },
                            navigateToContactDetail = {
                                navController.navigateToContactDetail(it.id.toString())
                            },
                            navigateToAboutScreen = {
                                navController.navigate(About.Route)
                            }
                        )
                    }

                    composable(
                        route = AddContact.Route
                    ) {
                        AddContact.Screen(
                            navigateBack = { navController.popBackStack() },
                            navigateToContactDetail = { contactId ->
                                navController.navigateToContactDetail(contactId.toString()) {
                                    popUpTo(ContactList.Route)
                                }
                            }
                        )
                    }

                    contactDetailNavigationEntry(
                        navigateBack = { navController.popBackStack() },
                        navigateToEditContact = { contactId ->
                            navController.navigateToEditContact(contactId.toString())
                        }
                    )

                    editContactNavigationEntry(navigateBack = { navController.popBackStack() })

                    composable(
                        route = About.Route
                    ) {
                        About.Screen(
                            onBack = { navController.popBackStack() }
                        )
                    }
                }
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        @Suppress("DEPRECATION")
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        val map: Map<String, Boolean> = permissions
            .zip(
                grantResults.map { it == PackageManager.PERMISSION_GRANTED }
            ).toMap()
        coroutineScope.launch {
            get<PermissionsManager>().onPermissionResult(map)
        }
    }
}