package org.eltonkola.tvpulse.ui.movie

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import org.eltonkola.tvpulse.ui.components.TabScreen

@Composable
fun MovieScreen(
    id: Int,
    navController: NavHostController,
    viewModel: MovieViewModel = viewModel { MovieViewModel(id) },
) {

    val uiState by viewModel.movie.collectAsState(null)

    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {

        item{
            if(uiState !=null){
                MovieHeader(uiState!!, navController)
            }

        }
        item{
            TabScreen(
                tabs = listOf(
                    Pair("ABOUT") {

                    },
                    Pair("MORE") {

                    }
                )
            )
        }

    }

}
