package org.eltonkola.tvpulse.ui.main.explore

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
import org.eltonkola.tvpulse.ui.TrendingShowsScreen


@Composable
fun ExploreTab(
    navController: NavController,
) {


    TrendingShowsScreen()

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExploreAppBar(
    navController: NavController
) {
    TopAppBar(
        title = {
            Row {
                Icon(Lucide.Activity, null)
                Spacer(modifier = Modifier.width(12.dp))
                Text(text = "Explore")
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