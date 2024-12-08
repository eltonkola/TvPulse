package org.eltonkola.tvpulse.ui.main.explore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.eltonkola.tvpulse.DiGraph
import org.eltonkola.tvpulse.data.remote.model.TrendingTvShowDetails
import org.eltonkola.tvpulse.data.remote.repository.TrendingRepository

class TrendingTvShowsViewModel(
    private val trendingRepository: TrendingRepository = DiGraph.trendingRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow<PagingData<TrendingTvShowDetails>>(PagingData.empty())
    val uiState: StateFlow<PagingData<TrendingTvShowDetails>> = _uiState.asStateFlow()

    init {
        loadTrendingTvShows()
    }

    private fun loadTrendingTvShows() {
        viewModelScope.launch {
            trendingRepository.getTrendingTvShows()
                .cachedIn(viewModelScope)
                .stateIn(viewModelScope)
                .collect { pagingData ->
                    _uiState.value = pagingData
                }
        }
    }

}
