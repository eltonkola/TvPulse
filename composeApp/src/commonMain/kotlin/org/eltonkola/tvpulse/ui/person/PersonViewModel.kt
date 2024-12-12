package org.eltonkola.tvpulse.ui.person

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.eltonkola.tvpulse.DiGraph
import org.eltonkola.tvpulse.data.local.MediaRepository
import org.eltonkola.tvpulse.data.remote.model.CastCredit
import org.eltonkola.tvpulse.data.remote.model.Person

sealed class PersonUiState{
    data object Loading : PersonUiState()
    data class Ready(
        val person: Person,
        val movies: List<CastCredit> = emptyList(),
        val tvShows: List<CastCredit> = emptyList(),
        ) : PersonUiState()
    data class Error(val message: String) : PersonUiState()
}

class PersonViewModel(
    private val id: Int,
    private val mediaRepository: MediaRepository = DiGraph.mediaRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<PersonUiState> = MutableStateFlow(PersonUiState.Loading)
    val uiState: StateFlow<PersonUiState> = _uiState.asStateFlow()

    init {
        loadProfile()
    }

    fun loadProfile() {
        viewModelScope.launch {
            _uiState.update { PersonUiState.Loading }
            try{
                val person = mediaRepository.getPerson(id)
                val movies = mediaRepository.getActorMovies(id)
                val tvShows = mediaRepository.getActorTvShows(id)
                _uiState.update { PersonUiState.Ready(person, movies.cast, tvShows.cast) }
            }catch (e: Exception){
                _uiState.update { PersonUiState.Error(e.message ?: "Error loading profile") }
            }
        }
    }

}
