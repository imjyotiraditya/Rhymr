package dev.jyotiraditya.rhymr.presentation.navigation

sealed class Route(val path: String) {
    data object Home : Route("home")
    data object Lyrics : Route("lyrics")
    data object Settings : Route("settings")

    companion object {
        fun fromPath(route: String?): Route? = when (route) {
            Home.path -> Home
            Lyrics.path -> Lyrics
            Settings.path -> Settings
            else -> null
        }
    }
}