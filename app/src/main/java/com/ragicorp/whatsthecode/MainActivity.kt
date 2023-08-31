package com.ragicorp.whatsthecode

import android.content.pm.PackageManager
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

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
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