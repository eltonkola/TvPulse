package org.eltonkola.tvpulse.ui.tvshow

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.composables.icons.lucide.Film
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Plus
import org.eltonkola.tvpulse.data.Consts
import org.eltonkola.tvpulse.data.local.model.AppsScreen
import org.eltonkola.tvpulse.data.remote.model.toLocalModel
import org.eltonkola.tvpulse.expect.openLink
import org.eltonkola.tvpulse.expect.shareLink
import org.eltonkola.tvpulse.ui.components.*
import org.eltonkola.tvpulse.ui.movie.*


@Composable
fun TvShowScreen(
    id: Int,
    navController: NavHostController,
    viewModel: TvShowViewModel = viewModel { TvShowViewModel(id) },
) {

    val uiState by viewModel.uiState.collectAsState()
    var showMenu by remember { mutableStateOf(false) }

    when (uiState) {
        is TvShowUiState.Loading -> {
            LoadingUi()
        }

        is TvShowUiState.Error -> {
            ErrorUi(
                message = "Error loading movie!",
                retry = true,
                retryLabel = "Retry",
                onRetry = { viewModel.loadTvShow() },
                icon = Lucide.Film,
                iconColor = MaterialTheme.colorScheme.error,
            )
        }

        is TvShowUiState.Ready -> {
            val readyState = uiState as TvShowUiState.Ready

            Column(modifier = Modifier.fillMaxSize()) {
                LazyTabedHeadedList(
                    modifier = Modifier.fillMaxWidth().weight(1f),
                    tabs = listOf(
                        Pair("ABOUT") {
                            tvShowAboutTab(readyState, navController)
                        },
                        Pair("MORE") {
                            tvShowMoreTab(readyState, viewModel)
                        },
                        Pair("EPISODES") {
                            episodesTab(readyState, viewModel)
                        },
                    ),
                    minHeaderHeight = 120.dp,
                    header = {
                        Column(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            MovieHeader(
                                gethederDataForTvShow(readyState.savedTvShow, readyState.fullDetails),
                                navController
                            ) {
                                showMenu = true
                            }
                            StatusRowUi(
                                getStatusDataForTvShow(readyState.savedTvShow, readyState.fullDetails),
                                onClick = { viewModel.onMovieWatchedClick() }
                            )
                            DividerLine()

                        }

                    }
                )
                if ((uiState as TvShowUiState.Ready).savedTvShow == null) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(8.dp),
                    ) {
                        Button(
                            onClick = {
                                viewModel.addRemoveMovieToWatchlist()
                            },
                            shape = RoundedCornerShape(8.dp),
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(imageVector = Lucide.Plus, contentDescription = null)
                                Spacer(modifier = Modifier.size(8.dp))
                                Text("Add Tv Show")
                            }
                        }

                    }

                }
            }

            MovieBottomSheetMenu(
                isVisible = showMenu,
                onDismiss = { showMenu = false },
                onShareClick = {
                    shareLink(Consts.SHARE_URL)
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
                isFavorite = readyState.savedTvShow?.isFavorite == true,
                isSaved = readyState.savedTvShow != null
            )

        }

    }

}

private fun LazyListScope.tvShowAboutTab(
    readyState: TvShowUiState.Ready,
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
            MediaInfoRowUi(readyState.fullDetails.toMediaInfoData())
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
                    navController.navigate("${AppsScreen.Person.name}/${it.id}")
                }
                DividerLine()
            }
        }

        if (readyState.similar?.isNotEmpty() == true) {
            item {
                SimilarMoviesRowUi(readyState.similar.map { it.toLocalModel() }) {
                    navController.navigate("${AppsScreen.Movie.name}/$it")
                }
                DividerLine()
            }
        }

        item {
            Spacer(modifier = Modifier.size(16.dp))
        }

    }
}

private fun LazyListScope.tvShowMoreTab(
    readyState: TvShowUiState.Ready,
    viewModel: TvShowViewModel
) {
    if (readyState.loading) {
        item {
            LoadingUi(
                modifier = Modifier.fillMaxWidth().height(320.dp)
            )
        }
    } else {

        item {
            UserCommentRowUi(
                userComment = readyState.savedTvShow?.userComment ?: "",
                updateComment = {
                    viewModel.updateComment(it)
                }

            )
            DividerLine()
        }

        item {
            RateMovieRowUi(
                rate = readyState.savedTvShow?.userRating ?: 0,
                onRate = {
                    viewModel.rateMovie(it)
                }
            )
            DividerLine()
        }

        item {
            EmotionMovieRowUi(
                selectedEmotion = readyState.savedTvShow?.userEmotion ?: -1,
                onRate = {
                    viewModel.emotionMovie(it)
                }
            )
            DividerLine()
        }

        item {
            FavoriteRowUi(
                isFavorite = readyState.savedTvShow?.isFavorite == true,
                onClick = {
                    if (readyState.savedTvShow?.isFavorite == true) {
                        viewModel.removeFromFavorites()
                    } else {
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


private fun LazyListScope.episodesTab(
    readyState: TvShowUiState.Ready,
    viewModel: TvShowViewModel
) {
    if (readyState.loading) {
        item {
            LoadingUi(
                modifier = Modifier.fillMaxWidth().height(320.dp)
            )
        }
    } else {
        if(readyState.seasons != null){
            items(readyState.seasons) { season ->
                SeasonRow(
                    season = season,
                    watchEpisodes = viewModel::watchEpisodes,
                    unWatchEpisodes = viewModel::unWatchEpisodes,
                    isExpanded = season.isExpanded.value,
                    onExpand = {
                        season.isExpanded.value = !season.isExpanded.value
                    }
                )
                Spacer(modifier = Modifier.size(2.dp))
            }
        }

        item {
            Spacer(modifier = Modifier.size(16.dp))
        }

    }
}

