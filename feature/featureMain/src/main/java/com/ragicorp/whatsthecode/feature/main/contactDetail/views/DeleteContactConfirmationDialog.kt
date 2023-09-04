package com.ragicorp.whatsthecode.feature.main.contactDetail.views

import androidx.annotation.VisibleForTesting
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.ragicorp.whatsthecode.feature.main.R
import com.ragicorp.whatsthecode.feature.main.ui.theme.WhatsTheCodeTheme

@Composable
internal fun DeleteContactConfirmationDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = stringResource(R.string.deleteContactDialog_title)) },
        confirmButton = {
            Button(onClick = onConfirm) {
                Text(text = stringResource(R.string.deleteContactDialog_confirmButton))
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text(text = stringResource(R.string.deleteContactDialog_dismissButton))
            }
        },
    )
}

@VisibleForTesting(VisibleForTesting.PRIVATE)
@Preview
@Composable
internal fun DeleteContactConfirmationDialogPreview() {
    WhatsTheCodeTheme {
        DeleteContactConfirmationDialog({}, {})
    }
}