package org.eltonkola.tvpulse.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage

@Composable
fun MediaCard(
    posterUrl: String,
    onClick: () -> Unit,
    inDays: Int = 0
    ) {

    Card (
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(2f / 3f) // Typical poster aspect ratio
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(8.dp),
    ) {
        Box(modifier = Modifier.fillMaxSize()){
            AsyncImage(
                model = "https://image.tmdb.org/t/p/w500/$posterUrl",
                contentDescription = null,
                modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.surface),
                contentScale = ContentScale.Crop
            )
//            if(inDays > 0){
//                Column(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .padding(8.dp),
//                    verticalArrangement = Arrangement.Bottom
//                ) {
//                    Text(
//                        text = inDays.toString(),
//                        fontSize = 16.sp
//                    )
//                    Text(
//                        text = if(inDays > 1) "DAYS" else "DAY",
//                        fontSize = 12.sp
//                    )
//                }
//            }
        }

    }

}