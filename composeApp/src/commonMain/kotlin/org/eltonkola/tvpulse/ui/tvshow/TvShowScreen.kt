package org.eltonkola.tvpulse.ui.tvshow

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import org.eltonkola.tvpulse.ui.components.LazyHeadedList
import org.jetbrains.compose.resources.painterResource
import tvpulse.composeapp.generated.resources.Res
import tvpulse.composeapp.generated.resources.landing_background


@Composable
fun TvShowScreen(
    id: Int,
    navController: NavHostController,
//    viewModel: MovieViewModel = viewModel { MovieViewModel(id) },
) {

    LazyHeadedList(
        //maxHeaderHeight = 320.dp,
        minHeaderHeight = 80.dp,
        header = {
            Image(
                painter = painterResource(Res.drawable.landing_background),
                contentDescription = "Poster",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth().height(240.dp)
            )
            Text("It Works")
        },
        content = {
                items(100) { index ->
                    Text(
                        "I'm item $index", modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    )
            }
        }
    )

}
