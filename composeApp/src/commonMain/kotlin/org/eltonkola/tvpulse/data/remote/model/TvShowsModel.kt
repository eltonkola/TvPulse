package org.eltonkola.tvpulse.data.remote.model

import kotlinx.serialization.Serializable


@Serializable
data class Episode(
    val air_date: String,
    val crew: List<Crew>,
    val episode_number: Int,
    val episode_type: String,
    val guest_stars: List<GuestStar>,
    val id: Int,
    val name: String,
    val overview: String,
    val production_code: String,
    val runtime: Int?=null,
    val season_number: Int,
    val show_id: Int,
    val still_path: String,
    val vote_average: Double,
    val vote_count: Int
)

@Serializable
data class Crew(
    val adult: Boolean,
    val credit_id: String,
    val department: String,
    val gender: Int,
    val id: Int,
    val job: String,
    val known_for_department: String,
    val name: String,
    val original_name: String,
    val popularity: Double,
    val profile_path: String?
)

@Serializable
data class GuestStar(
    val adult: Boolean,
    val character: String,
    val credit_id: String,
    val gender: Int,
    val id: Int,
    val known_for_department: String,
    val name: String,
    val order: Int,
    val original_name: String,
    val popularity: Double,
    val profile_path: String?
)

@Serializable
data class SeasonResponse(
    val _id: String,
    val air_date: String,
    val episodes: List<Episode>
)
