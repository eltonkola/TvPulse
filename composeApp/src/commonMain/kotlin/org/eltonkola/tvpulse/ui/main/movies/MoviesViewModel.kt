package org.eltonkola.tvpulse.ui.main.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.eltonkola.tvpulse.DiGraph
import org.eltonkola.tvpulse.data.db.DbManager
import org.eltonkola.tvpulse.data.db.model.MediaEntity

data class MoviesUiState(
    val loading: Boolean = true,
    val movies : List<MediaEntity> = emptyList()
)

class MoviesViewModel(
     private val dbManager: DbManager = DiGraph.dbManager
) : ViewModel() {

//    private val _uiState = MutableStateFlow(MoviesUiState())
//    val uiState: StateFlow<MoviesUiState> = _uiState.asStateFlow()

    val movies = dbManager.getMoviesFlow()

//    init {
//        loadMovies()
//    }
//
//    fun loadMovies() {
//        viewModelScope.launch {
//            _uiState.update { it.copy(loading = true) }
//            try{
//                val movies = dbManager.getAllMovies()
//                _uiState.update { it.copy(loading = false, movies = movies) }
//            }catch (e: Exception){
//                _uiState.update { it.copy(loading = false, movies = emptyList()) }
//            }
//        }
//    }

}
