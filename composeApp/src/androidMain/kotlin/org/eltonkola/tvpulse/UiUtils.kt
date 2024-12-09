package org.eltonkola.tvpulse

import androidx.compose.runtime.Composable
import org.eltonkola.tvpulse.data.local.model.WatchingStats
import org.eltonkola.tvpulse.ui.main.profile.StatsUi
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
@Preview // now we are able to call the preview annotation
fun StatsUiPreview() {
    StatsUi(
        stats = WatchingStats(
            tvMonths = 14,
            tvDays = 29,
            tvHours = 21,
            episodesWatched = 14959,
            moviesMonths = 0,
            moviesDays = 25,
            moviesHours = 14,
            moviesWatched = 335
        )
    )
}
