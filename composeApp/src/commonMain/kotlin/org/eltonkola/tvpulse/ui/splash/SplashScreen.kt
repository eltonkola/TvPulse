package org.eltonkola.tvpulse.ui.splash

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import org.eltonkola.tvpulse.data.local.model.AppsScreen
import org.eltonkola.tvpulse.ui.components.LoadingUi

@Composable
fun SplashScreen(
    navController: NavHostController,
    viewModel: SplashViewModel = viewModel { SplashViewModel() },
) {
    val uiState by viewModel.uiState.collectAsState()
    when (uiState.state) {
        is SplashOpState.Loading -> LoadingUi()
        is SplashOpState.Ready -> {
            navController.navigate(AppsScreen.Main.name){
                popUpTo(AppsScreen.Splash.name) { inclusive = true }
            }
        }
        is SplashOpState.FirstTime -> {
            navController.navigate(AppsScreen.Tutorial.name){
                popUpTo(AppsScreen.Splash.name) { inclusive = true }
            }
        }
    }
}
