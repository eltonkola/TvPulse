package org.eltonkola.tvpulse.ui.main.profile

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.eltonkola.tvpulse.ui.components.DividerLine

@Composable
fun ProfileNumbers(
    nrMovies: Int,
    nrTvShows: Int,
    nrNotes: Int
){
    Column(
        modifier = Modifier.fillMaxWidth().height(68.dp),
    ) {
        DividerLine()

        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Column(
                modifier = Modifier.weight(1f).height(66.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = nrMovies.toString(),
                    color = Color.White,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
                Spacer(
                    modifier = Modifier.size(6.dp)
                )
                Text(
                    text = "movies",
                    color = Color.White,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
            DividerLine(height = 66.dp, width = 0.5.dp)

            Column(
                modifier = Modifier.weight(1f).height(66.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = nrTvShows.toString(),
                    color = Color.White,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
                Spacer(
                    modifier = Modifier.size(6.dp)
                )
                Text(
                    text = "tv shows",
                    color = Color.White,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }

            DividerLine(height = 66.dp, width = 0.5.dp)
            Column(
                modifier = Modifier.weight(1f).height(66.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = nrNotes.toString(),
                    color = Color.White,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
                Spacer(
                    modifier = Modifier.size(6.dp)
                )
                Text(
                    text = "notes",
                    color = Color.White,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }

        }


        DividerLine()

    }
}

