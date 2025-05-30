package org.eltonkola.tvpulse.ui.main.tvShows

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.composables.icons.lucide.*
import org.eltonkola.tvpulse.data.Consts
import org.eltonkola.tvpulse.data.db.model.MediaEntity
import org.eltonkola.tvpulse.data.local.model.AppsScreen
import org.eltonkola.tvpulse.ui.components.ErrorUi
import org.eltonkola.tvpulse.ui.components.LoadingUi
import org.eltonkola.tvpulse.ui.components.MediaCard
import org.eltonkola.tvpulse.ui.components.TabScreen


@Composable
fun TvShowsTab(
    navController: NavController,
    openTab:(Int) -> Unit,
    viewModel: TvShowsViewModel = viewModel { TvShowsViewModel() },
) {

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        val uiState by viewModel.uiState.collectAsState()

        when (uiState) {
            is TvShowsUiState.Loading -> LoadingUi()
            is TvShowsUiState.Ready -> {
                val tvShows = (uiState as TvShowsUiState.Ready).tvShows

                TabScreen(
                    tabs = listOf(
                        Pair("WATCHLIST") {

                            if (tvShows.isEmpty()) {
                                ErrorUi(
                                    message = "No shows in watchlist",
                                    retry = true,
                                    retryLabel = "Explore tv shows",
                                    onRetry = { openTab(2) },
                                    icon = Lucide.Tv,
                                    iconColor = MaterialTheme.colorScheme.primary,
                                )
                            } else {
                                TvShowsPosterGrid(tvShows) {
                                    navController.navigate("${AppsScreen.TvShow.name}/${it.id}")
                                }
                            }

                        },
                        Pair("UPCOMING") {
                            if (tvShows.isEmpty()) {
                                ErrorUi(
                                    message = "No upcoming shows",
                                    retry = true,
                                    retryLabel = "Explore tv shows",
                                    onRetry = { openTab(2) },
                                    icon = Lucide.Calendar,
                                    iconColor = MaterialTheme.colorScheme.primary,
                                )
                            } else {
                                TvShowsPosterGrid(tvShows) {
                                    navController.navigate("${AppsScreen.TvShow.name}/${it.id}")
                                }
                            }
                        }
                    )
                )

            }
            is TvShowsUiState.Error ->
                ErrorUi(
                    message = "Error loading tv shows",
                    retry = true,
                    onRetry = {
                        viewModel.loadTvShows()
                    },
                    icon = Lucide.Tv
                )
        }

    }


}

@Composable
fun TvShowsPosterGrid(
    tvShows: List<MediaEntity>,
    onPosterClick: (MediaEntity) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(tvShows) { tvShow ->
            MediaCard(
                posterUrl = tvShow.posterPath ?: Consts.DEFAULT_THUMB_URL,
                onClick = { onPosterClick(tvShow) },
                isTvShow = true,
                progress = tvShow.progress()
            )
        }
    }
}

