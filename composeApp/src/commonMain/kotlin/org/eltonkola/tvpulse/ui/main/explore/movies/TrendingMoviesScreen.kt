package org.eltonkola.tvpulse.ui.main.explore.movies


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import app.cash.paging.compose.collectAsLazyPagingItems
import com.composables.icons.lucide.Film
import com.composables.icons.lucide.Lucide
import com.dokar.sonner.Toaster
import com.dokar.sonner.rememberToasterState
import org.eltonkola.tvpulse.data.Consts
import org.eltonkola.tvpulse.data.local.model.AppsScreen
import org.eltonkola.tvpulse.ui.components.ErrorUi
import org.eltonkola.tvpulse.ui.components.ExploreCard
import org.eltonkola.tvpulse.ui.components.LoadingUi
import org.eltonkola.tvpulse.ui.main.explore.tvshows.formatDateToHumanReadable


@Composable
fun TrendingMoviesScreen(
    navController: NavController,
    viewModel: TrendingMoviesViewModel = viewModel { TrendingMoviesViewModel() }
) {

    val movies = viewModel.uiState.collectAsLazyPagingItems()
    val addState = viewModel.addState.collectAsState()
    val toaster = rememberToasterState()


    when(movies.loadState.append){
        LoadState.Loading -> {
            LoadingUi()
        }
        is LoadState.Error -> {
            ErrorUi(
                message = "Error loading trending Movies!",
                retry = true,
                onRetry = { movies.retry() }
            )
        }
        else -> {}
    }
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(movies.itemCount) {
                        if(movies[it] != null) {
                            ExploreCard(
                                modifier = Modifier.fillMaxWidth().clickable { navController.navigate("${AppsScreen.Movie.name}/${movies[it]!!.id}") },
                                icon = Lucide.Film,
                                title = movies[it]!!.title,
                                country = movies[it]!!.original_language,
                                subtitle = "${movies[it]!!.release_date.formatDateToHumanReadable()} · " +
                                        "⭐ ${movies[it]!!.vote_average}/10 (${movies[it]!!.vote_count} votes)",
                                added = movies[it]!!.saved,
                                backgroundUrl = movies[it]!!.backdrop_path ?: Consts.DEFAULT_THUMB_URL,
                                onAdd = {
                                    if (addState.value is AddMovieState.Idle) {
                                        viewModel.addMovieToWatchlist(movies[it]!!.id)
                                    } else {
                                        toaster.show("Already adding a movie..")
                                    }
                                }
                            )
                        }
                    }
                }


                when(addState.value){
                    AddMovieState.Adding -> {
                        CircularProgressIndicator()
                    }
                    AddMovieState.Done -> {
                        toaster.show("Movie added!..")
                    }
                    is AddMovieState.Error -> {
                        toaster.show("Error adding Movie..")
                    }
                    AddMovieState.Idle -> {

                    }
                }

                LaunchedEffect(addState.value){
                    when(addState.value){
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

    Toaster(state = toaster, modifier = Modifier.padding(bottom = 60.dp))

}
