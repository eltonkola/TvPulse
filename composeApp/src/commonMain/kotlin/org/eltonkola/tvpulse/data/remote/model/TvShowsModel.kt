package org.eltonkola.tvpulse.data.remote.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient


@Serializable
data class SeasonsResponse(
    val id: Int,
    val fullSeasons: Map<String, Season> = emptyMap()
)


@Serializable
data class Season(
    val _id: String,
    val air_date: String,
    val episodes: List<Episode>,
    @Transient
    var isExpanded: MutableState<Boolean> = mutableStateOf(false)
)

@Serializable
data class Episode(
    val air_date: String?,
    val episode_number: Int,
    val episode_type: String,
    val id: Int,
    val name: String,
    val overview: String?,
    val production_code: String,
    val runtime: Int?,
    val season_number: Int,
    val show_id: Int,
    val still_path: String?="",
    val vote_average: Double,
    val vote_count: Int,
    val crew: List<CrewMember>,
    val guest_stars: List<GuestStar>,
    @Transient
    var isWatched: Boolean = false
)

@Serializable
data class GuestStar(
    val character: String,
    val credit_id: String,
    val order: Int,
    val adult: Boolean,
    val gender: Int,
    val id: Int,
    val known_for_department: String,
    val name: String,
    val original_name: String,
    val popularity: Double,
    val profile_path: String?
)

