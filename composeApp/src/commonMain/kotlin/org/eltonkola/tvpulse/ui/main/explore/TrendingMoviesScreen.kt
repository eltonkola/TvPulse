package org.eltonkola.tvpulse.ui.main.explore


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.composables.icons.lucide.Film
import com.composables.icons.lucide.Lucide
import com.dokar.sonner.Toaster
import com.dokar.sonner.rememberToasterState
import org.eltonkola.tvpulse.data.Consts
import org.eltonkola.tvpulse.ui.components.ErrorUi
import org.eltonkola.tvpulse.ui.components.ExploreCard
import org.eltonkola.tvpulse.ui.components.LoadingUi


@Composable
fun TrendingMoviesScreen(
    navController: NavController,
    viewModel: TrendingMoviesViewModel = viewModel { TrendingMoviesViewModel() }
) {

    val uiState by viewModel.uiState.collectAsState()
    val toaster = rememberToasterState()

    when(uiState.dataState){
        is TrendingMoviesSate.Error -> {
            ErrorUi(
                message = "Error loading trending movies.",
                retry = true,
                retryLabel = "Retry",
                onRetry = {
                    viewModel.loadTrendingMovies()
                }
            )
        }
        TrendingMoviesSate.Loading -> {
            LoadingUi()
        }
        is TrendingMoviesSate.Ready -> {
            val movies = (uiState.dataState as TrendingMoviesSate.Ready).movies
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(movies) { movie ->
                        ExploreCard(
                            modifier = Modifier.fillMaxWidth(),
                            icon = Lucide.Film,
                            title = movie.title,
                            country = movie.original_language,
                            subtitle = "${formatDateToHumanReadable(movie.release_date)} · " +
                                    "⭐ ${movie.vote_average}/10 (${movie.vote_count} votes)",
                            added = movie.saved,
                            backgroundUrl = movie.backdrop_path ?: Consts.DEFAULT_THUMB_URL,
                            onAdd = {
                                if (uiState.addMovieState is AddMovieState.Idle) {
                                    viewModel.addMovieToWatchlist(movie.id)
                                } else {
                                    toaster.show("Already adding a movie..")
                                }
                            }
                        )
                    }
                }

                when(uiState.addMovieState){
                    AddMovieState.Adding -> {
                        CircularProgressIndicator()
                    }
                    AddMovieState.Done -> {
                        toaster.show("Movie added!..")
                    }
                    is AddMovieState.Error -> {
                        toaster.show("Error adding movie..")
                    }
                    AddMovieState.Idle -> {

                    }
                }

                LaunchedEffect(uiState.addMovieState){
                    when(uiState.addMovieState){
                        AddMovieState.Done -> {
                            viewModel.movieAddedShown()
                        }
                        is AddMovieState.Error -> {
                            viewModel.movieAddedShown()
                        }
                        else -> {

                        }
                    }
                }


            }

        }
    }

    Toaster(state = toaster)

}
