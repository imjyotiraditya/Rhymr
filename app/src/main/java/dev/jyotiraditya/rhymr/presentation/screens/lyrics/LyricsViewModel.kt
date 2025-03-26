package dev.jyotiraditya.rhymr.presentation.screens.lyrics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.jyotiraditya.rhymr.data.remote.LyricsApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LyricsViewModel : ViewModel() {
    private val _state = MutableStateFlow(LyricsState())
    val state: StateFlow<LyricsState> = _state.asStateFlow()

    fun fetchLyrics(query: String, source: LyricsSource) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true,
                    error = null,
                    searched = true,
                    source = source,
                    lyricsResponse = null
                )
            }

            try {
                val lyricsResponse = when (source) {
                    LyricsSource.SPOTIFY -> LyricsApiService.getLyricsFromSpotify(query)
                    LyricsSource.GENIUS -> LyricsApiService.getLyricsFromGenius(query)
                }

                _state.update {
                    it.copy(
                        isLoading = false,
                        lyricsResponse = lyricsResponse,
                        error = null
                    )
                }
            } catch (error: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = error.message ?: "Failed to fetch lyrics"
                    )
                }
            }
        }
    }

    fun setSource(source: LyricsSource) {
        _state.update {
            it.copy(source = source)
        }
    }

    fun resetState() {
        _state.value = LyricsState()
    }
}