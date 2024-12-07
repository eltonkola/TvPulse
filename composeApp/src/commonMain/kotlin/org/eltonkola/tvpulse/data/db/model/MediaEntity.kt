package org.eltonkola.tvpulse.data.db.model

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

enum class MediaType(val type: String) {
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
    var type: String = MediaType.TV_SHOW.type
    var posterPath: String? = null
    var backdropPath: String? = null
    var overview: String? = null

    // Basic metadata
    var releaseDate: String? = null
    var genres: RealmList<GenreEntity> = realmListOf()
    var originalLanguage: String? = null

    // Tracking metadata
    var userWatchStatus: String = WatchStatus.NOT_WATCHED.status
    var userRating: Float? = null
    var personalNotes: String? = null

    // Minimal stats
    var voteAverage: Float = 0f
    var voteCount: Int = 0
    var popularity: Float = 0f

    // For TV Shows
    var numberOfSeasons: Int = 0
    var numberOfEpisodes: Int = 0

    // For Movies
    var runtime: Int? = null // movie length in minutes


    var mediaType: MediaType
        get() = MediaType.entries.first { it.type == type }
        set(value) {
            type = value.type
        }

    var mediaStatus: WatchStatus
        get() = WatchStatus.entries.first { it.status == userWatchStatus }
        set(value) {
            userWatchStatus = value.status
        }

}
