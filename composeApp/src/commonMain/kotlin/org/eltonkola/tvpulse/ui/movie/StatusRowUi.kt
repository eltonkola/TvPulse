package org.eltonkola.tvpulse.ui.movie

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.*
import org.eltonkola.tvpulse.data.db.model.MediaEntity
import org.eltonkola.tvpulse.data.db.model.WatchStatus
import org.eltonkola.tvpulse.data.remote.model.MovieDetails
import org.eltonkola.tvpulse.data.remote.model.TvShowDetails
import org.eltonkola.tvpulse.ui.main.explore.tvshows.formatDateToHumanReadable

data class StatusMovieData(
    val releaseDate: String,
    val watched: Boolean
)

fun getStatusDataForMovie(movie: MediaEntity?, fullMovie: MovieDetails?): StatusMovieData {
    return if(movie !=null){
        StatusMovieData(
            releaseDate = movie.releaseDate?.formatDateToHumanReadable() ?: "",
            watched = movie.mediaStatus == WatchStatus.COMPLETED,
        )
    } else if(fullMovie !=null){
        StatusMovieData(
            releaseDate = fullMovie.release_date.formatDateToHumanReadable(),
            watched = false,
        )
    } else{
        StatusMovieData(
            releaseDate = "",
            watched = false,
        )
    }
}

fun getStatusDataForTvShow(movie: MediaEntity?, fullMovie: TvShowDetails?): StatusMovieData {
    return if(movie !=null){
        StatusMovieData(
            releaseDate = movie.releaseDate?.formatDateToHumanReadable() ?: "",
            watched = movie.mediaStatus == WatchStatus.COMPLETED,
        )
    } else if(fullMovie !=null){
        StatusMovieData(
            releaseDate = fullMovie.last_air_date?.formatDateToHumanReadable() ?: "",
            watched = false,
        )
    } else{
        StatusMovieData(
            releaseDate = "",
            watched = false,
        )
    }
}

@Composable
fun StatusRowUi(
    data: StatusMovieData,
    onClick: () -> Unit
) {
    Row (
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        Icon(
            imageVector = Lucide.Calendar,
            contentDescription = null,
            modifier = Modifier.size(16.dp)
        )
        Text(text = data.releaseDate, modifier = Modifier.padding(start = 8.dp))
        Spacer(modifier = Modifier.size(8.dp))
        if (data.watched) {
            Icon(
                imageVector = Lucide.Eye,
                contentDescription = null,
                modifier = Modifier.size(16.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Text(text = "Watched", modifier = Modifier.padding(start = 8.dp))
        }else{
            Icon(
                imageVector = Lucide.Eye,
                contentDescription = null,
                modifier = Modifier.size(16.dp),
                tint = MaterialTheme.colorScheme.onBackground
            )
            Text(text = "Not Watched", modifier = Modifier.padding(start = 8.dp))
        }

        Spacer(modifier = Modifier.weight(1f))

        IconButton(onClick = onClick){
            Icon(
                imageVector = Lucide.CircleCheck,
                contentDescription = null,
                tint = if(data.watched) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground,
            )
        }

    }
}




