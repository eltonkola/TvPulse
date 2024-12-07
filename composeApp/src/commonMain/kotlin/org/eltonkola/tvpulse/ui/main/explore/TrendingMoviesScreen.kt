package org.eltonkola.tvpulse.ui.main.explore


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.Film
import com.composables.icons.lucide.Lucide
import org.eltonkola.tvpulse.DiGraph
import org.eltonkola.tvpulse.data.remote.model.MovieDetails
import org.eltonkola.tvpulse.data.remote.model.TmdbListResponse
import org.eltonkola.tvpulse.data.remote.service.TmdbApiClient
import org.eltonkola.tvpulse.ui.components.ExploreCard
import org.eltonkola.tvpulse.ui.components.LoadingUi


@Composable
fun TrendingMoviesScreen(apiClient: TmdbApiClient = DiGraph.tmdbApiClient) {
    var movies by remember { mutableStateOf<TmdbListResponse<MovieDetails>?>(null) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        try {
            movies = apiClient.getTrendingMovies()
        } catch (e: Exception) {
            errorMessage = e.message
        }
    }

    if (errorMessage != null) {
        Text("Error: $errorMessage")
    } else if (movies != null) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            items(movies?.results!!){ movie ->
                ExploreCard(
                    modifier = Modifier.fillMaxWidth(),
                    icon = Lucide.Film,
                    title = movie.title,
                    country = movie.original_language,
                    subtitle = "${formatDateToHumanReadable(movie.release_date)} · " +
                            "⭐ ${movie.vote_average}/10 (${movie.vote_count} votes)",
                    added = false,
                    backgroundUrl = "https://image.tmdb.org/t/p/w500${movie.backdrop_path}"
                )
            }

        }
    } else {
        LoadingUi()
    }
}
