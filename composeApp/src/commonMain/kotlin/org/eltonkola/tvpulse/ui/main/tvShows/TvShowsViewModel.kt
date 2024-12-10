package org.eltonkola.tvpulse.ui.main.tvShows

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.eltonkola.tvpulse.DiGraph
import org.eltonkola.tvpulse.data.db.DbManager
import org.eltonkola.tvpulse.data.db.model.MediaEntity

sealed class TvShowsUiState{
    data object Loading : TvShowsUiState()
    data class Ready(val tvShows: List<MediaEntity>) : TvShowsUiState()
    data class Error(val message: String) : TvShowsUiState()
}

class TvShowsViewModel(
     private val dbManager: DbManager = DiGraph.dbManager
) : ViewModel() {

    private val _uiState : MutableStateFlow<TvShowsUiState> = MutableStateFlow(TvShowsUiState.Loading)
    val uiState: StateFlow<TvShowsUiState> = _uiState.asStateFlow()

    init {
        loadTvShows()
    }

    fun loadTvShows() {
        viewModelScope.launch {
            _uiState.update { TvShowsUiState.Loading }

            try{
                dbManager.getTvShowsFlow()
                    .stateIn(viewModelScope)
                    .collect { tvShows ->
                        _uiState.update { TvShowsUiState.Ready(tvShows) }
                    }
            }catch (e: Exception){
                _uiState.update { TvShowsUiState.Error(e.message ?: "Error loading tv shows") }
            }
        }
    }

}
