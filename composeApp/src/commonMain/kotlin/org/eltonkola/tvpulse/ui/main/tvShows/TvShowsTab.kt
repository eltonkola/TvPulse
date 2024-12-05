package org.eltonkola.tvpulse.ui.main.tvShows

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.composables.icons.lucide.Activity
import com.composables.icons.lucide.Lucide
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime


@Composable
fun TvShowsTab(
    navController: NavController,
) {

    Text("TV SHOWS")

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TvShowsAppBar(
    navController: NavController,
) {
    TopAppBar(
        title = {
            Row {
                Icon(Lucide.Activity, null)
                Spacer(modifier = Modifier.width(12.dp))
                Text(text = "TvShows")
            }
        },
        actions = {
//            IconButton( {
//                navController.navigate(AppsScreen.Exercises.name)
//            }){
//                Icon(Lucide.BicepsFlexed, null)
//            }
        }
    )
}