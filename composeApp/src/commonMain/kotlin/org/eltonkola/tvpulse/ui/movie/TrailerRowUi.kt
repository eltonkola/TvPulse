package org.eltonkola.tvpulse.ui.movie

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.composables.icons.lucide.*
import org.eltonkola.tvpulse.data.remote.model.VideoResult

@Composable
fun TrailerRowUi(trailer: VideoResult, playTrailer:(String) -> Unit ) {
    Column (
        modifier = Modifier.fillMaxWidth().padding(8.dp)
    ){
        Spacer(modifier = Modifier.size(2.dp))


        Row(
            modifier = Modifier.fillMaxWidth()
        ){
            Box(
                modifier = Modifier.width(160.dp).aspectRatio(16f/9f).clip(RoundedCornerShape(4.dp))
                    .clickable{ playTrailer("https://youtube.com/${trailer.key}") } ,
                contentAlignment = Alignment.Center
            ){
                AsyncImage(
                    model = "https://img.youtube.com/vi/${trailer.key}/hqdefault.jpg",
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.surface),
                    contentScale = ContentScale.Crop
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color.Black.copy(alpha = 0.2f),
                                    Color.Black.copy(alpha = 0.4f),
                                    Color.Black.copy(alpha = 1f)
                                )
                            )
                        )
                )
                Icon(
                    imageVector = Lucide.CirclePlay,
                    contentDescription = null,
                    modifier = Modifier.size(32.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            Spacer(modifier = Modifier.size(8.dp))

            Column{
                Text(
                    text = "Watch trailer",
                    modifier = Modifier.fillMaxWidth(),
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = trailer.name,
                    modifier = Modifier.fillMaxWidth(),
                    style = MaterialTheme.typography.titleSmall
                )
            }

        }

    }
    Spacer(modifier = Modifier.size(2.dp))

}
