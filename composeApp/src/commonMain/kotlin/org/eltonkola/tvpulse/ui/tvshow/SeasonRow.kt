package org.eltonkola.tvpulse.ui.tvshow

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import com.composables.icons.lucide.ChevronDown
import com.composables.icons.lucide.ChevronUp
import com.composables.icons.lucide.CircleCheck
import com.composables.icons.lucide.Lucide
import org.eltonkola.tvpulse.data.remote.model.Episode
import org.eltonkola.tvpulse.data.remote.model.Season
import org.eltonkola.tvpulse.ui.components.ProgressUi

@Composable
fun SeasonRow(
    season: Season
) {
    var isExpanded by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Title Row
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
                .clickable { isExpanded = !isExpanded }
                .padding(8.dp)
        ) {
            Text(
                text = "Season ${season.episodes.first().season_number}",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
            )

            IconButton(onClick = {
                isExpanded = !isExpanded
            }) {
                Icon(
                    imageVector = if (isExpanded) Lucide.ChevronUp else Lucide.ChevronDown,
                    contentDescription = if (isExpanded) "Collapse" else "Expand"
                )
            }

            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "${season.episodes.filter { it.isWatched }.size} /${season.episodes.size}",
                style = MaterialTheme.typography.titleMedium,
            )
            IconButton(onClick = {

            }) {
                Icon(imageVector = Lucide.CircleCheck, contentDescription = "Watched")
            }
        }
        ProgressUi(
            progress = season.episodes.filter { it.isWatched }.size.toFloat() / season.episodes.size,
            modifier = Modifier.fillMaxWidth(),
        )

        // Expandable Content
        if (isExpanded) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()// Constrain the height of the LazyColumn
                    .padding(all = 8.dp)
            ) {
                season.episodes.forEach { episode ->
                    EpisodeRow(episode)
                    Spacer(modifier = Modifier.height(2.dp))
                }
            }
        }
    }
}

@Composable
fun EpisodeRow(episode: Episode) {
    Row(
        modifier = Modifier.fillMaxWidth()
            // .padding(bottom = 2.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(MaterialTheme.colorScheme.surface),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Image(
            painter = rememberAsyncImagePainter(
                "https://image.tmdb.org/t/p/w500/${episode.still_path}"
            ),
            contentDescription = "${episode.name}'s picture",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .width(120.dp)
                .aspectRatio(4/3f)
                .background(MaterialTheme.colorScheme.onPrimary)
        )

        Column(
            modifier = Modifier
                .height(90.dp)
                .weight(1f)
                .padding(8.dp)
        ) {
            Text(
                text = "S${episode.season_number} | E${episode.episode_number}",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = episode.name,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Aired on ${episode.air_date}",
                style = MaterialTheme.typography.bodySmall
            )
        }
        IconButton(onClick = {

        }) {
            Icon(imageVector = Lucide.CircleCheck, contentDescription = "Watched")
        }
    }
}