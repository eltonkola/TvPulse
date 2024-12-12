package org.eltonkola.tvpulse.ui.person

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import org.eltonkola.tvpulse.ui.components.LoadingUi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import com.composables.icons.lucide.*
import org.eltonkola.tvpulse.data.Consts
import org.eltonkola.tvpulse.data.db.model.MediaEntity
import org.eltonkola.tvpulse.data.local.model.AppsScreen
import org.eltonkola.tvpulse.data.remote.model.CastCredit
import org.eltonkola.tvpulse.data.remote.model.Person
import org.eltonkola.tvpulse.expect.openLink
import org.eltonkola.tvpulse.ui.components.ErrorUi
import org.eltonkola.tvpulse.ui.components.MediaCard
import org.eltonkola.tvpulse.ui.main.explore.tvshows.formatDateToHumanReadable
import org.eltonkola.tvpulse.ui.main.profile.StatsUi

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonScreen(
    id: Int,
    navController: NavHostController,
    viewModel: PersonViewModel = viewModel { PersonViewModel(id) },
) {
    val uiState by viewModel.uiState.collectAsState()
    when (uiState) {
        is PersonUiState.Loading -> LoadingUi()
        is PersonUiState.Ready -> {
            val data = (uiState as PersonUiState.Ready)
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text(text = data.person.name) },
                        navigationIcon = {
                            IconButton(onClick = { navController.popBackStack() }) {
                                Icon(imageVector = Lucide.ArrowLeft, contentDescription = "Back")
                            }
                        },
                        actions = {
                            IconButton(onClick = {
                                openLink("https://www.imdb.com/name/${data.person.imdbId}/")
                            }) {
                                Icon(imageVector = Lucide.ExternalLink, contentDescription = "Profile")
                            }
                        }
                    )
                }

            ){
                PersonDetailScreen(
                    person = data.person,
                    movies = data.movies,
                    tvShows = data.tvShows,
                    navController = navController,
                    modifier = Modifier.padding(it)
                )
            }

        }
        is PersonUiState.Error -> {
            ErrorUi(
                message = "Error loading profile!",
                retry = true,
                retryLabel = "Retry",
                onRetry = { viewModel.loadProfile() },
                icon = Lucide.PersonStanding,
                iconColor = MaterialTheme.colorScheme.error,
            )
        }
    }
}

@Composable
fun PersonDetailScreen(
    person: Person,
    movies: List<CastCredit>,
    tvShows: List<CastCredit>,
    navController: NavHostController,
    modifier : Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Profile Image
            person.profilePath.takeIf { it.isNotBlank() }?.let { profilePath ->
                Image(
                    painter = rememberAsyncImagePainter(
                        "https://image.tmdb.org/t/p/w500$profilePath"
                    ),
                    contentDescription = "${person.name}'s profile picture",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(420.dp)
                        .clip(RoundedCornerShape(8.dp))
                )
            }

            // Name
            Text(
                text = person.name,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            // Basic Info Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    person.birthday?.let {
                        InfoRow("Birthday", it.formatDateToHumanReadable())
                    }
                    InfoRow("Place of Birth", person.placeOfBirth)
                    InfoRow("Gender", if (person.gender == 1) "Female" else "Male")
                    InfoRow("Popularity", person.popularity.toString())
                    InfoRow("Department", person.knownForDepartment)
                }
            }

            // Also Known As
            Text(
                text = "Also Known As:",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            person.alsoKnownAs.forEach { alias ->
                Text(
                    text = "• $alias",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            // Biography
            Text(
                text = "Biography",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            Text(
                text = person.biography,
                style = MaterialTheme.typography.bodyMedium
            )

            // Additional Details
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "IMDB ID: ${person.imdbId}",
                style = MaterialTheme.typography.labelSmall
            )


            if(movies.isNotEmpty()) {
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Movies",
                    modifier = Modifier.fillMaxWidth(),
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.size(8.dp))
                CardSection(
                    items = movies,
                    emptyTitle = "No Movies found",
                    onCardClick = { navController.navigate("${AppsScreen.Movie.name}/$it") }
                )
            }
            if(tvShows.isNotEmpty()) {
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Tv Shows",
                    modifier = Modifier.fillMaxWidth(),
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.size(8.dp))
                CardSection(
                    items = tvShows,
                    emptyTitle = "No Tv Shows found",
                    onCardClick = { navController.navigate("${AppsScreen.TvShow.name}/$it") }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

        }
    }
}

@Composable
private fun CardSection(
    items: List<CastCredit>,
    emptyTitle: String,
    onCardClick: (Int) -> Unit
) {
    if (items.isEmpty()) {
        StatsUi(
            modifier = Modifier.fillMaxWidth().height(80.dp),
            title = emptyTitle
        )
    }

    LazyRow(
        modifier = Modifier.fillMaxWidth().padding(start = 8.dp, end = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(items) {
            Box(
                modifier = Modifier.width(120.dp)
            ) {
                MediaCard(
                    posterUrl = it.poster_path ?: Consts.DEFAULT_THUMB_URL,
                    onClick = { onCardClick(it.id) }
                )
            }
        }
    }
}


@Composable
fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Text(
            text = "$label:",
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(0.3f)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(0.7f)
        )
    }
}