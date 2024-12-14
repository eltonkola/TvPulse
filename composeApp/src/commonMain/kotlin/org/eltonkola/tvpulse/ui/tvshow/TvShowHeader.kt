package org.eltonkola.tvpulse.ui.tvshow


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
import org.eltonkola.tvpulse.data.remote.model.TvShowDetails

data class HeaderData(
    val posterPath: String,
    val title: String,
    val seasons: String,
    val genres: String,
)

fun gethederDataForTvShow(tvShow: MediaEntity?, fullTvShow: TvShowDetails?): HeaderData {
    return if(tvShow !=null){
        HeaderData(
            posterPath = tvShow.posterPath ?: "",
            title = tvShow.title,
            seasons = "${tvShow.numberOfSeasons} seasons",
            genres = tvShow.genres.joinToString { it.title }
        )
    } else if(fullTvShow !=null){
        HeaderData(
            posterPath = fullTvShow.poster_path ?: "",
            title = fullTvShow.name,
            seasons = "${fullTvShow.number_of_seasons} seasons",
            genres = fullTvShow.genres.joinToString { it.name }
        )
    } else{
        HeaderData(
            posterPath = "",
            title = "",
            seasons = "",
            genres = ""
        )
    }
}

@Composable
fun TvShowHeader(
    tvShow : HeaderData,
    navController: NavController,
    onMenuClick: () -> Unit
) {

    Box(
        modifier = Modifier.fillMaxWidth().aspectRatio(1.8f),
        contentAlignment = Alignment.BottomStart
    ) {

        AsyncImage(
            model = "https://image.tmdb.org/t/p/w500/${tvShow.posterPath}",
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
                    text = tvShow.title,
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.titleLarge
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp, bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                  Text(
                    text = "${tvShow.seasons} · ${tvShow.genres}",
                    modifier = Modifier.fillMaxWidth(),
                    style = MaterialTheme.typography.titleMedium
                )
            }

        }

    }
}
