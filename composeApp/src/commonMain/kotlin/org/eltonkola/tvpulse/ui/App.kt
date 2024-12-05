package org.eltonkola.tvpulse.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil3.ImageLoader
import coil3.annotation.ExperimentalCoilApi
import coil3.compose.setSingletonImageLoaderFactory
import coil3.request.CachePolicy
import coil3.request.crossfade
import org.eltonkola.tvpulse.DiGraph
import org.eltonkola.tvpulse.data.AppSettings
import org.eltonkola.tvpulse.data.AppsScreen
import org.eltonkola.tvpulse.ui.main.MainScreen
import org.eltonkola.tvpulse.ui.splash.SplashScreen
import org.eltonkola.tvpulse.ui.theme.TvPulseTheme
import org.eltonkola.tvpulse.ui.tutorial.TutorialScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalCoilApi::class)
@Composable
@Preview
fun App(
    navController: NavHostController = rememberNavController(),
    appSettings: AppSettings = DiGraph.appSettings
) {
    val themeState by appSettings.settingsState.collectAsState()

    val darkTheme = if (themeState.system) {
        isSystemInDarkTheme()
    } else {
        themeState.dark
    }

    TvPulseTheme(darkTheme = darkTheme) {

        setSingletonImageLoaderFactory { context ->
            ImageLoader.Builder(context)
                .crossfade(true)
                .diskCachePolicy(CachePolicy.ENABLED)
                .memoryCachePolicy(CachePolicy.ENABLED)
                .build()
        }

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {

            NavHost(
                navController = navController,
                startDestination = AppsScreen.Splash.name,
                modifier = Modifier.fillMaxSize()
            ) {
                composable(route = AppsScreen.Splash.name) {
                    SplashScreen(navController = navController)
                }
                composable(route = AppsScreen.Tutorial.name) {
                    TutorialScreen(navController = navController)
                }
                composable(route = AppsScreen.Main.name) {
                    MainScreen(navController = navController)
                }
                composable(route = "${AppsScreen.TvShow.name}/{id}") { backStackEntry ->
//                    SingleExercise(
//                        navController = navController,
//                        backStackEntry.arguments!!.getString("id")!!
//                    )
                }
                composable(route = "${AppsScreen.Movie.name}/{id}") { backStackEntry ->
//                    SingleExercise(
//                        navController = navController,
//                        backStackEntry.arguments!!.getString("id")!!
//                    )
                }

//                dialog(route = AppsScreen.TrackMoodDialog.name) {
//                    TrackMoodDialog(navController = navController)
//                }
            }


        }
    }
}