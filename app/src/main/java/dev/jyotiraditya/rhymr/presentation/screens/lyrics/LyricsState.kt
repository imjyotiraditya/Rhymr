package dev.jyotiraditya.rhymr.presentation.screens.lyrics

import dev.jyotiraditya.rhymr.data.model.LyricsResponse

data class LyricsState(
    val lyricsResponse: LyricsResponse? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val source: LyricsSource? = null,
    val searched: Boolean = false
)