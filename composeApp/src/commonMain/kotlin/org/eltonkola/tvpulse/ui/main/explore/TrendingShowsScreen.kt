package org.eltonkola.tvpulse.ui.main.explore


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import app.cash.paging.compose.collectAsLazyPagingItems
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Tv
import com.dokar.sonner.Toaster
import com.dokar.sonner.rememberToasterState
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
    val addState = viewModel.addState.collectAsState()
    val toaster = rememberToasterState()

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
                       added = tvShows[it]!!.saved,
                       backgroundUrl = tvShows[it]!!.backdrop_path ?: Consts.DEFAULT_THUMB_URL,
                       onAdd = {
                           if (addState.value is AddTvShowState.Idle) {
                               viewModel.addTvShowToWatchlist(tvShows[it]!!.id)
                           } else {
                               toaster.show("Already adding a show..")
                           }
                       }
                   )
               }
            }

        }



    when(addState.value){
        AddTvShowState.Adding -> {
            CircularProgressIndicator()
        }
        AddTvShowState.Done -> {
            toaster.show("TvShow added!..")
        }
        is AddTvShowState.Error -> {
            toaster.show("Error adding TvShow..")
        }
        AddTvShowState.Idle -> {

        }
    }

    LaunchedEffect(addState.value){
        when(addState.value){
            AddTvShowState.Done -> {
                viewModel.tvShowAddedShown()
            }
            is AddTvShowState.Error -> {
                viewModel.tvShowAddedShown()
            }
            else -> {

            }
        }
    }

    Toaster(state = toaster, modifier = Modifier.padding(bottom = 60.dp))
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

