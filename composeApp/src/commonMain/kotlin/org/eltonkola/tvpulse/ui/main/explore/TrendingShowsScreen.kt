package org.eltonkola.tvpulse.ui.main.explore


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import app.cash.paging.compose.collectAsLazyPagingItems
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Tv
import kotlinx.datetime.LocalDate
import kotlinx.datetime.toLocalDate
import org.eltonkola.tvpulse.DiGraph
import org.eltonkola.tvpulse.data.Consts
import org.eltonkola.tvpulse.data.remote.model.TmdbListResponse
import org.eltonkola.tvpulse.data.remote.model.TrendingTvShowDetails
import org.eltonkola.tvpulse.data.remote.model.TvShowDetails
import org.eltonkola.tvpulse.data.remote.service.TmdbApiClient
import org.eltonkola.tvpulse.ui.components.ExploreCard
import org.eltonkola.tvpulse.ui.components.LoadingUi


@Composable
fun TrendingShowsScreen(
    navController: NavController,
    viewModel: TrendingTvShowsViewModel = viewModel { TrendingTvShowsViewModel() },
) {

    val tvShows = viewModel.uiState.collectAsLazyPagingItems()

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            items(tvShows.itemCount){
               if(tvShows[it] != null) {
                   ExploreCard(
                       modifier = Modifier.fillMaxWidth(),
                       icon = Lucide.Tv,
                       title = tvShows[it]!!.name,
                       country = tvShows[it]!!.origin_country.first(),
                       subtitle = "${formatDateToHumanReadable(tvShows[it]!!.first_air_date)} · " +
                               "⭐ ${tvShows[it]!!.vote_average}/10 (${tvShows[it]!!.vote_count} votes)",
                       added = false,
                       backgroundUrl = tvShows[it]!!.backdrop_path ?: Consts.DEFAULT_THUMB_URL
                   )
               }
            }

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

