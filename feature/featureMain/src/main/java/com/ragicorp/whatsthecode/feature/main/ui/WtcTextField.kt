package com.ragicorp.whatsthecode.feature.main.ui

import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ragicorp.whatsthecode.feature.main.ui.theme.Spacing
import com.ragicorp.whatsthecode.feature.main.ui.theme.WhatsTheCodeTheme

sealed class WtcImeAction {
    data object Default : WtcImeAction()
    data object Next : WtcImeAction()
    data object Validate : WtcImeAction()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WtcTextField(
    modifier: Modifier = Modifier,
    value: TextFieldValue,
    onValueChanged: (TextFieldValue) -> Unit,
    label: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    capitalization: KeyboardCapitalization = KeyboardCapitalization.Sentences,
    singleLine: Boolean = true,
    keyboardAction: WtcImeAction = WtcImeAction.Next,
    onCursorRectChange: ((Rect) -> Unit)? = null,
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

    // Code from OutlinedTextField
    val textStyle = LocalTextStyle.current
    val colors = OutlinedTextFieldDefaults.colors()
    val textColor = MaterialTheme.colorScheme.onSurface
    val mergedTextStyle = textStyle.merge(TextStyle(color = textColor))
    val interactionSource = remember { MutableInteractionSource() }
    val visualTransformation = VisualTransformation.None

    BasicTextField(
        value = value,
        modifier = modifier
            .fillMaxWidth()
            // Merge semantics at the beginning of the modifier chain to ensure padding is
            // considered part of the text field.
            .semantics(mergeDescendants = true) {}
            .padding(top = Spacing.single)
            .defaultMinSize(
                minWidth = OutlinedTextFieldDefaults.MinWidth,
                minHeight = OutlinedTextFieldDefaults.MinHeight
            ),
        onValueChange = onValueChanged,
        textStyle = mergedTextStyle,
        cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
        keyboardOptions = getKeyboardOption(),
        keyboardActions = KeyboardActions {
            if (keyboardAction == WtcImeAction.Validate) {
                focusManager.clearFocus()
                return@KeyboardActions
            }
            focusManager.moveFocus(FocusDirection.Down)
        },
        visualTransformation = visualTransformation,
        interactionSource = interactionSource,
        singleLine = singleLine,
        minLines = if (singleLine) 1 else 3,
        decorationBox = @Composable { innerTextField ->
            OutlinedTextFieldDefaults.DecorationBox(
                value = value.text,
                visualTransformation = visualTransformation,
                innerTextField = innerTextField,
                label = { Text(text = label) },
                singleLine = singleLine,
                enabled = true,
                interactionSource = interactionSource,
                colors = colors,
                container = {
                    OutlinedTextFieldDefaults.ContainerBox(
                        enabled = true,
                        isError = false,
                        interactionSource,
                        colors,
                        OutlinedTextFieldDefaults.shape
                    )
                }
            )
        },
        // Custom code
        onTextLayout = { textLayoutResult ->
            val cursorPosition = textLayoutResult.getCursorRect(value.selection.end)
            if (onCursorRectChange != null) {
                onCursorRectChange(cursorPosition)
            }
        }
    )
}

@VisibleForTesting(VisibleForTesting.PRIVATE)
@Preview
@Composable
internal fun WtcTextFieldPreview() {
    WhatsTheCodeTheme {
        Surface {
            Column {
                WtcTextField(
                    modifier = Modifier.padding(8.dp),
                    singleLine = true,
                    value = TextFieldValue("Initial value"),
                    onValueChanged = {},
                    label = "Hello"
                )
                WtcTextField(
                    modifier = Modifier.padding(8.dp),
                    singleLine = false,
                    value = TextFieldValue("Value on one line"),
                    onValueChanged = {},
                    label = "World"
                )
                WtcTextField(
                    modifier = Modifier.padding(8.dp),
                    singleLine = false,
                    value = TextFieldValue("Value\nOn\nMultiple\n\n\n\nLines"),
                    onValueChanged = {},
                    label = "HYD"
                )
            }
        }
    }
}