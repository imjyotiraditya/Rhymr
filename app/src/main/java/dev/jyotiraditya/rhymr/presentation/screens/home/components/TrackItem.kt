package dev.jyotiraditya.rhymr.presentation.screens.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import dev.jyotiraditya.rhymr.data.model.Track
import dev.jyotiraditya.rhymr.data.utils.extensions.formatDuration
import dev.jyotiraditya.rhymr.presentation.components.TrackCover

@Composable
fun TrackItem(
    track: Track,
    onTrackClick: (Track) -> Unit,
    modifier: Modifier = Modifier
) {
    ListItem(
        modifier = modifier.then(Modifier.clickable { onTrackClick(track) }),
        headlineContent = {
            Text(
                text = track.title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.fillMaxWidth()
            )
        },
        supportingContent = {
            Text(
                text = track.artist,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.fillMaxWidth()
            )
        },
        leadingContent = {
            TrackCover(
                url = track.albumArtUri,
                size = 56.dp
            )
        },
        trailingContent = {
            Text(
                text = track.duration.formatDuration(),
                style = MaterialTheme.typography.bodySmall
            )
        }
    )
}