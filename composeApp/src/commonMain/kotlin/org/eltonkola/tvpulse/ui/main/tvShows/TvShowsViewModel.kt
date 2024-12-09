package org.eltonkola.tvpulse.ui.main.tvShows

import androidx.lifecycle.ViewModel
import org.eltonkola.tvpulse.DiGraph
import org.eltonkola.tvpulse.data.db.DbManager

class TvShowsViewModel(
     dbManager: DbManager = DiGraph.dbManager
) : ViewModel() {

    val tvShows = dbManager.getTvShowsFlow()

}
