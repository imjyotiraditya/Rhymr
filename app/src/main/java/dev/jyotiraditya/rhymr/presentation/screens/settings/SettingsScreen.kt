package dev.jyotiraditya.rhymr.presentation.screens.settings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import dev.jyotiraditya.rhymr.R
import dev.jyotiraditya.rhymr.presentation.components.EmptyStateMessage

@Composable
fun SettingsScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        EmptyStateMessage(
            title = stringResource(R.string.settings_coming_soon),
            description = stringResource(R.string.settings_coming_soon_description),
            icon = Icons.Outlined.Settings
        )
    }
}