package org.eltonkola.tvpulse.ui.movie


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
import org.eltonkola.tvpulse.data.db.model.MediaEntity
import org.eltonkola.tvpulse.data.remote.model.MovieDetails

data class HeaderData(
    val posterPath: String,
    val title: String,
    val runtime: Int,
    val genres: String,
)

fun gethederDataForMovie(movie: MediaEntity?, fullMovie: MovieDetails?): HeaderData {
    return if(movie !=null){
        HeaderData(
            posterPath = movie.posterPath ?: "",
            title = movie.title,
            runtime = movie.runtime ?: 0,
            genres = movie.genres.joinToString { it.title }
        )
    } else if(fullMovie !=null){
        HeaderData(
            posterPath = fullMovie.poster_path ?: "",
            title = fullMovie.title,
            runtime = fullMovie.runtime,
            genres = fullMovie.genres.joinToString { it.name }
        )
    } else{
        HeaderData(
            posterPath = "",
            title = "",
            runtime = 0,
            genres = ""
        )
    }
}

@Composable
fun MovieHeader(
    movie : HeaderData,
    navController: NavController,
    onMenuClick: () -> Unit
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
                    contentDescription = "Back"
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = onMenuClick) {
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
                  Text(
                    text = "${movie.runtime?.formatRuntime() ?: "Unknown duration"} · " +
                            movie.genres,
                    modifier = Modifier.fillMaxWidth(),
                    style = MaterialTheme.typography.titleMedium
                )
            }

        }

    }
}

fun Int.formatRuntime(): String {
    if (this <= 0) return "0 minutes"

    val hours = this / 60
    val remainingMinutes = this % 60

    return buildString {
        if (hours > 0) {
            append("$hours hour")
            if (hours > 1) append("s") // Make "hour" plural if needed
            if (remainingMinutes > 0) append(" ")
        }
        if (remainingMinutes > 0) {
            append("$remainingMinutes minute")
            if (remainingMinutes > 1) append("s") // Make "minute" plural if needed
        }
    }
}