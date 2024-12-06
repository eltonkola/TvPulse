package org.eltonkola.tvpulse.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

@Composable
fun DoubleTabScreen(
    tab1Name: String,
    tab1Content: @Composable () -> Unit,
    tab2Name: String,
    tab2Content: @Composable () -> Unit
) {
    var selectedTabIndex by remember { mutableStateOf(0) }

    Column() {
        // Tabs
        TabRow(
            selectedTabIndex = selectedTabIndex,
            containerColor = MaterialTheme.colorScheme.surface
        ) {
            Tab(
                selected = selectedTabIndex == 0,
                onClick = { selectedTabIndex = 0 },
                text = { Text(tab1Name) }
            )
            Tab(
                selected = selectedTabIndex == 1,
                onClick = { selectedTabIndex = 1 },
                text = { Text(tab2Name) }
            )
        }

        // Tab content
        when (selectedTabIndex) {
            0 -> tab1Content()
            1 -> tab2Content()
        }
    }
}
