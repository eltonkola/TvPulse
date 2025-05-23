package org.eltonkola.tvpulse.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.systemBarsPadding
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
import org.eltonkola.tvpulse.data.local.AppSettings
import org.eltonkola.tvpulse.data.local.model.AppsScreen
import org.eltonkola.tvpulse.ui.main.MainScreen
import org.eltonkola.tvpulse.ui.movie.MovieScreen
import org.eltonkola.tvpulse.ui.person.PersonScreen
import org.eltonkola.tvpulse.ui.settings.SettingsScreen
import org.eltonkola.tvpulse.ui.splash.SplashScreen
import org.eltonkola.tvpulse.ui.theme.TvPulseTheme
import org.eltonkola.tvpulse.ui.tutorial.TutorialScreen
import org.eltonkola.tvpulse.ui.tvshow.TvShowScreen
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
                modifier = Modifier.fillMaxSize().systemBarsPadding() //TODO do we want it here, or on every screen
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
                composable(route = AppsScreen.Settings.name) {
                    SettingsScreen(navController = navController)
                }
                composable(route = "${AppsScreen.TvShow.name}/{id}") { backStackEntry ->
                    TvShowScreen(
                        backStackEntry.arguments!!.getString("id")!!.toInt(),
                        navController = navController,
                    )
                }
                composable(route = "${AppsScreen.Movie.name}/{id}") { backStackEntry ->
                    MovieScreen(
                        backStackEntry.arguments!!.getString("id")!!.toInt(),
                        navController = navController,
                    )
                }
                composable(route = "${AppsScreen.Person.name}/{id}") { backStackEntry ->
                    PersonScreen(
                        backStackEntry.arguments!!.getString("id")!!.toInt(),
                        navController = navController,
                    )
                }



//                dialog(route = AppsScreen.TrackMoodDialog.name) {
//                    TrackMoodDialog(navController = navController)
//                }
            }


        }
    }
}