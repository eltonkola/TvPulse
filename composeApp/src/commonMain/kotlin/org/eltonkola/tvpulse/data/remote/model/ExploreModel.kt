package org.eltonkola.tvpulse.data.remote.model

import kotlinx.serialization.SerialName
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
    val media_type: String?=null,
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
    val runtime: Int,


    val homepage: String? = null,
    val budget: Long = 0,
    val revenue : Long = 0,
    val status : String = "",
    val tagline : String = "",
    val belongsToCollection : String ?= null,

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


@Serializable
data class MovieVideosResponse(
    val id: Int,
    val results: List<VideoResult>
)

@Serializable
data class VideoResult(
    @SerialName("iso_639_1") val language: String,
    @SerialName("iso_3166_1") val region: String,
    val name: String,
    val key: String, // YouTube video key
    val site: String,
    val size: Int,
    val type: String,
    val official: Boolean,
    @SerialName("published_at") val publishedAt: String,
    val id: String
)


@Serializable
data class MovieCreditsResponse(
    val id: Int,
    val cast: List<CastMember>,
    val crew: List<CrewMember>
)

@Serializable
data class CastMember(
    val id: Int,
    val name: String,
    val character: String,
    @SerialName("profile_path") val profilePath: String?
)

@Serializable
data class CrewMember(
    val id: Int,
    val name: String,
    val department: String,
    val job: String,
    @SerialName("profile_path") val profilePath: String?
)


@Serializable
data class Person(
    val adult: Boolean,
    @SerialName("also_known_as") val alsoKnownAs: List<String>,
    val biography: String,
    val birthday: String? = null,
    val deathday: String?,
    val gender: Int,
    val homepage: String?,
    val id: Int,
    @SerialName("imdb_id") val imdbId: String,
    @SerialName("known_for_department")val knownForDepartment: String,
    val name: String,
    @SerialName("place_of_birth")val placeOfBirth: String,
    val popularity: Double,
    @SerialName("profile_path")val profilePath: String
)


