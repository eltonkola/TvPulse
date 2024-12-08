package org.eltonkola.tvpulse.data.remote.model

import kotlinx.serialization.Serializable


@Serializable
data class TmdbListResponse<T>(
    val page: Int,
    val results: List<T>,
    val total_pages: Int,
    val total_results: Int
)


@Serializable
data class TrendingMovieDetails(
    val id: Int,
    val backdrop_path: String?,
    val title: String,
    val original_title: String,
    val overview: String,
    val poster_path: String?,
    val vote_average: Double,
    val vote_count: Int,
    val release_date: String,
    val popularity: Double,
    val adult: Boolean,
    val video: Boolean,
    val genre_ids: List<Int>,
    val original_language: String,
    val media_type: String,
    val saved: Boolean = false
)

@Serializable
data class TrendingTvShowDetails(
    val id: Int,
    val name: String,
    val original_name: String,
    val overview: String,
    val poster_path: String?,
    val backdrop_path: String?,
    val vote_average: Double,
    val vote_count: Int,
    val first_air_date: String,
    val last_air_date: String? = null,
    val popularity: Double,
    val genre_ids: List<Int>,
    val original_language: String,
    val origin_country: List<String>,
    val adult: Boolean,
    val saved: Boolean = false
)


@Serializable
data class GenreDetails(
    val id: Int,
    val name: String
)

@Serializable
data class MovieDetails(
    val id: Int,
    val backdrop_path: String?,
    val title: String,
    val original_title: String,
    val overview: String,
    val poster_path: String?,
    val vote_average: Double,
    val vote_count: Int,
    val release_date: String,
    val popularity: Double,
    val adult: Boolean,
    val video: Boolean,
    val genres: List<GenreDetails>,
    val original_language: String,
    val runtime: Int
)

@Serializable
data class TvShowDetails(
    val id: Int,
    val name: String,
    val original_name: String,
    val overview: String,
    val poster_path: String?,
    val backdrop_path: String?,
    val vote_average: Double,
    val vote_count: Int,
    val first_air_date: String,
    val last_air_date: String? = null,
    val popularity: Double,
    val genres: List<GenreDetails>,
    val original_language: String,
    val origin_country: List<String>,
    val adult: Boolean,
    val number_of_episodes: Int,
    val number_of_seasons: Int,
    val seasons: List<Seasons>
)

@Serializable
data class Seasons(
    val air_date: String,
    val episode_count: Int,
    val id: Int,
    val name: String,
    val overview: String?,
    val poster_path: String?,
    val season_number: Int,
    val vote_average: Double,
)
