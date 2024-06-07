package com.ragicorp.whatsthecode.feature.main.importContact

import android.net.Uri
import android.widget.Toast
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.ragicorp.whatsthecode.feature.main.R
import org.koin.androidx.compose.koinViewModel

@Composable
fun HandleImportContact(
    importContactViewModel: ImportContactViewModel = koinViewModel(),
    intentData: Uri?,
    navigateToContactDetail: (String) -> Unit
) {
    val context = LocalContext.current

    val alertDialogStatus: DialogStatus by importContactViewModel.alertDialogStatus

    LaunchedEffect(intentData) {
        importContactViewModel.handleIntent(
            intentData = intentData,
            navigateToContactDetail = navigateToContactDetail,
            showImportFailedToast = {
                Toast.makeText(
                    context,
                    context.getString(R.string.importContact_importFailed),
                    Toast.LENGTH_SHORT
                ).show()
            }
        )
    }

    if (alertDialogStatus is DialogStatus.Visible) {
        AlertDialog(
            title = { Text(stringResource(R.string.importContact_contactAlreadyExisting)) },
            text = { Text(stringResource(R.string.importContact_text)) },
            confirmButton = {
                Button(onClick = { importContactViewModel.replaceOldContact(navigateToContactDetail = navigateToContactDetail) }) {
                    Text(stringResource(R.string.importContact_confirm))
                }
            },
            dismissButton = {
                Button(onClick = { importContactViewModel.cancelImport() }) {
                    Text(stringResource(R.string.importContact_cancel))
                }
            },
            onDismissRequest = { importContactViewModel.cancelImport() },
        )
    }
}