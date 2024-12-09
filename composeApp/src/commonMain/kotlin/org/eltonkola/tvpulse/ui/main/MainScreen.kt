package org.eltonkola.tvpulse.ui.main


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.composables.icons.lucide.*
import org.eltonkola.tvpulse.ui.main.explore.ExploreTab
import org.eltonkola.tvpulse.ui.main.movies.MoviesTab
import org.eltonkola.tvpulse.ui.main.profile.ProfileTab
import org.eltonkola.tvpulse.ui.main.tvShows.TvShowsTab
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import tvpulse.composeapp.generated.resources.Res
import tvpulse.composeapp.generated.resources.level_tab_1
import tvpulse.composeapp.generated.resources.level_tab_2
import tvpulse.composeapp.generated.resources.level_tab_3
import tvpulse.composeapp.generated.resources.level_tab_4

data class AppTab(
    val icon: ImageVector,
    val name: StringResource
)

@Composable
fun MainScreen(navController: NavController,
    viewModel: MainScreenViewModel = viewModel { MainScreenViewModel() },
) {

//    val uiState by viewModel.uiState.collectAsState()

    var selectedItem by rememberSaveable { mutableIntStateOf(0) }

    val items = listOf(
        AppTab(Lucide.Tv, Res.string.level_tab_1),
        AppTab(Lucide.Film, Res.string.level_tab_2),
        AppTab(Lucide.SquareLibrary, Res.string.level_tab_3),
        AppTab(Lucide.SquareUserRound, Res.string.level_tab_4)
    )

    val borderColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.2f)
    Scaffold (
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface,
                modifier = Modifier.drawBehind {
                    drawLine(
                        color =borderColor,
                        start = androidx.compose.ui.geometry.Offset(0f, 0f),
                        end = androidx.compose.ui.geometry.Offset(size.width, 0f),
                        strokeWidth = 1.dp.toPx()
                    )
                }
            ) {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        icon = {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = stringResource(item.name) ,
                                modifier = Modifier.size(24.dp),
                            )
                        },
                        label = { Text(stringResource(item.name)) },
                        selected = selectedItem == index,
                        onClick = { selectedItem = index },
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = Color.Transparent,
                            unselectedIconColor =  MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                        )
                    )
                }
            }

        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            when (selectedItem) {
                0 -> TvShowsTab (navController)
                1 -> MoviesTab(navController)
                2 -> ExploreTab(navController)
                3 -> ProfileTab(navController)
            }
        }
    }

}
