package org.eltonkola.tvpulse.ui.main.movies

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import org.eltonkola.tvpulse.data.Consts
import org.eltonkola.tvpulse.data.db.model.MediaEntity
import org.eltonkola.tvpulse.ui.components.MediaCard
import org.eltonkola.tvpulse.ui.components.TabScreen

@Composable
fun MoviesTab(
    navController: NavController,
    viewModel: MoviesViewModel = viewModel { MoviesViewModel() }
) {

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        val movies by viewModel.movies.collectAsState(emptyList())

        TabScreen(
            tabs = listOf(
                Pair("WATCHLIST") {

                        if (movies.isEmpty()) {
                            Text("No movies in watchlist")
                        } else {
                            MoviePosterGrid(movies) {
                                navController.navigate("movie/${it.id}")
                            }
                        }

                },
                Pair("UPCOMING") {
                    if (movies.isEmpty()) {
                        Text("No movies in upcoming")
                    } else {
                        MoviePosterGrid(movies) {
                            navController.navigate("movie/${it.id}")
                        }
                    }
                }
            )
        )
    }

}

@Composable
fun MoviePosterGrid(
    movies: List<MediaEntity>,
    onPosterClick: (MediaEntity) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(movies) { movie ->
            MediaCard(
                posterUrl = movie.posterPath ?: Consts.DEFAULT_THUMB_URL,
                onClick = { onPosterClick(movie) }
            )
        }
    }
}


