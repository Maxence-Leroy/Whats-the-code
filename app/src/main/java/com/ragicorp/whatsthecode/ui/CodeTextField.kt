package com.ragicorp.whatsthecode.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.ragicorp.whatsthecode.R
import com.ragicorp.whatsthecode.ui.theme.Spacing

@Composable
private fun SmallCodeTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChanged: (String) -> Unit,
    label: String,
    placeholder: String
) {

}

@Composable
fun CodeTextField(
    index: Int,
    value: Pair<String, String>,
    onValueChanged: (Pair<String, String>) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(Spacing.single * 2),
        verticalAlignment = Alignment.Top
    ) {
        SmallCodeTextField(
            value = value.first,
            onValueChanged = { onValueChanged(Pair(it, value.second)) },
            label = stringResource(R.string),
            placeholder =
        )
    }
}
