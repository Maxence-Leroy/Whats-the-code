package com.ragicorp.whatsthecode.feature.main.contactList.views

import androidx.annotation.VisibleForTesting
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
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.ragicorp.whatsthecode.feature.main.contactList.ContactWithDistance
import com.ragicorp.whatsthecode.feature.main.ui.InitialsView
import com.ragicorp.whatsthecode.feature.main.ui.InitialsViewSize
import com.ragicorp.whatsthecode.feature.main.ui.theme.Spacing
import com.ragicorp.whatsthecode.feature.main.ui.theme.WhatsTheCodeTheme
import com.ragicorp.whatsthecode.library.libContact.PlaceDomain
import com.ragicorp.whatsthecode.library.libContact.stubContact

@Composable
internal fun ContactItem(
    modifier: Modifier = Modifier,
    contactWithDistance: ContactWithDistance,
    onClick: () -> Unit
) {
    val contact = contactWithDistance.contact
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
            Column {
                InitialsView(
                    name = contact.name,
                    color = Color(contact.color),
                    size = InitialsViewSize.Small
                )
                if (contactWithDistance.distance != null) {
                    Text(
                        modifier = Modifier.align(CenterHorizontally),
                        text = contactWithDistance.printDistance(LocalContext.current) ?: "",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
            Column {
                if (contact.name.isNotBlank()) {
                    Text(
                        text = contact.name,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                if (contact.address.address.isNotBlank()) {
                    Text(
                        text = contact.address.address,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}

@VisibleForTesting(VisibleForTesting.PRIVATE)
@Composable
@Preview
internal fun FullContactItemPreview() {
    WhatsTheCodeTheme {
        Box(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
            ContactItem(
                contactWithDistance = ContactWithDistance(stubContact, null),
                onClick = {}
            )
        }
    }
}

@VisibleForTesting(VisibleForTesting.PRIVATE)
@Composable
@Preview
internal fun ContactWithoutAddressPreview() {
    WhatsTheCodeTheme {
        Box(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
            ContactItem(
                contactWithDistance = ContactWithDistance(
                    stubContact.copy(
                        address = PlaceDomain(
                            "",
                            null,
                            null
                        )
                    ), null
                ),
                onClick = {}
            )
        }
    }
}

@VisibleForTesting(VisibleForTesting.PRIVATE)
@Composable
@Preview
internal fun ContactWithoutNamePreview() {
    WhatsTheCodeTheme {
        Box(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
            ContactItem(
                contactWithDistance = ContactWithDistance(stubContact.copy(name = ""), null),
                onClick = {}
            )
        }
    }
}