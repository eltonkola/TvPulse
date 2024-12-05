package org.eltonkola.tvpulse.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
@Serializable
data class TvShowResponse(
    val page: Int,
    val results: List<TvShow>,
    val total_pages: Int, // Include total_pages
    val total_results: Int // Include total_results
)

@Serializable
data class TvShow(
    val backdrop_path: String?,
    val id: Int,
    val name: String,
    val original_name: String,
    val overview: String,
    val poster_path: String?,
    val media_type: String?,
    val adult: Boolean,
    val original_language: String,
    val genre_ids: List<Int>,
    val popularity: Double,
    val first_air_date: String?,
    val vote_average: Double,
    val vote_count: Int,
    val origin_country: List<String>
)