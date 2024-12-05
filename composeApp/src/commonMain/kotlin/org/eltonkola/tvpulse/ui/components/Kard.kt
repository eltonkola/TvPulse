package org.eltonkola.tvpulse.ui.components


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Settings

@Composable
fun Kard(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    title: String,
    showSettings: Boolean = false,
    onSettingsCLick: () -> Unit = {},
    content: @Composable ColumnScope.() -> Unit
) {

    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = modifier
    ){
        Row(
            modifier = Modifier.fillMaxWidth()
                .background(MaterialTheme.colorScheme.secondaryContainer)
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(icon, title)
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = title,
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.labelLarge
            )
            if(showSettings) {
                Spacer(modifier = Modifier.size(8.dp))
//                IconButton({
//                    onSettingsCLick()
//                }){
                Icon(
                    modifier = Modifier.size(24.dp).clickable { onSettingsCLick() },
                    imageVector = Lucide.Settings,
                    contentDescription = "settings",
                )
            }
//            }
        }
        Column(modifier = Modifier.padding(8.dp)) {
            content()
        }
    }

}