package org.eltonkola.tvpulse.data.db


import co.touchlab.kermit.Logger
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.query
import org.eltonkola.tvpulse.data.db.model.GenreEntity
import org.eltonkola.tvpulse.data.db.model.MediaEntity
import org.eltonkola.tvpulse.data.db.model.MediaType


class DbManager {

    private val realm: Realm

    private fun getEncryptionKey(): ByteArray {
        //TODO -  change this to something else
        return "EltonKola-KleartaKola-DionisKola-KlevisKola-ElidonaMirashi-Love!".encodeToByteArray()
    }

    init{


        val config = RealmConfiguration.Builder(
            schema = setOf(MediaEntity::class, GenreEntity::class)
        )
            .encryptionKey(getEncryptionKey())
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

    // Get all movies
    suspend fun getAllMovies(): List<MediaEntity> {
        return realm.query<MediaEntity>("type == $0", MediaType.MOVIE)
            .find()
            .toList()
    }

    // Get all TV shows
    suspend fun getAllTvShows(): List<MediaEntity> {
        return realm.query<MediaEntity>("type == $0", MediaType.TV_SHOW)
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
