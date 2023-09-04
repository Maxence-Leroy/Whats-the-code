package com.ragicorp.whatsthecode.feature.main.contactDetail.views

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.ragicorp.whatsthecode.feature.main.R

@Composable
fun FreeTextCard(
    freeText: String
) {
    ContactCard(
        title = stringResource(R.string.contact_freeText)
    ) {
        Text(
            text = freeText,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}