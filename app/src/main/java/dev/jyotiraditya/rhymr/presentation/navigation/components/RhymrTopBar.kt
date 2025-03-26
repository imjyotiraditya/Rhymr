package dev.jyotiraditya.rhymr.presentation.navigation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import dev.jyotiraditya.rhymr.R
import dev.jyotiraditya.rhymr.presentation.navigation.Route

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RhymrTopBar(
    currentRoute: Route?,
    onNavigateBack: () -> Unit = {},
    showBackButton: Boolean = false,
    scrollBehavior: TopAppBarScrollBehavior
) {
    TopAppBar(
        title = {
            Text(
                text = when (currentRoute) {
                    is Route.Home -> stringResource(R.string.nav_home)
                    is Route.Lyrics -> stringResource(R.string.nav_lyrics)
                    is Route.Settings -> stringResource(R.string.nav_settings)
                    null -> ""
                },
                style = MaterialTheme.typography.titleLarge
            )
        },
        navigationIcon = {
            if (showBackButton) {
                IconButton(onClick = onNavigateBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = stringResource(R.string.cd_back_button)
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface,
            scrolledContainerColor = MaterialTheme.colorScheme.surface,
            navigationIconContentColor = MaterialTheme.colorScheme.onSurface,
            titleContentColor = MaterialTheme.colorScheme.onSurface
        ),
        scrollBehavior = scrollBehavior
    )
}