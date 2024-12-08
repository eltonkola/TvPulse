package org.eltonkola.tvpulse.ui.main.explore

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import org.eltonkola.tvpulse.ui.components.TabScreen

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
