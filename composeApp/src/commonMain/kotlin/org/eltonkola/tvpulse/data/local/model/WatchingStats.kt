package org.eltonkola.tvpulse.data.local.model

data class WatchingStats(
    val tvMonths: Int,
    val tvDays: Int,
    val tvHours: Int,
    val episodesWatched: Int,
    val moviesMonths: Int,
    val moviesDays: Int,
    val moviesHours: Int,
    val moviesWatched: Int
)