package org.eltonkola.tvpulse.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class PersonCreditsResponse(
    val id: Int,
    val cast: List<CastCredit>,
    val crew: List<CrewCredit>
)

@Serializable
data class CastCredit(
    val adult: Boolean,
    val backdrop_path: String?,
    val character: String,
    val credit_id: String,
    val genre_ids: List<Int>,
    val id: Int,
    val original_language: String,
    val original_title: String? =null,
    val overview: String,
    val popularity: Double,
    val poster_path: String?,
    val release_date: String?=null,
    val title: String?=null,
    val video: Boolean?=null,
    val vote_average: Double,
    val vote_count: Int,
    val episode_count : Int?=null,
){
    fun isMovie() = episode_count == null
}


@Serializable
data class CrewCredit(
    val adult: Boolean,
    val backdrop_path: String?,
    val credit_id: String,
    val department: String,
    val genre_ids: List<Int>,
    val id: Int,
    val job: String,
    val original_language: String,
    val original_title: String?=null,
    val overview: String,
    val popularity: Double,
    val poster_path: String?,
    val release_date: String?=null,
    val title: String?=null,
    val video: Boolean?=null,
    val vote_average: Double,
    val vote_count: Int
)
