package com.ragicorp.whatsthecode.feature.main.addressSelection.views

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.ragicorp.whatsthecode.feature.main.ui.theme.Spacing
import com.ragicorp.whatsthecode.feature.main.ui.theme.WhatsTheCodeTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddressSuggestion(
    address: String,
    onClick: (String) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = { onClick(address) }
    ) {
        Text(
            modifier = Modifier.padding(
                horizontal = Spacing.single * 2,
                vertical = Spacing.single
            ),
            text = address
        )
    }
}

@Composable
@Preview
fun AddressSuggestionPreview() {
    WhatsTheCodeTheme {
        AddressSuggestion(address = "55 Rue du Faubourg Saint-Honoré\n75008 Paris", onClick = {})
    }
}