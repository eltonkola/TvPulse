package org.eltonkola.tvpulse.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
fun LazyHeadedList(
    minHeaderHeight: Dp = 100.dp,
    header: @Composable BoxScope.() -> Unit,
    content: LazyListScope.() -> Unit
) {
    val maxHeaderHeightPx = remember { mutableStateOf(0) } // Store maxHeaderHeight in pixels
    val minHeaderHeightPx = with(LocalDensity.current) { minHeaderHeight.roundToPx() }

    val toolbarOffsetHeightPx = remember { mutableStateOf(0f) }

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val delta = available.y
                val newOffset = toolbarOffsetHeightPx.value + delta
                toolbarOffsetHeightPx.value = newOffset.coerceIn(-maxHeaderHeightPx.value.toFloat() + minHeaderHeightPx, 0f)
                return Offset.Zero
            }
        }
    }

    Box(
        Modifier
            .fillMaxSize()
            .nestedScroll(nestedScrollConnection)
    ) {
        LazyColumn(
            contentPadding = PaddingValues(top = with(LocalDensity.current) { maxHeaderHeightPx.value.toDp() }),
            content = content
        )

        Box(
            modifier = Modifier
                .onGloballyPositioned { coordinates ->
                    maxHeaderHeightPx.value = coordinates.size.height
                }
                .offset { IntOffset(x = 0, y = toolbarOffsetHeightPx.value.roundToInt()) }
                .background(Color.Red),
            contentAlignment = Alignment.BottomCenter,
            content = header
        )
    }
}

