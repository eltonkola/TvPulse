package org.eltonkola.tvpulse.ui


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.eltonkola.tvpulse.model.TvShow
import org.eltonkola.tvpulse.service.TvDbApiService

@Composable
fun TrendingShowsScreen() {
    val apiService = remember { TvDbApiService() }
    var tvShows by remember { mutableStateOf<List<TvShow>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }

    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        try {
            val response = apiService.getTrendingTvShows()
            tvShows = response.results
            isLoading = false
        } catch (e: Exception) {
            error = e.message
            isLoading = false
        }
    }

    when {
        isLoading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        error != null -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Error: ${error}")
            }
        }
        else -> {
            LazyColumn {
                items(tvShows) { show ->
                    TvShowItem(show)
                }
            }
        }
    }
}

@Composable
fun TvShowItem(show: TvShow) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(text = show.name, style = MaterialTheme.typography.h6)
        Spacer(modifier = Modifier.height(8.dp))
        show.overview?.let {
            Text(text = it, style = MaterialTheme.typography.body2)
        }
        show.voteAverage?.let {
            Text(
                text = "Rating: ${String.format("%.1f", it)}",
                style = MaterialTheme.typography.caption
            )
        }
    }
}
