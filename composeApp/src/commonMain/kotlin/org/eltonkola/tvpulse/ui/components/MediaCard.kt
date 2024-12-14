package org.eltonkola.tvpulse.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage

@Composable
fun MediaCard(
    posterUrl: String,
    onClick: () -> Unit,
    inDays: Int = 0,
    progress : Float = 0f,
    isTvShow: Boolean = false
    ) {

    Card (
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(2f / 3f) // Typical poster aspect ratio
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(8.dp),
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomStart
            ){
            AsyncImage(
                model = "https://image.tmdb.org/t/p/w500/$posterUrl",
                contentDescription = null,
                modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.surface),
                contentScale = ContentScale.Crop
            )

            Column {
//                if(inDays > 0){
//                    Column (
//                        modifier = Modifier
//                            .fillMaxSize()
//                            .padding(8.dp),
//                        verticalArrangement = Arrangement.Bottom
//                    ) {
//                        Text(
//                            text = inDays.toString(),
//                            fontSize = 16.sp
//                        )
//                        Text(
//                            text = if(inDays > 1) "DAYS" else "DAY",
//                            fontSize = 12.sp
//                        )
//                    }
//                }

                if(isTvShow){
                    ProgressUi(
                        modifier = Modifier.fillMaxWidth(),
                        progress = progress
                    )
                }
            }

        }

    }

}