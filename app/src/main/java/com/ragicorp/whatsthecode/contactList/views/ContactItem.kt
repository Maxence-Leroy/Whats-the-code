package com.ragicorp.whatsthecode.contactList.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import com.ragicorp.whatsthecode.library.libContact.ContactDomain
import com.ragicorp.whatsthecode.ui.InitialsView
import com.ragicorp.whatsthecode.ui.InitialsViewSize
import com.ragicorp.whatsthecode.ui.theme.Spacing
import com.ragicorp.whatsthecode.ui.theme.WhatsTheCodeTheme
import java.util.UUID

@Composable
internal fun ContactItem(
    modifier: Modifier = Modifier,
    contact: ContactDomain,
    onClick: () -> Unit
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.background,
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.padding(horizontal = Spacing.single * 2, vertical = Spacing.single),
            horizontalArrangement = Arrangement.spacedBy(Spacing.single * 2),
            verticalAlignment = Alignment.CenterVertically
        ) {
            InitialsView(
                name = contact.name,
                color = Color(contact.color),
                size = InitialsViewSize.Small
            )
            Column {
                if (contact.name.isNotBlank()) {
                    Text(
                        text = contact.name,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                if (contact.address.isNotBlank()) {
                    Text(
                        text = contact.address,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
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
                    freeText = "I love free texts!",
                    color = MaterialTheme.colorScheme.primary.toArgb()
                ),
                onClick = {}
            )
        }
    }
}