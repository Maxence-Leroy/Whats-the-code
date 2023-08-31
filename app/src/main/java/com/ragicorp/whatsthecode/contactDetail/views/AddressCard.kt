package com.ragicorp.whatsthecode.contactDetail.views

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.core.content.ContextCompat
import com.ragicorp.whatsthecode.R
import com.ragicorp.whatsthecode.ui.theme.Spacing

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AddressCard(
    address: String,
    apartmentDescription: String
) {
    val clipboardManager: ClipboardManager = LocalClipboardManager.current
    val context = LocalContext.current

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.secondaryContainer)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Spacing.single * 2, vertical = Spacing.single),
            verticalArrangement = Arrangement.spacedBy(Spacing.single)
        ) {
            Text(
                text = stringResource(R.string.contactDetail_address_title),
                style = MaterialTheme.typography.titleSmall
            )
            if (address.isNotBlank()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(Spacing.single * 2)
                ) {
                    Text(
                        modifier = Modifier
                            .weight(1f, fill = true)
                            .combinedClickable(
                                onClick = {},
                                onLongClick = { clipboardManager.setText(AnnotatedString(address)) }
                            ),
                        text = address,
                        style = MaterialTheme.typography.headlineSmall,
                        textAlign = TextAlign.Justify
                    )

                    FilledIconButton(onClick = {
                        val mapIntent = Intent(Intent.ACTION_VIEW)
                        mapIntent.data = Uri.parse("geo:geo:0,0?q=$address")
                        ContextCompat.startActivity(context, mapIntent, null)
                    }) {
                        Icon(
                            Icons.Default.LocationOn,
                            contentDescription = stringResource(R.string.contactDetail_address_mapDescription)
                        )
                    }
                }
            }
            if (address.isNotBlank() && apartmentDescription.isNotBlank()) {
                Divider(color = MaterialTheme.colorScheme.onSecondaryContainer)
            }

            if (apartmentDescription.isNotBlank()) {
                Text(
                    text = apartmentDescription,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
    }
}