package dev.jyotiraditya.rhymr.presentation.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AudioFile
import androidx.compose.material.icons.outlined.MusicOff
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.jyotiraditya.rhymr.R
import dev.jyotiraditya.rhymr.data.model.Track
import dev.jyotiraditya.rhymr.data.permission.PermissionsManager
import dev.jyotiraditya.rhymr.presentation.components.EmptyStateMessage
import dev.jyotiraditya.rhymr.presentation.screens.home.components.TrackItem

@Composable
fun HomeScreen(
    onTrackSelected: (Track) -> Unit,
    viewModel: HomeViewModel = viewModel()
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val tracks by viewModel.tracks.collectAsState()
    val error by viewModel.error.collectAsState()

    val permissionsManager = remember { PermissionsManager(context) }
    var hasPermissions by remember { mutableStateOf(permissionsManager.arePermissionsGranted()) }

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                hasPermissions = permissionsManager.arePermissionsGranted()
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    LaunchedEffect(hasPermissions) {
        if (hasPermissions) {
            viewModel.loadTracks()
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = stringResource(R.string.title_music_library),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )

        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.Center
        ) {
            if (!hasPermissions) {
                EmptyStateMessage(
                    title = stringResource(R.string.label_permission_required),
                    description = stringResource(R.string.label_permission_required_description),
                    icon = Icons.Outlined.MusicOff
                )
            } else if (error != null) {
                EmptyStateMessage(
                    title = stringResource(R.string.error_loading_music),
                    description = error ?: stringResource(R.string.error_loading_music_description),
                    icon = Icons.Outlined.MusicOff
                )
            } else if (tracks.isEmpty()) {
                EmptyStateMessage(
                    title = stringResource(R.string.label_no_music),
                    description = stringResource(R.string.label_no_music_description),
                    icon = Icons.Outlined.AudioFile
                )
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(vertical = 8.dp)
                ) {
                    items(tracks) { track ->
                        TrackItem(
                            track = track,
                            onTrackClick = {
                                onTrackSelected(it)
                            }
                        )
                    }
                }
            }
        }
    }
}