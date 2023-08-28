package com.ragicorp.whatsthecode

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.ragicorp.whatsthecode.contactList.ContactListScreen
import com.ragicorp.whatsthecode.ui.theme.WhatsTheCodeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WhatsTheCodeTheme {
                ContactListScreen()
            }
        }
    }
}