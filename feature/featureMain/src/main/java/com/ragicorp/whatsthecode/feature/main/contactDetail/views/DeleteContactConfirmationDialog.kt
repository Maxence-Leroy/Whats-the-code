package com.ragicorp.whatsthecode.feature.main.contactDetail.views

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.ragicorp.whatsthecode.feature.main.R

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

@Preview
@Composable
private fun DeleteContactConfirmationDialogPreview() {
    DeleteContactConfirmationDialog({}, {})
}