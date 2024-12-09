package org.eltonkola.tvpulse.ui.main.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.composables.icons.lucide.Heart
import com.composables.icons.lucide.Lucide
import com.dokar.sonner.Toaster
import com.dokar.sonner.rememberToasterState
import org.eltonkola.tvpulse.data.Consts
import org.eltonkola.tvpulse.data.db.model.MediaEntity
import org.eltonkola.tvpulse.data.local.model.AppsScreen
import org.eltonkola.tvpulse.data.local.model.WatchingStats
import org.eltonkola.tvpulse.ui.components.MediaCard
import org.eltonkola.tvpulse.ui.components.SectionHeader


@Composable
fun ProfileTab(
    navController: NavController,
    viewModel: ProfileViewModel = viewModel { ProfileViewModel() }
) {


    val tvShows = viewModel.tvShows.collectAsState(emptyList())
    val movies = viewModel.movies.collectAsState(emptyList())
    val favoriteTvShows = viewModel.favoriteTvShows.collectAsState(emptyList())
    val favoriteMovies = viewModel.favoriteMovies.collectAsState(emptyList())

    val listState = rememberSaveable(saver = LazyListState.Saver) {
        LazyListState()
    }

    val toaster = rememberToasterState()

    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        state = listState
    ) {
        item {
            ProfileHeader(navController = navController)
        }
        item {
            Spacer(modifier = Modifier.size(2.dp))
            ProfileNumbers(nrMovies = 20, nrTvShows = 128, nrNotes = 36)
        }
        item {
            Spacer(modifier = Modifier.size(16.dp))
            SectionHeader(
                title = "Stats",
            ) {
                toaster.show("More stats coming soon!")
            }
            StatsUi(
                stats = WatchingStats(
                    tvMonths = 14,
                    tvDays = 29,
                    tvHours = 21,
                    episodesWatched = 14959,
                    moviesMonths = 0,
                    moviesDays = 25,
                    moviesHours = 14,
                    moviesWatched = 335
                )
            )
        }
        //TODO - implement this feature later on
//            Spacer(modifier = Modifier.size(48.dp))
//            SectionHeader(
//                title = "Lists",
//            ){
//
//            }

        item {
            Spacer(modifier = Modifier.size(48.dp))
            SectionHeader(
                title = "Shows",
            ) {
                toaster.show("All shows coming soon!")
            }

            CardSection(
                items = tvShows.value,
                emptyTitle = "No TvShows",
                onCardClick = { navController.navigate("${AppsScreen.TvShow.name}/$it") }
            )
        }
        item {
            Spacer(modifier = Modifier.size(48.dp))
            SectionHeader(
                title = "Favorite Shows",
                icon = Lucide.Heart
            ) {
                toaster.show("All favorite shows coming soon!")
            }

            CardSection(
                items = favoriteTvShows.value,
                emptyTitle = "No Favorite TvShows",
                onCardClick = { navController.navigate("${AppsScreen.TvShow.name}/$it") }
            )

        }
        item {
            Spacer(modifier = Modifier.size(48.dp))
            SectionHeader(
                title = "Movies",
            ) {
                toaster.show("All movies coming soon!")
            }

            CardSection(
                items = movies.value,
                emptyTitle = "No Movies",
                onCardClick = { navController.navigate("${AppsScreen.Movie.name}/$it") }
            )

        }
        item {
            Spacer(modifier = Modifier.size(48.dp))
            SectionHeader(
                title = "Favorite Movies",
                icon = Lucide.Heart
            ) {
                toaster.show("All favorite movies coming soon!")
            }
            Spacer(modifier = Modifier.size(48.dp))

            CardSection(
                items = favoriteMovies.value,
                emptyTitle = "No Favorite Movies",
                onCardClick = { navController.navigate("${AppsScreen.Movie.name}/$it") }
            )

        }

    }


    Toaster(state = toaster, modifier = Modifier.padding(bottom = 60.dp))
}

@Composable
private fun CardSection(
    items: List<MediaEntity>,
    emptyTitle: String,
    onCardClick: (Int) -> Unit
) {
    if (items.isEmpty()) {
        StatsUi(
            modifier = Modifier.fillMaxWidth().height(80.dp),
            title = emptyTitle
        )
    }

    LazyRow(
        modifier = Modifier.fillMaxWidth().padding(start = 8.dp, end = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        items(items) {
            Box(modifier = Modifier.width(120.dp)) {
                MediaCard(
                    posterUrl = it.posterPath ?: Consts.DEFAULT_THUMB_URL,
                    onClick = { onCardClick(it.id) }
                )
            }
        }
    }
}
