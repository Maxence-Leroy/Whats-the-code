package com.ragicorp.whatsthecode

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ragicorp.whatsthecode.addContact.AddContact
import com.ragicorp.whatsthecode.contactDetail.ContactDetail.contactDetailNavigationEntry
import com.ragicorp.whatsthecode.contactDetail.ContactDetail.navigateToContactDetail
import com.ragicorp.whatsthecode.contactList.ContactList
import com.ragicorp.whatsthecode.ui.theme.WhatsTheCodeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WhatsTheCodeTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = ContactList.Route
                ) {
                    composable(ContactList.Route) {
                        ContactList.Screen(
                            navigateToAddContact = {
                                navController.navigate(AddContact.Route)
                            },
                            navigateToContactDetail = {
                                navController.navigateToContactDetail(it.id.toString())
                            }
                        )
                    }

                    composable(
                        route = AddContact.Route,
                        enterTransition = { slideInHorizontally { it } },
                        exitTransition = null,
                        popEnterTransition = null,
                        popExitTransition = { slideOutHorizontally { it } }
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
                        navigateBack = { navController.popBackStack() }
                    )
                }
            }
        }
    }
}