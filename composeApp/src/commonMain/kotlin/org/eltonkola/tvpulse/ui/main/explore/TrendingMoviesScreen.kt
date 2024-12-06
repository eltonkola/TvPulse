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
import com.composables.icons.lucide.Tv
import kotlinx.datetime.LocalDate
import kotlinx.datetime.toLocalDate
import org.eltonkola.tvpulse.data.model.TvShowResponse
import org.eltonkola.tvpulse.data.service.TmdbApiClient
import org.eltonkola.tvpulse.ui.components.ExploreCard
import org.eltonkola.tvpulse.ui.components.LoadingUi


@Composable
fun TrendingMoviesScreen(apiClient: TmdbApiClient = TmdbApiClient()) {
    var tvShow by remember { mutableStateOf<TvShowResponse?>(null) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        try {
            tvShow = apiClient.getTrendingMovies()
        } catch (e: Exception) {
            errorMessage = e.message
        }
    }

    if (errorMessage != null) {
        Text("Error: $errorMessage")
    } else if (tvShow != null) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            items(tvShow?.results!!){ tvShow ->
                ExploreCard(
                    modifier = Modifier.fillMaxWidth(),
                    icon = Lucide.Film,
                    title = tvShow.name,
                    subtitle = "${tvShow.origin_country.first()} - ${formatDateToHumanReadable(tvShow.first_air_date?: "")} - ${tvShow.vote_average}",
                    added = false,
                    backgroundUrl = "https://image.tmdb.org/t/p/w500${tvShow.backdrop_path}"
                )
            }

        }
    } else {
        LoadingUi()
    }
}
