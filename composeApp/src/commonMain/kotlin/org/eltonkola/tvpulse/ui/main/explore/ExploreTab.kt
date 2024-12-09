package org.eltonkola.tvpulse.ui.main.explore

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import org.eltonkola.tvpulse.ui.components.TabScreen
import org.eltonkola.tvpulse.ui.main.explore.movies.TrendingMoviesScreen
import org.eltonkola.tvpulse.ui.main.explore.tvshows.TrendingShowsScreen

@Composable
fun ExploreTab(
    navController: NavController,
) {
    TabScreen(
        tabs = listOf(
            Pair("Shows") { TrendingShowsScreen(navController) },
            Pair("Movies") { TrendingMoviesScreen(navController) },
        )
    )
}
