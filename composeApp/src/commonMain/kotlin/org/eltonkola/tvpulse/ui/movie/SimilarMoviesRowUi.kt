package org.eltonkola.tvpulse.ui.movie

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.composables.icons.lucide.*
import org.eltonkola.tvpulse.data.remote.model.TrendingMovieDetails

@Composable
fun SimilarMoviesRowUi(movies: List<TrendingMovieDetails>,
                       addRemoveMovieToWatchlist:(TrendingMovieDetails) -> Unit,
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
                    modifier = Modifier.width(140.dp).aspectRatio(2/3f)
                        .clip(RoundedCornerShape(4.dp))
                        .clickable { openMovie(movie) },
                    contentAlignment = Alignment.TopEnd
                ) {
                    AsyncImage(
                        model = "https://image.tmdb.org/t/p/w500/${movie.poster_path}",
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize()
                            .background(MaterialTheme.colorScheme.surface),
                        contentScale = ContentScale.Crop
                    )

                    Column(
                        modifier = Modifier.fillMaxWidth().padding(4.dp),
                        horizontalAlignment = Alignment.End
                    ){

                            Box(
                                modifier = Modifier
                                    .border(
                                        width = 1.dp,
                                        color = MaterialTheme.colorScheme.primary,
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(
                                        MaterialTheme.colorScheme.surface.copy(alpha = 0.8f),
                                    ).clickable(enabled = !movie.saved, onClick = { addRemoveMovieToWatchlist(movie) }).padding(6.dp)

                            ) {
                                Icon(
                                    imageVector = if(movie.saved) Lucide.Check else Lucide.Plus,
                                    contentDescription = "add",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        }


                    }

            }

        }

    }
    Spacer(modifier = Modifier.size(4.dp))

}
