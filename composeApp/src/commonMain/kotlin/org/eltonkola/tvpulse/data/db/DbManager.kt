package org.eltonkola.tvpulse.data.db


import co.touchlab.kermit.Logger
import io.realm.kotlin.MutableRealm
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.eltonkola.tvpulse.data.db.model.GenreEntity
import org.eltonkola.tvpulse.data.db.model.MediaEntity
import org.eltonkola.tvpulse.data.db.model.MediaType


class DbManager {

    private val realm: Realm

    private val encryptionKey = "TrackTheMoviesTrackTheSHowsDontTrackTheUsersSupportThisAppPlease".encodeToByteArray()

    init{
        val config = RealmConfiguration.Builder(
            schema = setOf(MediaEntity::class, GenreEntity::class)
        )
            .encryptionKey(encryptionKey)
            .schemaVersion(1) // Keep the schema version at 1
            .deleteRealmIfMigrationNeeded() // Delete the Realm file if a migration is needed
            .build()

        realm = Realm.open(config)
        Logger.i { "openDatabase - realm: $realm" }
    }

    // Add a media entity to the database
    suspend fun addMedia(mediaEntity: MediaEntity) {
        realm.write {
            copyToRealm(mediaEntity)
        }
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


//    // Get all movies
//    suspend fun getAllMovies(): List<MediaEntity> {
//        return realm.query<MediaEntity>("type == $0", MediaType.MOVIE)
//            .find()
//            .toList()
//    }

    fun getMoviesFlow(): Flow<List<MediaEntity>> {
        return realm.query<MediaEntity>("type == $0", MediaType.MOVIE.mediaType).asFlow() // Observe changes
            .map { it.list } // Extract the list from the query result
    }

    fun getTvShowsFlow(): Flow<List<MediaEntity>> {
        return realm.query<MediaEntity>("type == $0", MediaType.TV_SHOW.mediaType).asFlow() // Observe changes
            .map { it.list } // Extract the list from the query result
    }

    suspend fun getAllMovies(): List<MediaEntity> {
        return realm.query<MediaEntity>("type == $0", MediaType.MOVIE.mediaType)
            .find()
            .toList()
    }

    // Get all TV shows
    suspend fun getAllTvShows(): List<MediaEntity> {
        return realm.query<MediaEntity>("type == $0", MediaType.TV_SHOW.mediaType)
            .find()
            .toList()
    }

    // Get media by ID
    suspend fun getMediaById(mediaId: Int): MediaEntity? {
        return realm.query<MediaEntity>("id == $0", mediaId)
            .first()
            .find()
    }

    // Close the database
    fun close() {
        realm.close()
    }
}
