package org.eltonkola.tvpulse.ui.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.eltonkola.tvpulse.DiGraph
import org.eltonkola.tvpulse.data.db.model.MediaEntity
import org.eltonkola.tvpulse.data.db.model.WatchStatus
import org.eltonkola.tvpulse.data.local.MediaRepository
import org.eltonkola.tvpulse.data.remote.model.CastMember
import org.eltonkola.tvpulse.data.remote.model.MovieDetails
import org.eltonkola.tvpulse.data.remote.model.TrendingMovieDetails
import org.eltonkola.tvpulse.data.remote.model.VideoResult

sealed class MovieUiState{
    data object Loading : MovieUiState()
    data class Ready(
        val savedMovie: MediaEntity?=null,
        val loading: Boolean = true,
        val error: Boolean = false,
        val fullDetails : MovieDetails? = null,
        val trailer: VideoResult? = null,
        val cast: List<CastMember>? = null,
        val similar: List<TrendingMovieDetails>? = null,
        val userOperation: Boolean = false,
        val toastMsg: String? = null
        ) : MovieUiState()
    data class Error(val message: String) : MovieUiState()
}

class MovieViewModel(
    private val id: Int,
    private val mediaRepository: MediaRepository = DiGraph.mediaRepository
) : ViewModel() {

    private val _uiState : MutableStateFlow<MovieUiState> = MutableStateFlow(MovieUiState.Loading)
    val uiState: StateFlow<MovieUiState> = _uiState.asStateFlow()

    init {
        loadTrendingMovies()
    }

    fun loadTrendingMovies() {
        viewModelScope.launch {

            _uiState.update { MovieUiState.Loading }

            mediaRepository.getMovieById(id)
                .stateIn(viewModelScope)
                .collect { localMovie ->

                    if(localMovie == null){
                        _uiState.value = MovieUiState.Ready(savedMovie = null)
                    }

                        if(uiState.value is MovieUiState.Ready && localMovie!=null){
                            _uiState.update {
                                (uiState.value as MovieUiState.Ready).copy(savedMovie = localMovie)
                            }
                        }else{
                            _uiState.update { MovieUiState.Ready(
                                savedMovie = localMovie,
                                fullDetails = null,
                                loading = true
                            ) }

                            try{
                                val fullMovie = mediaRepository.getFullMovieById(id)
                                val trailers = mediaRepository.getMovieTrailers(id)
                                val cast = mediaRepository.getMovieCredits(id).cast
                                val similar = mediaRepository.getSimilarMovies(id).results

                                _uiState.update {
                                    MovieUiState.Ready(
                                        savedMovie = localMovie,
                                        fullDetails = fullMovie,
                                        trailer = trailers.results.firstOrNull(),
                                        cast = cast,
                                        similar = similar,
                                        loading =false,
                                        error = false
                                    )
                                }
                            }catch (e: Exception){
                                e.printStackTrace()
                                _uiState.update {
                                    MovieUiState.Ready(
                                        savedMovie = localMovie,
                                        fullDetails = null,
                                        loading =false,
                                        error = true
                                    )
                                }
                            }


                    }

                }
        }
    }

    fun addRemoveMovieToWatchlist(){
        viewModelScope.launch {
            if(_uiState.value is MovieUiState.Ready){
                val state = _uiState.value as MovieUiState.Ready
                if(state.savedMovie == null){
                    //add it
                    mediaRepository.addMovieToWatchlist(id)

                }else{
                    //remove it
                    mediaRepository.removeMovieFromWatchlist(id)
                }
            }
        }
    }

    fun onMovieWatchedClick() {

        viewModelScope.launch {

            if(uiState.value is MovieUiState.Ready){
                val state = uiState.value as MovieUiState.Ready
                val movie = state.savedMovie
                val newStatus = if(movie?.mediaStatus == WatchStatus.NOT_WATCHED){
                    WatchStatus.COMPLETED
                } else {
                    WatchStatus.NOT_WATCHED
                }

                _uiState.update { state.copy(userOperation = true) }

                val saved = mediaRepository.setMovieWatched(newStatus, id)

                _uiState.update {
                    state.copy(
                        userOperation = false,
                        toastMsg = if(!saved) "Error updating movie" else null)
                }

            }

        }

    }

    fun addToFavorites() {
        viewModelScope.launch {
            if(uiState.value is MovieUiState.Ready) {
                val state = uiState.value as MovieUiState.Ready
                val movie = state.savedMovie
                if(movie != null) {
                    mediaRepository.setMovieFavorite(true, id)
                }
            }
        }
    }
    fun removeFromFavorites() {
        viewModelScope.launch {
            if(uiState.value is MovieUiState.Ready) {
                val state = uiState.value as MovieUiState.Ready
                val movie = state.savedMovie
                if(movie != null) {
                    mediaRepository.setMovieFavorite(false, id)
                }
            }
        }
    }

    fun emotionMovie(emotion: Int) {
        viewModelScope.launch {
            if(uiState.value is MovieUiState.Ready) {
                mediaRepository.emotionMovie(emotion, id)
            }
        }
    }

    fun updateComment(comment: String) {
        viewModelScope.launch {
            if(uiState.value is MovieUiState.Ready) {
                mediaRepository.updateComment(comment, id)
            }
        }
    }




    fun rateMovie(rate: Int) {
        viewModelScope.launch {
            if(uiState.value is MovieUiState.Ready) {
                mediaRepository.setMovieRate(rate, id)
            }
        }
    }

}
