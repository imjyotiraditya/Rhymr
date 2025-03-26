package dev.jyotiraditya.rhymr.presentation.screens.lyrics

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AudioFile
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.MusicNote
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.jyotiraditya.rhymr.R
import dev.jyotiraditya.rhymr.data.model.Track
import dev.jyotiraditya.rhymr.presentation.components.EmptyStateMessage
import dev.jyotiraditya.rhymr.presentation.screens.lyrics.components.TrackInfoItem

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LyricsScreen(
    track: Track? = null,
    onNavigateBack: () -> Unit,
    viewModel: LyricsViewModel = viewModel()
) {
    val state by viewModel.state.collectAsState()
    val scrollState = rememberScrollState()
    val keyboardController = LocalSoftwareKeyboardController.current
    var searchQuery by remember { mutableStateOf("") }

    val performSearch = {
        keyboardController?.hide()
        if (searchQuery.isNotEmpty()) {
            viewModel.fetchLyrics(searchQuery, state.source ?: LyricsSource.SPOTIFY)
        }
    }

    BackHandler {
        onNavigateBack()
    }

    DisposableEffect(Unit) {
        onDispose {
            viewModel.resetState()
        }
    }

    LaunchedEffect(Unit) {
        if (state.source == null) {
            viewModel.setSource(LyricsSource.SPOTIFY)
        }
    }

    LaunchedEffect(track) {
        if (track != null) {
            val query = "${track.title} ${track.artist}"
            searchQuery = query
            viewModel.fetchLyrics(query, state.source ?: LyricsSource.SPOTIFY)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        if (track != null) {
            TrackInfoItem(
                track = track,
                modifier = Modifier.fillMaxWidth()
            )
        } else {
            TextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedContainerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainerHigh
                ),
                shape = MaterialTheme.shapes.small,
                placeholder = {
                    Text(
                        text = stringResource(R.string.search_lyrics),
                        style = MaterialTheme.typography.bodyLarge
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Search,
                        contentDescription = stringResource(R.string.cd_search)
                    )
                },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = {
                            searchQuery = ""
                            viewModel.resetState()
                        }) {
                            Icon(
                                imageVector = Icons.Outlined.Clear,
                                contentDescription = stringResource(R.string.cd_clear_search)
                            )
                        }
                    }
                },
                singleLine = true,
                textStyle = MaterialTheme.typography.bodyLarge,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        performSearch()
                    }
                )
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            FilterChip(
                selected = state.source == LyricsSource.SPOTIFY,
                onClick = {
                    viewModel.setSource(LyricsSource.SPOTIFY)
                    if (searchQuery.isNotEmpty()) {
                        viewModel.fetchLyrics(searchQuery, LyricsSource.SPOTIFY)
                        keyboardController?.hide()
                    }
                },
                label = { Text("Spotify") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.AudioFile,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                    selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    selectedLeadingIconColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                modifier = Modifier.padding(end = 8.dp)
            )

            FilterChip(
                selected = state.source == LyricsSource.GENIUS,
                onClick = {
                    viewModel.setSource(LyricsSource.GENIUS)
                    if (searchQuery.isNotEmpty()) {
                        viewModel.fetchLyrics(searchQuery, LyricsSource.GENIUS)
                        keyboardController?.hide()
                    }
                },
                label = { Text("Genius") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Description,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                    selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    selectedLeadingIconColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }

        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

        if (state.lyricsResponse != null) {
            TrackInfoItem(
                track = Track(
                    id = -1,
                    title = state.lyricsResponse!!.title,
                    artist = state.lyricsResponse!!.artist,
                    album = "",
                    duration = 0,
                    path = "",
                    albumArtUri = state.lyricsResponse!!.cover
                )
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(vertical = 8.dp)
        ) {
            when {
                state.isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                state.error != null -> {
                    Text(
                        text = state.error ?: stringResource(R.string.error_loading_lyrics),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .border(
                                width = 1.dp,
                                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(16.dp)
                    )
                }

                state.lyricsResponse != null -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .border(
                                width = 1.dp,
                                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(16.dp)
                            .verticalScroll(scrollState)
                    ) {
                        Text(
                            text = state.lyricsResponse!!.lyrics,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                state.searched && state.lyricsResponse == null -> {
                    EmptyStateMessage(
                        title = stringResource(R.string.no_lyrics_found),
                        description = stringResource(R.string.no_lyrics_found_description),
                        icon = Icons.Outlined.MusicNote
                    )
                }

                else -> {
                    EmptyStateMessage(
                        title = stringResource(R.string.search_instruction),
                        description = stringResource(R.string.search_instruction_description),
                        icon = Icons.Outlined.Search
                    )
                }
            }
        }
    }
}