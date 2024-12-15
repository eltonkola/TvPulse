package org.eltonkola.tvpulse.data.db.model

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmInstant
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

enum class MediaType(val mediaType: String) {
    MOVIE("movie"),
    TV_SHOW ("tv")
}

enum class WatchStatus(val status: String) {
    NOT_WATCHED("Not Watched"), // default, when adding show
    WATCHING("Watching"), // when we track an episode
    COMPLETED("Completed"), // when we track an episode, and it is the last one of the season, and the seas is final (not returning)
    ON_HOLD("On Hold") //user stops watching the show for a while (manually, when manually goes back, we will sync the data)
}

class MediaEntity : RealmObject {


    @PrimaryKey
    var id: Int = 0
    var title: String = ""
    var type: String = MediaType.TV_SHOW.mediaType
    var posterPath: String? = null
    var backdropPath: String? = null
    var overview: String? = null

    // Basic metadata
    var releaseDate: String? = null
    var genres: RealmList<GenreEntity> = realmListOf()
    var originalLanguage: String? = null

    // Tracking metadata
    var userWatchStatus: String = WatchStatus.NOT_WATCHED.status
    var userRating: Int? = null
    var userEmotion: Int? = null
    var userComment: String? = null

    // Minimal stats
    var voteAverage: Double = 0.0
    var voteCount: Int = 0
    var popularity: Double = 0.0

    // For Movies
    var runtime: Int? = null // movie length in minutes

    var isFavorite: Boolean = false

    var updatedTimestamp: RealmInstant = RealmInstant.now() //will use it to trigger flow updates when adding, removing episodes



    // For TV Shows
    var numberOfSeasons: Int = 0
    var numberOfEpisodes: Int = 0
    var episodes: RealmList<EpisodeEntity> = realmListOf() //we will use this to track watched episodes
    var seasons: RealmList<SeasonEntity> = realmListOf() //we will use this to track progress in combination with episodes

    var lastEpisodeToAir: EpisodeDetails? = null //will use for progress of a running season
    var nextEpisodeToAir: EpisodeDetails? = null //will use for list of future episodes


    fun progress(): Float {
        val totalEpisodes = seasons.sumOf {
            if(it.season_number == 0){
                0
            }else if(this.lastEpisodeToAir?.seasonNumber == it.season_number){
                this.lastEpisodeToAir!!.episodeNumber
            }else{
                it.episode_count
            }
        }
        val watchedEpisodes = episodes.size
        if (totalEpisodes == 0) return 0.0f
        return watchedEpisodes.toFloat() / totalEpisodes.toFloat()
    }

    class EpisodeDetails : RealmObject {
        @PrimaryKey
        var id: Int = 0
        var episodeNumber: Int = 0
        var seasonNumber: Int = 0
        var airDate: String = ""
    }




    var mediaType: MediaType
        get() = MediaType.entries.first { it.mediaType == type }
        set(value) {
            type = value.mediaType
        }

    var mediaStatus: WatchStatus
        get() = WatchStatus.entries.first { it.status == userWatchStatus }
        set(value) {
            userWatchStatus = value.status
        }

}
