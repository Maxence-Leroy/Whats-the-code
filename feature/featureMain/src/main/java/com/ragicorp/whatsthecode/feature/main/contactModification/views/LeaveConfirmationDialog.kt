package com.ragicorp.whatsthecode.feature.main.contactModification.views

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.ragicorp.whatsthecode.feature.main.R

@Composable
fun LeaveConfirmationDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = stringResource(R.string.unsavedChangesDialog_title)) },
        text = { Text(text = stringResource(R.string.unsavedChangesDialog_text)) },
        confirmButton = {
            Button(onClick = onConfirm) {
                Text(text = stringResource(R.string.unsavedChangesDialog_confirmButton))
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text(text = stringResource(R.string.unsavedChangesDialog_dismissButton))
            }
        },
    )
}