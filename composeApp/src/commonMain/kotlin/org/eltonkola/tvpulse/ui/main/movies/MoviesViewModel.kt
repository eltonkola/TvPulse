package org.eltonkola.tvpulse.ui.main.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.eltonkola.tvpulse.DiGraph
import org.eltonkola.tvpulse.data.db.DbManager
import org.eltonkola.tvpulse.data.db.model.MediaEntity
import org.eltonkola.tvpulse.ui.main.tvShows.TvShowsUiState

sealed class MoviesUiState{
    data object Loading : MoviesUiState()
    data class Ready(val movies: List<MediaEntity>) : MoviesUiState()
    data class Error(val message: String) : MoviesUiState()
}

class MoviesViewModel(
     private val dbManager: DbManager = DiGraph.dbManager
) : ViewModel() {
    private val _uiState : MutableStateFlow<MoviesUiState> = MutableStateFlow(MoviesUiState.Loading)
    val uiState: StateFlow<MoviesUiState> = _uiState.asStateFlow()

    init {
        loadMovies()
    }

    fun loadMovies() {
        viewModelScope.launch {
            _uiState.update { MoviesUiState.Loading }

            try{
                dbManager.getMoviesFlow()
                    .stateIn(viewModelScope)
                    .collect { tvShows ->
                        _uiState.update { MoviesUiState.Ready(tvShows) }
                    }
            }catch (e: Exception){
                _uiState.update { MoviesUiState.Error(e.message ?: "Error loading movies") }
            }
        }
    }


}
