package com.ragicorp.whatsthecode.feature.main.contactDetail.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import com.ragicorp.whatsthecode.feature.main.ui.InitialsView
import com.ragicorp.whatsthecode.feature.main.ui.InitialsViewSize
import com.ragicorp.whatsthecode.feature.main.ui.theme.Spacing

@Composable
fun ContactImageAndName(
    name: String,
    color: Int
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(Spacing.single * 4)
    ) {
        InitialsView(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            name = name,
            color = Color(color),
            size = InitialsViewSize.Big
        )

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = name,
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Center
        )
    }
}