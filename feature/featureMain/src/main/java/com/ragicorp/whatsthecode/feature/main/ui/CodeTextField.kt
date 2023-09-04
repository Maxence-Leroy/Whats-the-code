package com.ragicorp.whatsthecode.feature.main.ui

import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ragicorp.whatsthecode.feature.main.R
import com.ragicorp.whatsthecode.feature.main.ui.theme.Spacing
import com.ragicorp.whatsthecode.feature.main.ui.theme.WhatsTheCodeTheme

@Composable
private fun SmallCodeTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChanged: (String) -> Unit,
    label: String,
    placeholder: String,
    icon: @Composable (() -> Unit)
) {
    TextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChanged,
        label = { Text(text = label) },
        placeholder = { Text(text = placeholder) },
        leadingIcon = icon,
        singleLine = false
    )
}

@Composable
fun CodeTextField(
    modifier: Modifier = Modifier,
    index: Int,
    value: Pair<String, String>,
    onValueChanged: (Pair<String, String>) -> Unit,
    onDeletePressed: () -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(Spacing.single * 2),
        verticalAlignment = Alignment.Top
    ) {
        SmallCodeTextField(
            modifier = Modifier.weight(1f, fill = true),
            value = value.first,
            onValueChanged = { onValueChanged(Pair(it, value.second)) },
            label = stringResource(R.string.modifyContact_code_doorLabel, (index + 1)),
            placeholder = stringResource(R.string.modifyContact_code_doorPlaceholder),
            icon = {
                Icon(
                    Icons.Default.Home,
                    contentDescription = stringResource(R.string.modifyContact_code_doorIconDescription)
                )
            }
        )

        SmallCodeTextField(
            modifier = Modifier.weight(1f, fill = true),
            value = value.second,
            onValueChanged = { onValueChanged(Pair(value.first, it)) },
            label = stringResource(R.string.modifyContact_code_codeLabel, (index + 1)),
            placeholder = stringResource(R.string.modifyContact_code_codePlaceholder),
            icon = {
                Icon(
                    Icons.Default.Lock,
                    contentDescription = stringResource(R.string.modifyContact_code_codeIconDescription)
                )
            }
        )

        IconButton(onClick = onDeletePressed) {
            Icon(
                Icons.Default.Delete,
                contentDescription = stringResource(R.string.modifyContact_code_deleteIconDescription)
            )
        }
    }
}

@VisibleForTesting(VisibleForTesting.PRIVATE)
@Composable
@Preview
internal fun CodeTextFieldPreview() {
    WhatsTheCodeTheme {
        Surface {
            Column {
                CodeTextField(
                    modifier = Modifier.padding(8.dp),
                    index = 0,
                    value = Pair("Initial door", "Initial code"),
                    onValueChanged = {},
                    onDeletePressed = {}
                )

                CodeTextField(
                    modifier = Modifier.padding(8.dp),
                    index = 1,
                    value = Pair("", ""),
                    onValueChanged = {},
                    onDeletePressed = {}
                )
            }
        }
    }
}