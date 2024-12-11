package org.eltonkola.tvpulse.data.local

import io.realm.kotlin.ext.query
import io.realm.kotlin.ext.toRealmList
import kotlinx.coroutines.flow.Flow
import org.eltonkola.tvpulse.data.db.DbManager
import org.eltonkola.tvpulse.data.db.model.GenreEntity
import org.eltonkola.tvpulse.data.db.model.MediaEntity
import org.eltonkola.tvpulse.data.db.model.MediaType
import org.eltonkola.tvpulse.data.db.model.WatchStatus
import org.eltonkola.tvpulse.data.remote.model.MovieCreditsResponse
import org.eltonkola.tvpulse.data.remote.model.MovieDetails
import org.eltonkola.tvpulse.data.remote.model.MovieVideosResponse
import org.eltonkola.tvpulse.data.remote.model.TmdbListResponse
import org.eltonkola.tvpulse.data.remote.model.TrendingMovieDetails
import org.eltonkola.tvpulse.data.remote.model.TvShowDetails
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

    suspend fun removeMovieFromWatchlist(id: Int){
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
                dbManager.getOrCreateGenre(this, genre.id, genre.name) // Pass `this` MutableRealm
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
            })
        }


    }

    fun getMovieById(id: Int): Flow<MediaEntity?> {
        return dbManager.getMovieById(id)
    }

    fun getTvShowById(id: Int): Flow<MediaEntity?> {
        return dbManager.getTvShowById(id)
    }

    suspend fun getFullMovieById(id: Int): MovieDetails {
        return tmdbApiClient.getMovieDetails(id)
    }

    suspend fun getMovieTrailers(id: Int): MovieVideosResponse {
        return tmdbApiClient.getMovieTrailers(id)
    }

    suspend fun getMovieCredits(id: Int): MovieCreditsResponse {
        return tmdbApiClient.getMovieCredits(id)
    }

    suspend fun getSimilarMovies(id: Int): TmdbListResponse<TrendingMovieDetails> {
        return tmdbApiClient.getSimilarMovies(id)
    }
    suspend fun getFullTvShowById(id: Int): TvShowDetails {
        return tmdbApiClient.getTvShowDetails(id)
    }

    suspend fun setMovieWatched(status: WatchStatus, id: Int) : Boolean {
        return dbManager.setMovieWatched(status, id)
    }
    suspend fun setMovieFavorite(favorite: Boolean, id: Int) : Boolean {
        return dbManager.setMovieFavorites(favorite, id)
    }
    suspend fun setMovieRate(rate: Int, id: Int) : Boolean {
        return dbManager.setMovieRate(rate, id)
    }

    suspend fun emotionMovie(emotion: Int, id: Int) : Boolean {
        return dbManager.emotionMovie(emotion, id)
    }

    suspend fun updateComment(comment: String, id: Int) : Boolean {
        return dbManager.updateComment(comment, id)
    }


}
