package org.eltonkola.tvpulse.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.TriangleAlert

@Composable
fun ErrorUi(
    message : String,
    retry: Boolean = false,
    retryLabel: String = "Retry",
    icon: ImageVector = Lucide.TriangleAlert,
    iconColor: Color = MaterialTheme.colorScheme.error,
    onRetry: () -> Unit = {},
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.aspectRatio(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconColor,
                modifier = Modifier.size(64.dp)
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(message)
            if(retry) {
                Spacer(modifier = Modifier.size(8.dp))
                Button(onClick = {
                    onRetry()
                }) {
                    Text(retryLabel)
                }
            }
        }
    }
}