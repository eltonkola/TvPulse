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
import org.eltonkola.tvpulse.DiGraph.mediaRepository
import org.eltonkola.tvpulse.data.remote.model.TrendingTvShowDetails
import org.eltonkola.tvpulse.data.remote.repository.TrendingRepository

sealed class AddTvShowState{
    data object Idle : AddTvShowState()
    data object Adding : AddTvShowState()
    data object Done : AddTvShowState()
    data class Error(val error: String) : AddTvShowState()
}

class TrendingTvShowsViewModel(
    private val trendingRepository: TrendingRepository = DiGraph.trendingRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow<PagingData<TrendingTvShowDetails>>(PagingData.empty())
    val uiState: StateFlow<PagingData<TrendingTvShowDetails>> = _uiState.asStateFlow()

    private val _addState : MutableStateFlow<AddTvShowState> = MutableStateFlow(AddTvShowState.Idle)
    val addState: StateFlow<AddTvShowState> = _addState.asStateFlow()

    init {
        loadTrendingTvShows()
    }

    private fun loadTrendingTvShows() {
        viewModelScope.launch {

            val cachedTrendingFlow = trendingRepository.getTrendingTvShows()
                .cachedIn(viewModelScope)

            combine(
                cachedTrendingFlow,
                    dbManager.getTvShowsFlow()
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

    fun addTvShowToWatchlist(id: Int){
        viewModelScope.launch {
            _addState.update { AddTvShowState.Adding }
            try{
                mediaRepository.addTvShowToWatchlist(id)
                _addState.update { AddTvShowState.Done }
            }catch (e: Exception){
                _addState.update {  AddTvShowState.Error(e.message ?: "Error adding movie") }
                e.printStackTrace()
            }
        }
    }

    fun tvShowAddedShown(){
        _addState.update { AddTvShowState.Idle }
    }

}
