package com.ragicorp.whatsthecode.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ragicorp.whatsthecode.ui.theme.WhatsTheCodeTheme
import kotlin.math.min

enum class InitialsViewSize(val circleSize: Dp, val fontSize: TextUnit) {
    Big(200.dp, 75.sp),
    Small(40.dp, 12.sp)
}

@Composable
fun InitialsView(
    modifier: Modifier = Modifier,
    name: String,
    color: Color,
    size: InitialsViewSize
) {
    val firstLetters = computeMaxThreeInitials(name)

    Surface(
        modifier = modifier.size(size.circleSize),
        color = color,
        shape = CircleShape
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = firstLetters,
                textAlign = TextAlign.Center,
                fontSize = size.fontSize,
                color = contentColorFor(color)
            )
        }
    }
}

fun computeMaxThreeInitials(
    name: String
): String {
    val firstLetters = name.split(" ").map { it.first() }
    return firstLetters.subList(0, min(firstLetters.size, 3)).joinToString(separator = "")
}

@Composable
@Preview(widthDp = 1250)
fun BigInitialsPreview() {
    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        WhatsTheCodeTheme(darkTheme = false) {
            InitialsView(
                name = "Abc",
                color = MaterialTheme.colorScheme.primary,
                size = InitialsViewSize.Big
            )
            InitialsView(
                name = "Ragi Corp",
                color = MaterialTheme.colorScheme.secondary,
                size = InitialsViewSize.Big
            )
            InitialsView(
                name = "Alfhreh Bfihi Cihgrih",
                color = MaterialTheme.colorScheme.tertiary,
                size = InitialsViewSize.Big
            )
        }
        WhatsTheCodeTheme(darkTheme = true) {
            InitialsView(
                name = "Abc",
                color = MaterialTheme.colorScheme.primary,
                size = InitialsViewSize.Big
            )
            InitialsView(
                name = "Ragi Corp",
                color = MaterialTheme.colorScheme.secondary,
                size = InitialsViewSize.Big
            )
            InitialsView(
                name = "Alfhreh Bfihi Cihgrih",
                color = MaterialTheme.colorScheme.tertiary,
                size = InitialsViewSize.Big
            )
        }
    }
}

@Composable
@Preview
fun SmallInitialsPreview() {
    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        WhatsTheCodeTheme(darkTheme = false) {
            InitialsView(
                name = "Abc",
                color = MaterialTheme.colorScheme.errorContainer,
                size = InitialsViewSize.Small
            )
            InitialsView(
                name = "Ragi Corp",
                color = MaterialTheme.colorScheme.surfaceVariant,
                size = InitialsViewSize.Small
            )
            InitialsView(
                name = "Alfhreh Bfihi Cihgrih",
                color = MaterialTheme.colorScheme.error,
                size = InitialsViewSize.Small
            )
        }

        WhatsTheCodeTheme(darkTheme = true) {
            InitialsView(
                name = "Abc",
                color = MaterialTheme.colorScheme.errorContainer,
                size = InitialsViewSize.Small
            )
            InitialsView(
                name = "Ragi Corp",
                color = MaterialTheme.colorScheme.surfaceVariant,
                size = InitialsViewSize.Small
            )
            InitialsView(
                name = "Alfhreh Bfihi Cihgrih",
                color = MaterialTheme.colorScheme.error,
                size = InitialsViewSize.Small
            )
        }
    }
}