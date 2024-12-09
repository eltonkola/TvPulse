package org.eltonkola.tvpulse.ui.main.explore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.eltonkola.tvpulse.DiGraph
import org.eltonkola.tvpulse.DiGraph.dbManager
import org.eltonkola.tvpulse.data.local.MediaRepository
import org.eltonkola.tvpulse.data.remote.model.TrendingMovieDetails
import org.eltonkola.tvpulse.data.remote.repository.TrendingRepository

sealed class AddMovieState{
    data object Idle : AddMovieState()
    data object Adding : AddMovieState()
    data object Done : AddMovieState()
    data class Error(val error: String) : AddMovieState()
}

class TrendingMoviesViewModel(
    private val mediaRepository: MediaRepository = DiGraph.mediaRepository,
    private val trendingRepository: TrendingRepository = DiGraph.trendingRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow<PagingData<TrendingMovieDetails>>(PagingData.empty())
    val uiState: StateFlow<PagingData<TrendingMovieDetails>> = _uiState.asStateFlow()



    private val _addState : MutableStateFlow<AddMovieState> = MutableStateFlow(AddMovieState.Idle)
    val addState: StateFlow<AddMovieState> = _addState.asStateFlow()

    init {
        loadTrendingMovies()
    }

    private fun loadTrendingMovies() {
        viewModelScope.launch {

            val cachedTrendingFlow = trendingRepository.getTrendingMovies()
                .cachedIn(viewModelScope)

            combine(
                cachedTrendingFlow,
                dbManager.getMoviesFlow()
            ) { trendingShows, savedShows ->
                trendingShows.map { detail ->
                    detail.copy(saved = savedShows.any { savedShow -> savedShow.id == detail.id })
                }
            }
                .cachedIn(viewModelScope)
                .stateIn(viewModelScope)
                .collect { pagingData ->
                    _uiState.value = pagingData
                }
        }
    }

    fun addMovieToWatchlist(id: Int){
        viewModelScope.launch {
            _addState.update { AddMovieState.Adding }
            try{
                mediaRepository.addMovieToWatchlist(id)
                _addState.update { AddMovieState.Done }
            }catch (e: Exception){
                _addState.update { AddMovieState.Error(e.message ?: "Error adding movie") }
                e.printStackTrace()
            }
        }
    }

    fun movieAddedShown(){
        _addState.update { AddMovieState.Idle }
    }

}
