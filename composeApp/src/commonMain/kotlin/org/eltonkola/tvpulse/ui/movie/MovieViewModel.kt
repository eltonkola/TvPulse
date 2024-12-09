package org.eltonkola.tvpulse.ui.movie



import androidx.lifecycle.ViewModel
import org.eltonkola.tvpulse.DiGraph
import org.eltonkola.tvpulse.data.local.MediaRepository

class MovieViewModel(
    private val id: Int,
    private val mediaRepository: MediaRepository = DiGraph.mediaRepository
) : ViewModel() {

    val movie = mediaRepository.getMovieById(id)

}
