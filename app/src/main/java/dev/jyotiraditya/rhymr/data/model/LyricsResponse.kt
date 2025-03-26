package dev.jyotiraditya.rhymr.data.model

import kotlinx.serialization.Serializable

@Serializable
data class LyricsResponse(
    val title: String,
    val artist: String,
    val cover: String? = null,
    val lyrics: String
)