package org.eltonkola.tvpulse.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun ProgressUi(
    modifier: Modifier = Modifier.fillMaxWidth(),
    progress: Float = 0.0f,
    height: Dp = 8.dp,
    progressColor: Color = MaterialTheme.colorScheme.primary,
    backgroundColor: Color = MaterialTheme.colorScheme.surfaceVariant,
    completeColor: Color = Color.Green
) {
    // Clamp progress between 0 and 1 to avoid unexpected behavior
    val clampedProgress = progress.coerceIn(0f, 1f)

    Row(
        modifier = modifier
            .height(height)
            .background(backgroundColor)
    ) {
        // Progress bar
        if (clampedProgress > 0f) {
            Box(
                modifier = Modifier
                    .height(height)
                    .weight(clampedProgress)
                    .background(
                        if (clampedProgress == 1f) completeColor else progressColor
                    )
            )
        }
        // Empty space for remaining progress
        if (clampedProgress < 1f) {
            Spacer(
                modifier = Modifier
                    .height(height)
                    .weight(1f - clampedProgress)
            )
        }
    }
}