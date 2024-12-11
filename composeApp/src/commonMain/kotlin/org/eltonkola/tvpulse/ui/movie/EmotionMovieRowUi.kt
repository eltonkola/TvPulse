package org.eltonkola.tvpulse.ui.movie

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun EmotionMovieRowUi(
    selectedEmotion: Int,
    onRate: (Int) -> Unit
) {

    val emotions = listOf(
        "Shocked" to "😲",
        "Frustrated" to "😡",
        "Sad" to "😢",
        "Reflective" to "🤔",
        "Touched" to "🥹",
        "Amused" to "😂",
        "Scared" to "😱",
        "Bored" to "😴",
        "Understood" to "🤝",
        "Thrilled" to "😄",
        "Confused" to "😕",
        "Tense" to "😬"
    )

    Column (
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){

        Text(
            text = "How did you feel?",
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.size(8.dp))

        FlowRow(
            modifier = Modifier.padding(8.dp),
            maxItemsInEachRow = 4,
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            emotions.forEachIndexed { index, pair ->
                Box(
                    modifier = Modifier
                        .size(84.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(
                            if (selectedEmotion == index) MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
                            else MaterialTheme.colorScheme.surface
                        )
                        .clickable {
                            onRate(index)
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text =  pair.second,
                            fontSize = 42.sp,
                            modifier = Modifier.padding(4.dp)
                        )
                        Text(
                            text = pair.first,
                            style = MaterialTheme.typography.bodySmall,
                            textAlign = TextAlign.Center,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }

    }
}
