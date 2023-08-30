package com.ragicorp.whatsthecode.contactList.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.ragicorp.whatsthecode.library.libContact.ContactDomain
import com.ragicorp.whatsthecode.ui.theme.WhatsTheCodeTheme
import java.util.UUID

@Composable
internal fun ContactItem(contact: ContactDomain) {
    Text(
        modifier = Modifier.fillMaxWidth(),
        text = contact.name,
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onBackground
    )
}

@Composable
@Preview
private fun ContactItemPreview() {
    WhatsTheCodeTheme {
        Box(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
            ContactItem(
                contact = ContactDomain(
                    id = UUID.randomUUID(),
                    name = "Toto",
                    phoneNumber = "123",
                    address = "Trifouilli-les-Oies",
                    apartmentDescription = "Down the corridor",
                    freeText = "I love free texts!"
                )
            )
        }
    }
}