package com.ragicorp.whatsthecode

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ragicorp.whatsthecode.contactList.ContactList
import com.ragicorp.whatsthecode.ui.theme.WhatsTheCodeTheme
import org.koin.androidx.compose.getViewModel

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
                        ContactList.Screen(contactListViewModel = getViewModel())
                    }
                }
            }
        }
    }
}