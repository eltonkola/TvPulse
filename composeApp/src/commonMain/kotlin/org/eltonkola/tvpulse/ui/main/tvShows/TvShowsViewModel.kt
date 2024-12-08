package org.eltonkola.tvpulse.ui.main.tvShows

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

data class TvShowsUiState(
    val loading: Boolean = true,
    val tvShows : List<MediaEntity> = emptyList()
)

class TvShowsViewModel(
     private val dbManager: DbManager = DiGraph.dbManager
) : ViewModel() {

    val tvShows = dbManager.getTvShowsFlow()

}
