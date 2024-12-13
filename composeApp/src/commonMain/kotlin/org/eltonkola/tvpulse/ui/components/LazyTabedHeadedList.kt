package org.eltonkola.tvpulse.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * A composable that implements a lazy list with a sticky header. The header sticks to the top of
 * the screen until it reaches the top of the screen, at which point it begins to collapse until it
 * reaches the minimum height, at which point it stops collapsing.
 *
 * @param maxHeaderHeight The maximum height of the header.
 * @param minHeaderHeight The minimum height of the header.
 * @param header A composable that represents the header.
 * @param content A composable that represents the content of the list.
 *
 * @author Elton Kola, <eltonkola@gmail.com>
 */

@Composable
fun LazyTabedHeadedList(
    modifier: Modifier = Modifier,
    tabs: List<Pair<String, LazyListScope.() -> Unit>>,
    minHeaderHeight: Dp = 100.dp,
    header: @Composable () -> Unit
) {
    var selectedTabIndex by rememberSaveable { mutableStateOf(0) }

    LazyHeadedList(
        modifier = modifier.background(MaterialTheme.colorScheme.surface),
        minHeaderHeight = minHeaderHeight,
        header = {
            Column(
                modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.background)
            ) {
                header.invoke()
                TabRow(
                    selectedTabIndex = selectedTabIndex,
                    containerColor = MaterialTheme.colorScheme.background
                ) {
                    tabs.forEachIndexed { index, tab ->
                        Tab(
                            modifier = Modifier.fillMaxSize(),
                            selected = selectedTabIndex == index,
                            onClick = { selectedTabIndex = index },
                            text = { Text(tab.first) } // Tab name
                        )
                    }
                }
            }
        },
        content = tabs[selectedTabIndex].second
    )

}

