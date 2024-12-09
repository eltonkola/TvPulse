package org.eltonkola.tvpulse.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.composables.icons.lucide.ChevronRight
import com.composables.icons.lucide.Lucide

@Composable
fun SectionHeader(
    modifier: Modifier = Modifier,
    title: String,
    icon: ImageVector? = null,
    action: () -> Unit,
) {
    Row(
        modifier = modifier.fillMaxWidth().padding(start = 18.dp, end = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if(icon != null) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color.Red
            )
            Spacer(modifier = Modifier.size(8.dp))
        }
        Text(
            text = title,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.weight(1f).height(1.dp))
        IconButton(onClick = action){
            Icon(
                imageVector = Lucide.ChevronRight,
                contentDescription = null,
            )
        }

    }
}