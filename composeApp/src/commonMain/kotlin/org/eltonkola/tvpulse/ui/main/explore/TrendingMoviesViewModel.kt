package org.eltonkola.tvpulse.ui.main.explore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.eltonkola.tvpulse.DiGraph
import org.eltonkola.tvpulse.DiGraph.dbManager
import org.eltonkola.tvpulse.data.local.MediaRepository
import org.eltonkola.tvpulse.data.remote.model.TrendingMovieDetails
import org.eltonkola.tvpulse.data.remote.service.TmdbApiClient

sealed class TrendingMoviesSate{
    data object Loading : TrendingMoviesSate()
    data class Ready(val movies : List<TrendingMovieDetails>) : TrendingMoviesSate()
    data class Error(val error: String) : TrendingMoviesSate()
}

sealed class AddMovieState{
    data object Idle : AddMovieState()
    data object Adding : AddMovieState()
    data object Done : AddMovieState()
    data class Error(val error: String) : AddMovieState()
}

data class TrendingMoviesUiState(
    val dataState: TrendingMoviesSate = TrendingMoviesSate.Loading,
    val addMovieState: AddMovieState = AddMovieState.Idle
)

class TrendingMoviesViewModel(
    private val mediaRepository: MediaRepository = DiGraph.mediaRepository,
    private val tmdbApiClient: TmdbApiClient = DiGraph.tmdbApiClient
) : ViewModel() {

    private val _uiState = MutableStateFlow(TrendingMoviesUiState())
    val uiState: StateFlow<TrendingMoviesUiState> = _uiState.asStateFlow()

    init {
        loadTrendingMovies()
        loadSavedMovies()
    }

    private fun loadSavedMovies() {
        viewModelScope.launch {
            dbManager.getMoviesFlow()
                        .stateIn(viewModelScope)
                        .collect { savedMovies ->
                            _uiState.update {
                                if(it.dataState is TrendingMoviesSate.Ready){
                                    it.copy(
                                        dataState = TrendingMoviesSate.Ready(it.dataState.movies.map {  detail ->
                                            detail.copy(saved = savedMovies.any { movie -> movie.id == detail.id })
                                        })
                                    )
                                }else{
                                    it
                                }

                            }
            }

        }
    }


    fun loadTrendingMovies() {
        viewModelScope.launch {
            _uiState.update { it.copy(dataState = TrendingMoviesSate.Loading) }
            try{
                val savedMovies = dbManager.getAllMovies()
                val trendingMovies = tmdbApiClient.getTrendingMovies()
                _uiState.update {
                    it.copy(
                        dataState = TrendingMoviesSate.Ready(trendingMovies.results.map {  detail ->
                            detail.copy(saved = savedMovies.any { movie -> movie.id == detail.id })
                        })
                    )
                }
            }catch (e: Exception){
                _uiState.update { it.copy(dataState = TrendingMoviesSate.Error(e.message ?: "Error loading trending movies")) }
            }
        }
    }

    fun addMovieToWatchlist(id: Int){
        viewModelScope.launch {
            _uiState.update { it.copy(addMovieState = AddMovieState.Adding) }
            try{
                mediaRepository.addMovieToWatchlist(id)
                _uiState.update {
                    it.copy(
                        addMovieState = AddMovieState.Done
                    )
                }
            }catch (e: Exception){
                _uiState.update { it.copy(addMovieState = AddMovieState.Error(e.message ?: "Error adding movie")) }
                e.printStackTrace()
            }
        }
    }

    fun movieAddedShown(){
        _uiState.update { it.copy(addMovieState = AddMovieState.Idle) }
    }

}
