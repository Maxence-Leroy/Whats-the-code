package com.ragicorp.whatsthecode.contactDetail.views

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import com.ragicorp.whatsthecode.R
import com.ragicorp.whatsthecode.ui.theme.Spacing


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PhoneNumberCard(
    phoneNumber: String
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
                text = stringResource(R.string.contactDetail_phoneNumber_title),
                style = MaterialTheme.typography.titleSmall
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .combinedClickable(
                        onClick = {},
                        onLongClick = { clipboardManager.setText(AnnotatedString(phoneNumber)) }
                    ),
                text = phoneNumber,
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                FilledIconButton(onClick = { /* TODO */ }) {
                    Icon(
                        Icons.Default.Phone,
                        contentDescription = stringResource(R.string.contactDetail_phoneNumber_phoneButtonDescription)
                    )
                }
                FilledIconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        Icons.Default.Send,
                        contentDescription = stringResource(R.string.contactDetail_phoneNumber_sendButtonDescription)
                    )
                }
            }
        }
    }
}