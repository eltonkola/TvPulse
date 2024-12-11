package org.eltonkola.tvpulse.ui.movie

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.composables.icons.lucide.Film
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Plus
import org.eltonkola.tvpulse.data.db.model.WatchStatus
import org.eltonkola.tvpulse.data.local.model.AppsScreen
import org.eltonkola.tvpulse.expect.openLink
import org.eltonkola.tvpulse.expect.shareLink
import org.eltonkola.tvpulse.ui.components.DividerLine
import org.eltonkola.tvpulse.ui.components.ErrorUi
import org.eltonkola.tvpulse.ui.components.LazyTabedHeadedList
import org.eltonkola.tvpulse.ui.components.LoadingUi
import org.eltonkola.tvpulse.ui.main.explore.tvshows.formatDateToHumanReadable

@Composable
fun MovieScreen(
    id: Int,
    navController: NavHostController,
    viewModel: MovieViewModel = viewModel { MovieViewModel(id) },
) {

    val uiState by viewModel.uiState.collectAsState()
    var showMenu by remember { mutableStateOf(false) }

    when (uiState) {
        is MovieUiState.Loading ->{
            LoadingUi()
        }
        is MovieUiState.Error ->{
            ErrorUi(
                message = "Error loading movie!",
                retry = true,
                retryLabel = "Retry",
                onRetry = { viewModel.loadTrendingMovies() },
                icon = Lucide.Film,
                iconColor = MaterialTheme.colorScheme.error,
            )
        }
        is MovieUiState.Ready ->{
            val readyState = uiState as MovieUiState.Ready

            Column(modifier = Modifier.fillMaxSize()) {
                LazyTabedHeadedList(
                    modifier = Modifier.fillMaxWidth().weight(1f),
                    tabs = listOf(
                        Pair("ABOUT") {
                            movieAboutTab(readyState, viewModel, navController)
                        },
                        Pair("MORE") {
                            movieMoreTab(readyState, viewModel, navController)
                        }
                    ),
                    minHeaderHeight = 120.dp,
                    header = {
                        Column(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            MovieHeader(gethederDataForMovie(readyState.savedMovie, readyState.fullDetails), navController){
                                showMenu = true
                            }
                            StatusRowUi(
                                getStatusDataForMovie(readyState.savedMovie, readyState.fullDetails),
                                onClick = { viewModel.onMovieWatchedClick() }
                            )
                            DividerLine()

                        }

                    }
                )
                if((uiState as MovieUiState.Ready).savedMovie == null){
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(8.dp),
                    ) {
                        Button(
                            onClick = {
                                viewModel.addRemoveMovieToWatchlist()
                            },
                            shape = RoundedCornerShape(8.dp),
                        ){
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(imageVector = Lucide.Plus, contentDescription = null)
                                Spacer(modifier = Modifier.size(8.dp))
                                Text("Add Movie")
                            }
                        }

                    }


                }
            }

            MovieBottomSheetMenu(
                isVisible = showMenu,
                onDismiss = { showMenu = false },
                onShareClick = {
                    shareLink("https://play.google.com/store/apps/details?id=org.eltonkola.tvpulse")
                },
                onRemoveMovieClick = {
                    viewModel.addRemoveMovieToWatchlist()
                },
                onAddToFavoritesClick = {
                    viewModel.addToFavorites()
                },
                onRemoveFromFavoritesClick = {
                    viewModel.removeFromFavorites()
                },
                isFavorite = readyState.savedMovie?.isFavorite == true,
                isSaved = readyState.savedMovie !=null
            )


        }

    }

}





private fun LazyListScope.movieAboutTab(
    readyState: MovieUiState.Ready,
    viewModel: MovieViewModel,
    navController: NavHostController
) {
    if (readyState.loading) {
        item {
            LoadingUi(
                modifier = Modifier.fillMaxWidth().height(320.dp)
            )
        }
    } else if (readyState.fullDetails != null) {

        readyState.fullDetails.homepage?.let {
            item {
                WhereToWatchRowUi {
                    openLink(it)
                }
                DividerLine()
            }
        }
        item {
            MovieInfoRowUi(readyState.fullDetails)
            DividerLine()
        }
        readyState.trailer?.let { trailer ->
            item {
                TrailerRowUi(trailer) {
                    openLink(it)
                }
                DividerLine()
            }
        }

        if (readyState.cast?.isNotEmpty() == true) {
            item {
                CastRowUi(readyState.cast) {
                    //TODO - open actor details screen
                }
                DividerLine()
            }
        }

        if (readyState.similar?.isNotEmpty() == true) {
            item {
                SimilarMoviesRowUi(readyState.similar, {
                    viewModel.addRemoveMovieToWatchlist()
                }) {
                    navController.navigate("${AppsScreen.Movie.name}/${it.id}")
                }
                DividerLine()
            }
        }

        item {
            Spacer(modifier = Modifier.size(16.dp))
        }

    }
}


private fun LazyListScope.movieMoreTab(
    readyState: MovieUiState.Ready,
    viewModel: MovieViewModel,
    navController: NavHostController
) {
    if (readyState.loading) {
        item {
            LoadingUi(
                modifier = Modifier.fillMaxWidth().height(320.dp)
            )
        }
    } else {

        item{
            UserCommentRowUi(
                userComment =  readyState.savedMovie?.userComment ?: "",
                updateComment = {
                    viewModel.updateComment(it)
                }

            )
            DividerLine()
        }


        item{
            RateMovieRowUi(
                rate = readyState.savedMovie?.userRating ?: 0,
                onRate = {
                    viewModel.rateMovie(it)
                }
            )
            DividerLine()
        }

        item{
            EmotionMovieRowUi(
                selectedEmotion = readyState.savedMovie?.userEmotion ?: -1,
                onRate = {
                    viewModel.emotionMovie(it)
                }
            )
            DividerLine()
        }



        item{
            FavoriteRowUi(
                isFavorite = readyState.savedMovie?.isFavorite == true,
                onClick = {
                    if(readyState.savedMovie?.isFavorite == true){
                        viewModel.removeFromFavorites()
                    }else{
                        viewModel.addToFavorites()
                    }
                }
            )

        }

        item {
            Spacer(modifier = Modifier.size(16.dp))
        }

    }
}
