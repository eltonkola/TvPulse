package org.eltonkola.tvpulse.ui.movie

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.*
import org.eltonkola.tvpulse.data.remote.model.MovieDetails
import org.jetbrains.compose.resources.painterResource
import tvpulse.composeapp.generated.resources.Res
import tvpulse.composeapp.generated.resources.logo

@Composable
fun MovieInfoRowUi(fullMovie: MovieDetails) {
    Column (
        modifier = Modifier.fillMaxWidth().padding(8.dp)
    ){
        Spacer(modifier = Modifier.size(2.dp))

        Text(
            text = "Movie Info",
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.size(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ){
            Image(
                painter = painterResource(Res.drawable.logo),
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.size(4.dp))
            StarProgressUi(fullMovie.vote_average)
            Spacer(modifier = Modifier.size(2.dp))
            Text(
                text = "${fullMovie.vote_average}/10 (${fullMovie.vote_count} votes)",
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Spacer(modifier = Modifier.size(8.dp))

        Text(
            text = fullMovie.overview,
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.size(2.dp))
    }
}

@Composable
fun StarProgressUi(
    score: Double,
    modifier: Modifier = Modifier,
    size: Dp = 18.dp,
    onRate:(Int) -> Unit = {}
) {
    val primaryColor = MaterialTheme.colorScheme.primary
    val defaultColor = Color.Gray

    Row(modifier = modifier) {
        for (i in 1..10) {
            val color = when {
                score >= i -> primaryColor
                score > i - 1 -> {
                    primaryColor.copy(alpha = 0.5f)
                }
                else -> defaultColor
            }
            Icon(
                imageVector = Lucide.Star,
                contentDescription = "Star $i",
                tint = color,
                modifier = Modifier.size(size).clickable { onRate(i) }
            )
        }
    }
}


