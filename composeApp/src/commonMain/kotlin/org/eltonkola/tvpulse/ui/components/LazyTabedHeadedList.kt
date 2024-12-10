package org.eltonkola.tvpulse.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import org.eltonkola.tvpulse.ui.movie.MovieHeader
import kotlin.math.roundToInt

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
    tabs: List<Pair<String, LazyListScope.() -> Unit>>,
    minHeaderHeight: Dp = 100.dp,
    header: @Composable () -> Unit
) {
    var selectedTabIndex by remember { mutableStateOf(0) }

    LazyHeadedList(
        minHeaderHeight = minHeaderHeight,
        header = {
            Column {
                header.invoke()
                TabRow(
                    selectedTabIndex = selectedTabIndex,
                    containerColor = MaterialTheme.colorScheme.surface
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

