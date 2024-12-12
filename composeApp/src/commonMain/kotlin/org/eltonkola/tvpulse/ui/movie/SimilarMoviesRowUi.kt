package org.eltonkola.tvpulse.ui.movie

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.eltonkola.tvpulse.data.Consts
import org.eltonkola.tvpulse.data.remote.model.TrendingMovieDetails
import org.eltonkola.tvpulse.ui.components.MediaCard

@Composable
fun SimilarMoviesRowUi(movies: List<TrendingMovieDetails>,
                       openMovie:(TrendingMovieDetails) -> Unit ) {
    Column (
        modifier = Modifier.fillMaxWidth().padding(8.dp)
    ){
        Spacer(modifier = Modifier.size(2.dp))

        Text(
            text = "Similar Movies",
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.size(8.dp))

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ){

            items(movies) { movie ->
                Box(
                    modifier = Modifier.width(120.dp)
                ) {
                    MediaCard(
                        posterUrl = movie.poster_path ?: Consts.DEFAULT_THUMB_URL,
                        onClick = { openMovie(movie)  }
                    )
                }

            }

        }

    }
    Spacer(modifier = Modifier.size(4.dp))

}
