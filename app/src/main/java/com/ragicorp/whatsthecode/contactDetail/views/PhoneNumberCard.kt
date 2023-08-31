package com.ragicorp.whatsthecode.contactDetail.views

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Send
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
import androidx.core.content.ContextCompat
import com.ragicorp.whatsthecode.R


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PhoneNumberCard(
    phoneNumber: String
) {
    val clipboardManager: ClipboardManager = LocalClipboardManager.current
    val context = LocalContext.current

    ContactCard(
        title = stringResource(R.string.contactDetail_phoneNumber_title)
    ) {
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
            FilledIconButton(onClick = {
                val dialIntent = Intent(Intent.ACTION_DIAL)
                dialIntent.data = Uri.parse("tel:$phoneNumber")
                ContextCompat.startActivity(context, dialIntent, null)
            }) {
                Icon(
                    Icons.Default.Phone,
                    contentDescription = stringResource(R.string.contactDetail_phoneNumber_phoneButtonDescription)
                )
            }
            FilledIconButton(onClick = {
                val smsIntent = Intent(Intent.ACTION_SENDTO)
                smsIntent.data = Uri.parse("smsto:$phoneNumber")
                ContextCompat.startActivity(context, smsIntent, null)
            }) {
                Icon(
                    Icons.Default.Send,
                    contentDescription = stringResource(R.string.contactDetail_phoneNumber_sendButtonDescription)
                )
            }
        }
    }
}