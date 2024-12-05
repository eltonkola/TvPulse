package org.eltonkola.tvpulse.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TvShow(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("overview") val overview: String? = null,
    @SerialName("poster_path") val posterPath: String? = null,
    @SerialName("vote_average") val voteAverage: Double? = null
)

@Serializable
data class TrendingTvShowsResponse(
    @SerialName("results") val results: List<TvShow>
)
