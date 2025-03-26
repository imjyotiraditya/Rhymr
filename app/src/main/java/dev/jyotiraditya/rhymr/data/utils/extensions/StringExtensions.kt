package dev.jyotiraditya.rhymr.data.utils.extensions

import androidx.compose.ui.text.intl.Locale
import java.util.concurrent.TimeUnit

/**
 * Formats a duration in milliseconds to a human-readable string in the format "mm:ss"
 */
fun Long.formatDuration(): String {
    val minutes = TimeUnit.MILLISECONDS.toMinutes(this)
    val seconds = TimeUnit.MILLISECONDS.toSeconds(this) - TimeUnit.MINUTES.toSeconds(minutes)
    return String.format(java.util.Locale(Locale.current.language), "%d:%02d", minutes, seconds)
}