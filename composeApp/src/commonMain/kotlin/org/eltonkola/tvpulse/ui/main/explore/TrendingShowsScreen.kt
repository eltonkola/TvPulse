package org.eltonkola.tvpulse.ui.main.explore


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Tv
import kotlinx.datetime.LocalDate
import kotlinx.datetime.toLocalDate
import org.eltonkola.tvpulse.DiGraph
import org.eltonkola.tvpulse.data.remote.model.TmdbListResponse
import org.eltonkola.tvpulse.data.remote.model.TvShowDetails
import org.eltonkola.tvpulse.data.remote.service.TmdbApiClient
import org.eltonkola.tvpulse.ui.components.ExploreCard
import org.eltonkola.tvpulse.ui.components.LoadingUi


@Composable
fun TrendingShowsScreen(apiClient: TmdbApiClient = DiGraph.tmdbApiClient) {
    var tvShow by remember { mutableStateOf<TmdbListResponse<TvShowDetails>?>(null) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        try {
            tvShow = apiClient.getTrendingTvShows()
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
                    icon = Lucide.Tv,
                    title = tvShow.name,
                    country = tvShow.origin_country.first(),
                    subtitle = " · " +
                            "\uD83D\uDCC5 ${formatDateToHumanReadable(tvShow.first_air_date)} · " +
                            "⭐ ${tvShow.vote_average}/10 (${tvShow.vote_count} votes)",
                    added = false,
                    backgroundUrl = "https://image.tmdb.org/t/p/w500${tvShow.backdrop_path}"
                )

            }

        }

    } else {
        LoadingUi()
    }
}


fun formatDateToHumanReadable(dateString: String): String {
    return try {
        // Parse the input date string
        val date: LocalDate = dateString.toLocalDate()

        // Format the date into a human-readable string
        val month = date.month.name.lowercase().replaceFirstChar { it.uppercase() }
        val day = date.dayOfMonth
        val year = date.year

        "$month $day $year"
    } catch (e: Exception) {
        dateString // Handle invalid input
    }
}

