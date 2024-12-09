package org.eltonkola.tvpulse.ui.main.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.composables.icons.lucide.Film
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Tv
import org.eltonkola.tvpulse.data.local.model.WatchingStats

@Composable
fun StatsUi(
    stats: WatchingStats
) {

    LazyRow (
        modifier = Modifier.fillMaxWidth().padding(start = 8.dp, end = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ){
        item {
            TimeStats(
                title = "Tv Time",
                icon = Lucide.Tv,
                months = stats.tvMonths,
                days = stats.tvDays,
                hours = stats.tvHours
            )
        }
        item{
            NumberStats(
                title = "Episodes Watched",
                icon = Lucide.Tv,
                total = stats.episodesWatched
            )
        }

        item {
            TimeStats(
                title = "Movie Time",
                icon = Lucide.Film,
                months = stats.moviesMonths,
                days = stats.moviesDays,
                hours = stats.moviesHours
            )
        }
        item{
            NumberStats(
                title = "Movies Watched",
                icon = Lucide.Film,
                total = stats.moviesWatched
            )
        }
    }

}

@Composable
private fun TimeStats(
    title: String,
    icon: ImageVector,
    months: Int,
    days: Int,
    hours: Int
) {

   Box(
       modifier = Modifier.size(210.dp, 100.dp).border(
           width = 1.dp,
           color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
           shape = RoundedCornerShape(4.dp)
       ),
   ) {
       Column {
           Row(
               modifier = Modifier.fillMaxWidth().height(34.dp),
               verticalAlignment = Alignment.CenterVertically,
               horizontalArrangement = Arrangement.Center
           ) {
               Icon(imageVector = icon, contentDescription = null, modifier = Modifier.size(16.dp))
               Spacer(modifier = Modifier.size(4.dp))
               Text(text = title, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
           }
           Spacer(
               modifier = Modifier.fillMaxWidth().height(1.dp).background(MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f))
           )
           Row(
             modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp)
           ) {
               Column(
                   modifier = Modifier.weight(1f).fillMaxHeight(),
                   horizontalAlignment = Alignment.CenterHorizontally,
                   verticalArrangement = Arrangement.Center
               ) {
                   Text(
                       text = months.toString(),
                       fontSize = 22.sp,
                       fontWeight = FontWeight.Bold
                   )
                   Spacer(
                       modifier = Modifier.size(2.dp)
                   )
                   Text(
                       text = "MONTHS",
                       fontSize = 12.sp,
                       fontWeight = FontWeight.Bold
                   )
               }
               Column(
                   modifier = Modifier.weight(1f).fillMaxHeight(),
                   horizontalAlignment = Alignment.CenterHorizontally,
                   verticalArrangement = Arrangement.Center
               ) {
                   Text(
                       text = days.toString(),
                       fontSize = 22.sp,
                       fontWeight = FontWeight.Bold
                   )
                   Spacer(
                       modifier = Modifier.size(2.dp)
                   )
                   Text(
                       text = "DAYS",
                       fontSize = 12.sp,
                       fontWeight = FontWeight.Bold
                   )
               }
               Column(
                   modifier = Modifier.weight(1f).fillMaxHeight(),
                   horizontalAlignment = Alignment.CenterHorizontally,
                   verticalArrangement = Arrangement.Center
               ) {
                   Text(
                       text = hours.toString(),
                       fontSize = 22.sp,
                       fontWeight = FontWeight.Bold
                   )
                   Spacer(
                       modifier = Modifier.size(2.dp)
                   )
                   Text(
                       text = "HOURS",
                       fontSize = 12.sp,
                       fontWeight = FontWeight.Bold
                   )
               }
           }
       }
   }

}

@Composable
private fun NumberStats(
    title: String,
    icon: ImageVector,
    total: Int
) {

    Box(
        modifier = Modifier.size(210.dp, 100.dp).border(
            width = 1.dp,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
            shape = RoundedCornerShape(4.dp)
        ),
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth().height(34.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(imageVector = icon, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.size(4.dp))
                Text(text = title, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
            }
            Spacer(
                modifier = Modifier.fillMaxWidth().height(1.dp).background(MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f))
            )
            Row(
                modifier = Modifier.fillMaxWidth().weight(1f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = total.toString(),
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }

}

