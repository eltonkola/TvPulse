package org.eltonkola.tvpulse.ui.movie

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun RateMovieRowUi(
    rate: Int,
    onRate: (Int) -> Unit
) {
    Column (
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){

        Text(
            text = "Rate this movie",
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.size(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            StarProgressUi(
                score = rate.toDouble(),
                modifier = Modifier,
                size = 32.dp,
                onRate = onRate
            )
        }
        Spacer(modifier = Modifier.size(16.dp))
        Text(
            text = if(rate==0) "Your have not rated this movie yet!" else "You rated this movie $rate/10",
            style = MaterialTheme.typography.titleSmall
        )

    }
}
