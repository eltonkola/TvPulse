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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import com.composables.icons.lucide.ChevronDown
import com.composables.icons.lucide.ChevronUp
import com.composables.icons.lucide.Lucide
import org.eltonkola.tvpulse.data.remote.model.Episode
import org.eltonkola.tvpulse.data.remote.model.Season
import org.eltonkola.tvpulse.ui.components.ProgressUi
import org.eltonkola.tvpulse.ui.components.UiCheck

@Composable
fun SeasonRow(
    season: Season,
    watchEpisodes:(List<Int>) -> Unit,
    unWatchEpisodes:(List<Int>) -> Unit,
    isExpanded: Boolean,
    onExpand:() -> Unit
) {

    val progress = season.episodes.filter { it.isWatched }.size.toFloat() / season.episodes.size
    Column(
        modifier = Modifier.fillMaxWidth().padding(start = 8.dp, end = 8.dp, top = 8.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Title Row
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
                .clickable { onExpand() }
                .padding(8.dp)
        ) {
            Text(
                text = "Season ${season.episodes.first().season_number}",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
            )

            IconButton(onClick = onExpand) {
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

            UiCheck(
                onCheck = {
                    if(progress == 1f) {
                        unWatchEpisodes(season.episodes.map { it.id })
                    }else{
                        watchEpisodes(season.episodes.map { it.id })
                    }
                },
                checked = season.episodes.map { it.isWatched }.all { it }
            )

        }
        ProgressUi(
            progress = progress,
            modifier = Modifier.fillMaxWidth(),
        )

        // Expandable Content
        if (isExpanded) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 8.dp)
            ) {
                season.episodes.forEach { episode ->
                    EpisodeRow(episode){
                        if(episode.isWatched) {
                            unWatchEpisodes(listOf(episode.id))
                        }else{
                            watchEpisodes(listOf(episode.id))
                        }
                    }
                    Spacer(modifier = Modifier.height(2.dp))
                }
            }
        }
    }
}

@Composable
fun EpisodeRow(episode: Episode, watchUnwatch:() -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth()
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

        UiCheck(
            onCheck = watchUnwatch,
            checked = episode.isWatched
        )

    }
}