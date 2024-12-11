package org.eltonkola.tvpulse.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun DividerLine(
    height: Dp = 0.5.dp,
    width: Dp = 0.dp
) {
    if(width == 0.dp){
        Spacer(
            modifier = Modifier.fillMaxWidth()
                .height(height)
                .background(MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f))
        )
    }else{
        Spacer(
            modifier = Modifier.width(width)
                .height(height)
                .background(MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f))
        )
    }
}