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
    NOT_WATCHED("Not Watched"),
    WATCHING("Watching"),
    COMPLETED("Completed"),
    ON_HOLD("On Hold"),
    DROPPED("Dropped")
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

    // For TV Shows
    var numberOfSeasons: Int = 0
    var numberOfEpisodes: Int = 0
    var episodes: RealmList<EpisodeEntity> = realmListOf() //we will use this to track watched episodes


    // For Movies
    var runtime: Int? = null // movie length in minutes

    var isFavorite: Boolean = false

    var updatedTimestamp: RealmInstant = RealmInstant.now() //will use it to trigger flow updates when adding, removing episodes



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
