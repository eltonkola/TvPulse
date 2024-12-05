package org.eltonkola.tvpulse.ui


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import org.eltonkola.tvpulse.data.model.TvShow
import org.eltonkola.tvpulse.data.model.TvShowResponse
import org.eltonkola.tvpulse.data.service.TmdbApiClient


@Composable
fun TrendingShowsScreen(apiClient: TmdbApiClient = TmdbApiClient()) {
    var tvShow by remember { mutableStateOf<TvShowResponse?>(null) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        try {
            tvShow = apiClient.getTrendingTvShows()
        } catch (e: Exception) {
            errorMessage = e.message
        }
    }

    if (errorMessage != null) {
        Text("Error: $errorMessage")
    } else if (tvShow != null) {
        LazyColumn() {

            items(tvShow?.results!!){ tvShow ->
                TvShowItem(tvShow)
                Spacer(modifier = Modifier.height(1.dp).fillMaxWidth().background(Color.Gray))
            }

        }
    } else {
        CircularProgressIndicator()
    }
}

@Composable
fun TvShowItem(tvShow: TvShow) {
    Column(modifier = Modifier.fillMaxWidth()) {

        Text("Name: ${tvShow.name}")
        Text("Overview: ${tvShow.overview}")
        Text("First Air Date: ${tvShow.first_air_date}")
        tvShow.poster_path?.let { posterPath ->
            AsyncImage(
                model = "https://image.tmdb.org/t/p/w500$posterPath",
                contentDescription = "Poster of ${tvShow.name}"
            )
        }
    }
}