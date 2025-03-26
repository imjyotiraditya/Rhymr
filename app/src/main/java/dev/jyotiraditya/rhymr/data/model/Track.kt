package dev.jyotiraditya.rhymr.data.model

data class Track(
    val id: Long,
    val title: String,
    val artist: String,
    val album: String,
    val duration: Long,
    val path: String,
    val albumArtUri: String? = null,
    val size: Long = 0,
    val mimeType: String = ""
)