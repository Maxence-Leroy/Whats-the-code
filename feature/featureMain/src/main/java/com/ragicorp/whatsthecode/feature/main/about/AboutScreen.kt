package com.ragicorp.whatsthecode.feature.main.about

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.core.content.ContextCompat
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
        val context = LocalContext.current

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
                        .padding(
                            horizontal = Spacing.single * 2,
                            vertical = Spacing.single
                        )
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(Spacing.single * 3),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(R.string.about_content),
                    )

                    Button(onClick = {
                        val mailIntent = Intent(Intent.ACTION_SENDTO)
                        mailIntent.data = Uri.parse("mailto:ragicorp@gmail.com")
                        ContextCompat.startActivity(context, mailIntent, null)
                    }) {
                        Text(text = stringResource(R.string.about_emailButton))
                    }

                    Text(
                        modifier = Modifier.align(Alignment.Start),
                        text = version,
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }
        )
    }
}