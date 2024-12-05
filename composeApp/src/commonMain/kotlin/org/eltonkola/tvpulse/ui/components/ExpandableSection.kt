package org.eltonkola.tvpulse.ui.components



import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.ArrowDown
import com.composables.icons.lucide.Lucide
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ExpandableSection(
    title: String,
    expanded: Boolean,
    onExpand: () -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp)
            .border(
                width = 0.5.dp,
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = RoundedCornerShape(4.dp)
            )
            .clip(RoundedCornerShape(4.dp))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onExpand() }
                .background(MaterialTheme.colorScheme.primaryContainer)
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.size(8.dp))
            Icon(
                imageVector = Lucide.ArrowDown,
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp)
                    .rotate(if (expanded) 180f else 0f)
            )
        }
        if (expanded) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                content()
            }
        }
    }

}

@Preview
@Composable
fun ExpandableSectionPreview() {
    Column {

        ExpandableSection(
            expanded = true,
            title = "Local",
            onExpand = {}
        ) {
            Spacer(modifier = Modifier.size(80.dp))
            Text(text = "something")
            Spacer(modifier = Modifier.size(80.dp))
        }
        Spacer(modifier = Modifier.size(80.dp))

        ExpandableSection(
            expanded = false,
            title = "Local",
            onExpand = {}
        ) {
            Spacer(modifier = Modifier.size(80.dp))
            Text(text = "something")
            Spacer(modifier = Modifier.size(80.dp))
        }


    }

}