package org.eltonkola.tvpulse.ui.main.profile

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

class ProfileViewModel(
     dbManager: DbManager = DiGraph.dbManager
) : ViewModel() {

    val tvShows = dbManager.getTvShowsFlow()
    val movies = dbManager.getMoviesFlow()
    val favoriteTvShows = dbManager.getFavoriteTvShowsFlow()
    val favoriteMovies = dbManager.getFavoriteMoviesFlow()

}
