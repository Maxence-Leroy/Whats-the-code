package com.ragicorp.whatsthecode.feature.main.about

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.ragicorp.whatsthecode.feature.main.R
import com.ragicorp.whatsthecode.feature.main.ui.theme.Spacing
import org.koin.compose.koinInject
import org.koin.core.qualifier.named

internal object About {
    const val Route = "about"

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun Screen(
        version: String = koinInject(qualifier = named("appVersion")),
        onBack: () -> Unit
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    ),
                    title = {
                        Text(text = stringResource(R.string.about_title))
                    },
                    navigationIcon = {
                        IconButton(onClick = { onBack() }) {
                            Icon(
                                Icons.Default.ArrowBack,
                                contentDescription = stringResource(R.string.about_backButtonDescription)
                            )
                        }
                    }
                )
            },
            content = { paddingValues ->
                Column(
                    modifier = Modifier
                        .padding(paddingValues)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(Spacing.single * 3)
                ) {
                    Text(
                        text = stringResource(R.string.about_content),
                        textAlign = TextAlign.Justify
                    )

                    Button(onClick = { /*TODO*/ }) {
                        Text(text = stringResource(R.string.about_emailButton))
                    }

                    Text(
                        text = version,
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }
        )
    }
}