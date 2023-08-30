package com.ragicorp.whatsthecode.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview

sealed class WtcImeAction {
    data object Default : WtcImeAction()
    data object Next : WtcImeAction()
    data object Validate : WtcImeAction()
}

@Composable
fun WtcTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChanged: (String) -> Unit,
    label: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    capitalization: KeyboardCapitalization = KeyboardCapitalization.Sentences,
    singleLine: Boolean = true,
    keyboardAction: WtcImeAction = WtcImeAction.Next
) {
    val focusManager = LocalFocusManager.current

    fun getKeyboardOption(): KeyboardOptions {
        var imeAction = when (keyboardAction) {
            WtcImeAction.Default -> ImeAction.Default
            WtcImeAction.Next -> ImeAction.Next
            WtcImeAction.Validate -> ImeAction.Done
        }

        if (!singleLine) {
            imeAction = ImeAction.None
        }

        return KeyboardOptions(
            keyboardType = keyboardType,
            capitalization = capitalization,
            imeAction = imeAction
        )
    }

    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        value = value,
        onValueChange = onValueChanged,
        label = { Text(text = label) },
        singleLine = singleLine,
        minLines = if (singleLine) 1 else 3,
        keyboardOptions = getKeyboardOption(),
        keyboardActions = KeyboardActions {
            if (keyboardAction == WtcImeAction.Validate) {
                focusManager.clearFocus()
                return@KeyboardActions
            }
            focusManager.moveFocus(FocusDirection.Down)
        }
    )
}

@Preview
@Composable
internal fun WtcTextFieldPreview() {
    WtcTextField(value = "Initial value", onValueChanged = {}, label = "Bonjour")
}