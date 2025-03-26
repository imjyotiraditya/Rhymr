package dev.jyotiraditya.rhymr.presentation.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.MusicNote
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import dev.jyotiraditya.rhymr.R

data class NavigationItem(
    val route: String,
    val icon: ImageVector,
    @StringRes val label: Int
)

val navigationItems = listOf(
    NavigationItem(
        route = Route.Home.path,
        icon = Icons.Outlined.Home,
        label = R.string.nav_home
    ),
    NavigationItem(
        route = Route.Lyrics.path,
        icon = Icons.Outlined.MusicNote,
        label = R.string.nav_lyrics
    ),
    NavigationItem(
        route = Route.Settings.path,
        icon = Icons.Outlined.Settings,
        label = R.string.nav_settings
    )
)