package org.eltonkola.tvpulse.ui.tvshow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.realm.kotlin.types.annotations.FullText
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
import org.eltonkola.tvpulse.data.remote.model.*

sealed class TvShowUiState{
    data object Loading : TvShowUiState()
    data class Ready(
        val savedTvShow: MediaEntity?=null,
        val loading: Boolean = true,
        val error: Boolean = false,
        val fullDetails : TvShowDetails? = null,
        val trailer: VideoResult? = null,
        val cast: List<CastMember>? = null,
        val similar: List<TrendingTvShowDetails>? = null,
        val userOperation: Boolean = false,
        val toastMsg: String? = null,
        val seasons: List<SeasonResponse>? = null
        ) : TvShowUiState()
    data class Error(val message: String) : TvShowUiState()
}

class TvShowViewModel(
    private val id: Int,
    private val mediaRepository: MediaRepository = DiGraph.mediaRepository
) : ViewModel() {

    private val _uiState : MutableStateFlow<TvShowUiState> = MutableStateFlow(TvShowUiState.Loading)
    val uiState: StateFlow<TvShowUiState> = _uiState.asStateFlow()

    init {
        loadTvShow()
    }

    fun loadTvShow() {
        viewModelScope.launch {

            _uiState.update { TvShowUiState.Loading }

            mediaRepository.getMediaById(id)
                .stateIn(viewModelScope)
                .collect { localTvShow ->

                    if(localTvShow == null){
                        _uiState.value = TvShowUiState.Ready(savedTvShow = null)
                    }

                        if(uiState.value is TvShowUiState.Ready && localTvShow!=null){
                            _uiState.update {
                                (uiState.value as TvShowUiState.Ready).copy(savedTvShow = localTvShow)
                            }
                        }else{
                            _uiState.update { TvShowUiState.Ready(
                                savedTvShow = localTvShow,
                                fullDetails = null,
                                loading = true
                            ) }

                            try{
                                val fullTvShow = mediaRepository.getFullTvShowById(id)
                                val trailers = mediaRepository.getTvShowTrailers(id)
                                val cast = mediaRepository.getTvShowsCredits(id).cast
                                val similar = mediaRepository.getSimilarTvShows(id).results
                                val seasons = (1..fullTvShow.number_of_seasons).map {
                                    mediaRepository.getSeason(id, it)
                                }

                                _uiState.update {
                                    TvShowUiState.Ready(
                                        savedTvShow = localTvShow,
                                        fullDetails = fullTvShow,
                                        trailer = trailers.results.firstOrNull(),
                                        cast = cast,
                                        similar = similar,
                                        loading =false,
                                        error = false,
                                        seasons = seasons
                                    )
                                }



                            }catch (e: Exception){
                                e.printStackTrace()
                                _uiState.update {
                                    TvShowUiState.Ready(
                                        savedTvShow = localTvShow,
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
            if(_uiState.value is TvShowUiState.Ready){
                val state = _uiState.value as TvShowUiState.Ready
                if(state.savedTvShow == null){
                    //add it
                    mediaRepository.addTvShowToWatchlist(id)

                }else{
                    //remove it
                    mediaRepository.removeMediaFromWatchlist(id)
                }
            }
        }
    }

    fun onMovieWatchedClick() {

        viewModelScope.launch {

            if(uiState.value is TvShowUiState.Ready){
                val state = uiState.value as TvShowUiState.Ready
                val movie = state.savedTvShow
                val newStatus = if(movie?.mediaStatus == WatchStatus.NOT_WATCHED){
                    WatchStatus.COMPLETED
                } else {
                    WatchStatus.NOT_WATCHED
                }

                _uiState.update { state.copy(userOperation = true) }

                val saved = mediaRepository.setMediaWatchedStatus(newStatus, id)

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
            if(uiState.value is TvShowUiState.Ready) {
                val state = uiState.value as TvShowUiState.Ready
                val movie = state.savedTvShow
                if(movie != null) {
                    mediaRepository.setMediaFavorites(true, id)
                }
            }
        }
    }
    fun removeFromFavorites() {
        viewModelScope.launch {
            if(uiState.value is TvShowUiState.Ready) {
                val state = uiState.value as TvShowUiState.Ready
                val movie = state.savedTvShow
                if(movie != null) {
                    mediaRepository.setMediaFavorites(false, id)
                }
            }
        }
    }

    fun emotionMovie(emotion: Int) {
        viewModelScope.launch {
            if(uiState.value is TvShowUiState.Ready) {
                mediaRepository.setEmotionMediaScore(emotion, id)
            }
        }
    }

    fun updateComment(comment: String) {
        viewModelScope.launch {
            if(uiState.value is TvShowUiState.Ready) {
                mediaRepository.updateComment(comment, id)
            }
        }
    }




    fun rateMovie(rate: Int) {
        viewModelScope.launch {
            if(uiState.value is TvShowUiState.Ready) {
                mediaRepository.setMediaRating(rate, id)
            }
        }
    }

}
