package org.eltonkola.tvpulse.data.local

import co.touchlab.kermit.Logger
import io.realm.kotlin.ext.query
import io.realm.kotlin.ext.toRealmList
import io.realm.kotlin.log.RealmLog.remove
import io.realm.kotlin.types.RealmInstant
import kotlinx.coroutines.flow.Flow
import org.eltonkola.tvpulse.data.db.DbManager
import org.eltonkola.tvpulse.data.db.model.*
import org.eltonkola.tvpulse.data.local.model.SeasonData
import org.eltonkola.tvpulse.data.remote.model.*
import org.eltonkola.tvpulse.data.remote.service.TmdbApiClient

class MediaRepository (
    private val dbManager: DbManager,
    private val tmdbApiClient: TmdbApiClient
){

    suspend fun addMovieToWatchlist(id: Int){
        val movie = tmdbApiClient.getMovieDetails(id)

        dbManager.writeTransaction {
            val genres = movie.genres.map { genre ->
                dbManager.getOrCreateGenre(this, genre.id, genre.name) // Pass `this` MutableRealm
            }

            copyToRealm(MediaEntity().apply {
                this.id = movie.id
                this.title = movie.title
                this.type = MediaType.MOVIE.mediaType
                this.posterPath = movie.poster_path
                this.backdropPath = movie.backdrop_path
                this.overview = movie.overview
                this.releaseDate = movie.release_date
                this.genres = genres.toRealmList()
                this.originalLanguage = movie.original_language
                this.voteAverage = movie.vote_average
                this.voteCount = movie.vote_count
                this.popularity = movie.popularity
                this.runtime = movie.runtime
            })
        }

    }

    suspend fun removeMediaFromWatchlist(id: Int){
        dbManager.writeTransaction {
            val mediaToDelete: MediaEntity? = query<MediaEntity>("id == $0", id.toString()).first().find()
            mediaToDelete?.let {
                delete(it)
            }
        }
    }

    suspend fun addTvShowToWatchlist(id: Int){

        val tvShow = tmdbApiClient.getTvShowDetails(id)

        dbManager.writeTransaction {
            val genres = tvShow.genres.map { genre ->
                dbManager.getOrCreateGenre(this, genre.id, genre.name)
            }

            val seasons = tvShow.seasons.map { season ->
                dbManager.getOrCreateSeason(this, season)
            }

            copyToRealm(MediaEntity().apply{
                this.id = tvShow.id
                this.title = tvShow.name
                this.type = MediaType.TV_SHOW.mediaType
                this.posterPath = tvShow.poster_path
                this.backdropPath = tvShow.backdrop_path
                this.overview = tvShow.overview
                this.releaseDate = tvShow.first_air_date
                this.genres = genres.toRealmList()
                this.originalLanguage = tvShow.original_language
                this.voteAverage = tvShow.vote_average
                this.voteCount = tvShow.vote_count
                this.popularity = tvShow.popularity
                this.numberOfSeasons = tvShow.number_of_seasons
                this.numberOfEpisodes = tvShow.number_of_episodes
                this.seasons = seasons.toRealmList()
            })
        }

    }

    //TODO -- will sync the tvshows here, checking if there are new seasons for the tvshows we already have saved locally
    fun syncTvShows(){

    }

    fun getMediaById(id: Int): Flow<MediaEntity?> {
        return dbManager.getMediaFlowById(id)
    }

    suspend fun getFullMovieById(id: Int): MovieDetails {
        return tmdbApiClient.getMovieDetails(id)
    }

    suspend fun getMovieTrailers(id: Int): MovieVideosResponse {
        return tmdbApiClient.getMovieTrailers(id)
    }

    suspend fun getTvShowTrailers(id: Int): MovieVideosResponse {
        return tmdbApiClient.getTvShowTrailers(id)
    }

    suspend fun getMovieCredits(id: Int): MovieCreditsResponse {
        return tmdbApiClient.getMovieCredits(id)
    }

    suspend fun getTvShowsCredits(id: Int): MovieCreditsResponse {
        return tmdbApiClient.getTvShowsCredits(id)
    }

    suspend fun getSimilarMovies(id: Int): TmdbListResponse<TrendingMovieDetails> {
        return tmdbApiClient.getSimilarMovies(id)
    }

    suspend fun getSimilarTvShows(id: Int): TmdbListResponse<TrendingTvShowDetails> {
        return tmdbApiClient.getSimilarTvShows(id)
    }

    suspend fun getFullTvShowById(id: Int): TvShowDetails {
        return tmdbApiClient.getTvShowDetails(id)
    }

    suspend fun setMediaWatchedStatus(status: WatchStatus, id: Int) : Boolean {
        return dbManager.setMediaWatchedStatus(status, id)
    }
    suspend fun setMediaFavorites(favorite: Boolean, id: Int) : Boolean {
        return dbManager.setMediaFavorites(favorite, id)
    }
    suspend fun setMediaRating(rate: Int, id: Int) : Boolean {
        return dbManager.setMediaRating(rate, id)
    }

    suspend fun setEmotionMediaScore(emotion: Int, id: Int) : Boolean {
        return dbManager.setEmotionMediaScore(emotion, id)
    }

    suspend fun updateComment(comment: String, id: Int) : Boolean {
        return dbManager.updateComment(comment, id)
    }

    suspend fun getPerson(id: Int): Person {
        return tmdbApiClient.getPerson(id)
    }

    suspend fun getActorMovies(id: Int): PersonCreditsResponse {
        return tmdbApiClient.getActorMovies(id)
    }
    suspend fun getActorTvShows(id: Int): PersonCreditsResponse {
        return tmdbApiClient.getActorTvShows(id)
    }

    suspend fun getSeason(tvShowId: Int, season: Int): Season {
        return tmdbApiClient.getSeason(tvShowId, season)
    }

    suspend fun getTvShowWithSeasons(
        id: Int,
        seasonsNr: Int
    ): List<SeasonData> {
        val seasonsPerCall = 20
        val seasonResponses = mutableListOf<SeasonsResponse>()

        val calls = (seasonsNr / seasonsPerCall) + 1

        for (i in 1..calls) {
            val startSeason = (i - 1) * seasonsPerCall + 1
            val endSeason = minOf(i * seasonsPerCall, seasonsNr)

            val seasons = (startSeason..endSeason).joinToString(",") { "season/$it" }
            val response = tmdbApiClient.getTvShowWithSeasons(id, seasons)
            seasonResponses.add(response)
        }

        val all = seasonResponses.flatMap { it -> it.fullSeasons.map { SeasonData(it.key, it.value) } }.toSet().toList()
        Logger.i("all season data: $all")
        return all
    }

    suspend fun watchEpisodes(showId: Int, episodeIds: List<Int>) {
        dbManager.writeTransaction {
            val tvShow: MediaEntity? = query<MediaEntity>("id == $0", showId.toString()).first().find()
            if (tvShow != null) {
                // Add multiple episodes
                val episodes = episodeIds.map { episodeId ->
                    copyToRealm(EpisodeEntity().apply { id = episodeId })
                }
                tvShow.episodes.addAll(episodes)
                tvShow.updatedTimestamp = RealmInstant.now()
            } else {
                println("Show with id $showId not found!")
            }
        }
    }

    suspend fun unWatchEpisodes(showId: Int, episodeIds: List<Int>) {
        dbManager.writeTransaction {
            val tvShow: MediaEntity? = query<MediaEntity>("id == $0", showId.toString()).first().find()
            tvShow?.let {
                // Remove multiple episodes
                val episodesToRemove = it.episodes.filter { episode -> episode.id in episodeIds }
                if (episodesToRemove.isNotEmpty()) {
                    tvShow.episodes.removeAll(episodesToRemove)
                    episodesToRemove.forEach { episode ->
                        delete(episode)
                    }
                    tvShow.updatedTimestamp = RealmInstant.now()
                } else {
                    println("No matching episodes found in Show $showId!")
                }

            }
        }
    }

}
