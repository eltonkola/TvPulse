package org.eltonkola.tvpulse.ui.movie

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.composables.icons.lucide.Film
import com.composables.icons.lucide.Lucide
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

            LazyTabedHeadedList(
                tabs = listOf(
                    Pair("ABOUT") {
                          if(readyState.loading){
                              item{
                                  LoadingUi(
                                      modifier = Modifier.fillMaxWidth().height(120.dp)
                                  )
                              }
                          }else if(readyState.fullDetails !=null){

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

                              if(readyState.cast?.isNotEmpty() == true){
                                  item {
                                      CastRowUi(readyState.cast) {
                                          //TODO - open actor details screen
                                      }
                                      DividerLine()
                                  }
                              }

                              if(readyState.similar?.isNotEmpty() == true){
                                  item {
                                      SimilarMoviesRowUi(readyState.similar, {
                                          viewModel.addRemoveMovieToWatchlist(it.id)
                                      }) {
                                          navController.navigate("${AppsScreen.Movie.name}/${it.id}")
                                      }
                                      DividerLine()
                                  }
                              }

                              item{
                                  Spacer(modifier = Modifier.size(16.dp))
                              }

                          }
                    },
                    Pair("MORE") {
                        if(readyState.loading){
                            item{
                                LoadingUi()
                            }
                        }else{
                            item{
                                Text("Movie more")
                            }
                        }
                    }
                ),
                minHeaderHeight = 120.dp,
                header = {
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        MovieHeader(readyState.movie, navController)
                        StatusRowUi(
                            releaseDate = readyState.movie.releaseDate?.formatDateToHumanReadable() ?: "",
                            watch = readyState.movie.mediaStatus == WatchStatus.COMPLETED,
                            onClick = { viewModel.onMovieWatchedClick() }
                        )
                        DividerLine()

                    }

                }
            )

        }

    }


}
