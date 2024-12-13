package org.eltonkola.tvpulse.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.composables.icons.lucide.CircleCheck
import com.composables.icons.lucide.Lucide

@Composable
fun UiCheck(
    checked: Boolean,
    onCheck: () -> Unit
) {
    IconButton(onClick = onCheck){
        Icon(
            imageVector = Lucide.CircleCheck,
            contentDescription = null,
            tint = if(checked) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground,
        )
    }
}
