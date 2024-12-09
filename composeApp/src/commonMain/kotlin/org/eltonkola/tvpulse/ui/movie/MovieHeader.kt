package org.eltonkola.tvpulse.ui.movie


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.composables.icons.lucide.*
import dev.carlsen.flagkit.FlagKit
import org.eltonkola.tvpulse.data.db.model.MediaEntity
import org.eltonkola.tvpulse.ui.main.explore.tvshows.formatDateToHumanReadable

@Composable
fun MovieHeader(
    movie : MediaEntity,
    navController: NavController
) {
    Box(
        modifier = Modifier.fillMaxWidth().aspectRatio(1.8f),
        contentAlignment = Alignment.BottomStart
    ) {

        AsyncImage(
            model = "https://image.tmdb.org/t/p/w500/${movie.posterPath}",
            contentDescription = null,
            modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.surface),
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Black.copy(alpha = 0.6f),
                            Color.Black.copy(alpha = 0.4f),
                            Color.Black.copy(alpha = 0.6f),
                            Color.Black.copy(alpha = 1f)
                        )
                    )
                )
        )

        Row(
            modifier = Modifier.fillMaxWidth().align(Alignment.TopStart).padding(8.dp),
        ) {
            IconButton(onClick = {
                navController.popBackStack()
            }) {
                Icon(
                    imageVector = Lucide.ChevronDown,
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = {
                //TODO - show menu with actions
            }) {
                Icon(imageVector = Lucide.Ellipsis, contentDescription = "Menu")
            }
        }


        Column {

            Row(
                modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp, bottom = 2.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = movie.title,
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.titleLarge
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp, bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Image(
                    imageVector  = FlagKit.getFlag(movie.originalLanguage?: "us") ?: FlagKit.getFlag("us")!!,
                    contentDescription = movie.originalLanguage?: "us",
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.size(4.dp))

                Text(
                    text = "${formatDateToHumanReadable(movie.releaseDate ?: "")} · " +
                            "⭐ ${movie.voteAverage}/10 (${movie.voteCount} votes)",
                    modifier = Modifier.fillMaxWidth(),
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Spacer(
                modifier = Modifier.fillMaxWidth().height(1.dp).background(MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f))
            )
        }


    }
}

