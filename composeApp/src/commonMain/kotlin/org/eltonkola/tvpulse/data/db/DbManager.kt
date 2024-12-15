package org.eltonkola.tvpulse.data.db


import co.touchlab.kermit.Logger
import io.realm.kotlin.MutableRealm
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.exceptions.RealmException
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.eltonkola.tvpulse.data.db.model.*
import org.eltonkola.tvpulse.data.db.model.MediaEntity.EpisodeDetails
import org.eltonkola.tvpulse.data.remote.model.Episode
import org.eltonkola.tvpulse.data.remote.model.Seasons


class DbManager {

    private val realm: Realm

   // private val encryptionKey = "TrackTheMoviesTrackTheSHowsDontTrackTheUsersSupportThisAppPlease".encodeToByteArray()

    init{
        val config = RealmConfiguration.Builder(
            schema = setOf(MediaEntity::class, GenreEntity::class, EpisodeEntity::class, SeasonEntity::class, EpisodeDetails::class)
        )
            //.encryptionKey(encryptionKey)
            .schemaVersion(1) // Keep the schema version at 1
            .deleteRealmIfMigrationNeeded() // Delete the Realm file if a migration is needed
            .build()

        realm = Realm.open(config)
        Logger.i { "openDatabase - realm: $realm" }
    }


    suspend fun <R> writeTransaction(block: MutableRealm.() -> R): R {
        return realm.write { block() }
    }

    fun getOrCreateGenre(mutableRealm: MutableRealm, genreId: Int, genreName: String): GenreEntity {
        return mutableRealm.query<GenreEntity>("id == $0", genreId).find().firstOrNull()
            ?: mutableRealm.copyToRealm(GenreEntity().apply {
                id = genreId
                title = genreName
            })
    }

    fun getOrCreateSeason(mutableRealm: MutableRealm, season: Seasons): SeasonEntity {
        return mutableRealm.query<SeasonEntity>("id == $0", season.id).find().firstOrNull()
            ?: mutableRealm.copyToRealm(SeasonEntity().apply {
                id = season.id
                air_date = season.air_date
                episode_count = season.episode_count
                name = season.name
                overview = season.overview
                poster_path = season.poster_path
                season_number = season.season_number
                vote_average = season.vote_average
            })
    }


    fun getMoviesFlow(): Flow<List<MediaEntity>> {
        return realm.query<MediaEntity>("type == $0", MediaType.MOVIE.mediaType).asFlow() // Observe changes
            .map { it.list } // Extract the list from the query result
    }

    fun getTvShowsFlow(): Flow<List<MediaEntity>> {
        return realm.query<MediaEntity>("type == $0", MediaType.TV_SHOW.mediaType).asFlow() // Observe changes
            .map { it.list } // Extract the list from the query result
    }

    fun getFavoriteMoviesFlow(): Flow<List<MediaEntity>> {
        return realm.query<MediaEntity>("type == $0 and isFavorite == true", MediaType.MOVIE.mediaType).asFlow() // Observe changes
            .map { it.list } // Extract the list from the query result
    }

    fun getFavoriteTvShowsFlow(): Flow<List<MediaEntity>> {
        return realm.query<MediaEntity>("type == $0 and isFavorite == true", MediaType.TV_SHOW.mediaType).asFlow() // Observe changes
            .map { it.list } // Extract the list from the query result
    }


    // Close the database
    fun close() {
        realm.close()
    }

    fun getMediaFlowById(id: Int): Flow<MediaEntity?> {
        return realm.query<MediaEntity>("id == $0", id)
            .asFlow()
            .map { results ->
                results.list.firstOrNull()
            }
    }


    suspend fun setMediaWatchedStatus(status: WatchStatus, id: Int) : Boolean {
        return try {
            realm.write {
                // Query the entity by its ID
                val mediaEntity = query<MediaEntity>("id == $0", id).first().find()
                if (mediaEntity != null) {
                    // Update the userWatchStatus field
                    mediaEntity.userWatchStatus = status.status
                } else {
                    throw IllegalArgumentException("MediaEntity with id $id not found.")
                }
            }
            true // Success
        } catch (e: RealmException) {
            e.printStackTrace()
            false // Failure
        }
    }

    suspend fun setMediaFavorites(favorites:Boolean, id: Int) : Boolean {
        return try {
            realm.write {
                // Query the entity by its ID
                val mediaEntity = query<MediaEntity>("id == $0", id).first().find()
                if (mediaEntity != null) {
                    // Update the userWatchStatus field
                    mediaEntity.isFavorite = favorites
                } else {
                    throw IllegalArgumentException("MediaEntity with id $id not found.")
                }
            }
            true // Success
        } catch (e: RealmException) {
            e.printStackTrace()
            false // Failure
        }
    }
    suspend fun setMediaRating(rate: Int, id: Int) : Boolean {
        return try {
            realm.write {
                // Query the entity by its ID
                val mediaEntity = query<MediaEntity>("id == $0", id).first().find()
                if (mediaEntity != null) {
                    // Update the userWatchStatus field
                    mediaEntity.userRating = rate
                } else {
                    throw IllegalArgumentException("MediaEntity with id $id not found.")
                }
            }
            true // Success
        } catch (e: RealmException) {
            e.printStackTrace()
            false // Failure
        }
    }
    suspend fun setEmotionMediaScore(emotion: Int, id: Int) : Boolean {
        return try {
            realm.write {
                // Query the entity by its ID
                val mediaEntity = query<MediaEntity>("id == $0", id).first().find()
                if (mediaEntity != null) {
                    // Update the userWatchStatus field
                    mediaEntity.userEmotion = emotion
                } else {
                    throw IllegalArgumentException("MediaEntity with id $id not found.")
                }
            }
            true // Success
        } catch (e: RealmException) {
            e.printStackTrace()
            false // Failure
        }
    }

    suspend fun updateComment(comment: String, id: Int) : Boolean {
        return try {
            realm.write {
                // Query the entity by its ID
                val mediaEntity = query<MediaEntity>("id == $0", id).first().find()
                if (mediaEntity != null) {
                    // Update the userWatchStatus field
                    mediaEntity.userComment = comment
                } else {
                    throw IllegalArgumentException("MediaEntity with id $id not found.")
                }
            }
            true // Success
        } catch (e: RealmException) {
            e.printStackTrace()
            false // Failure
        }
    }

    fun getOrCreateEpisode(mutableRealm: MutableRealm, episode: Episode) : EpisodeDetails {
        return mutableRealm.query<EpisodeDetails>("id == $0", episode.id).find().firstOrNull()
            ?: mutableRealm.copyToRealm(EpisodeDetails().apply {
                id = episode.id
                airDate = episode.air_date ?: ""
                episodeNumber = episode.episode_number
                seasonNumber = episode.season_number
            })
    }

    fun getWatchingTvShows(): List<MediaEntity> {
        return realm.query<MediaEntity>("userWatchStatus == $0 and type == $1", WatchStatus.WATCHING.status, MediaType.TV_SHOW.mediaType).find()
    }

}
