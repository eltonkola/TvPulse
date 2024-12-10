package org.eltonkola.tvpulse.ui.movie

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.composables.icons.lucide.Heart
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Share
import kotlinx.coroutines.launch
import org.eltonkola.tvpulse.ui.components.LazyHeadedList
import org.eltonkola.tvpulse.ui.components.LazyTabedHeadedList
import org.eltonkola.tvpulse.ui.components.LoadingUi
import org.eltonkola.tvpulse.ui.components.TabScreen
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import tvpulse.composeapp.generated.resources.Res
import tvpulse.composeapp.generated.resources.landing_background
import kotlin.math.absoluteValue
import kotlin.math.min


@Composable
fun MovieScreen(
    id: Int,
    navController: NavHostController,
    viewModel: MovieViewModel = viewModel { MovieViewModel(id) },
) {

    val uiState by viewModel.movie.collectAsState(null)

    when (uiState) {
        null -> {
            LoadingUi()
        }

        else -> {


            LazyTabedHeadedList(
                tabs = listOf(
                    Pair("ABOUT") {
                            items(100) { index ->
                                Text(
                                    "Tab About item $index", modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp)
                                )
                            }
                    },
                    Pair("MORE") {
                        items(100) { index ->
                            Text(
                                "Tab More $index", modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            )
                        }
                    }
                ),
                minHeaderHeight = 120.dp,
                header = {
                    MovieHeader(uiState!!, navController)
                }
            )


        }
    }


}
