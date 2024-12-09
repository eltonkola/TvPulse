package org.eltonkola.tvpulse.ui.settings

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.composables.icons.lucide.Activity
import com.composables.icons.lucide.ArrowLeft
import com.composables.icons.lucide.ArrowRight
import com.composables.icons.lucide.Lucide
import org.eltonkola.tvpulse.DiGraph
import org.eltonkola.tvpulse.data.local.AppSettings


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navController: NavController,
    appSettings: AppSettings = DiGraph.appSettings
) {

    val themeState by appSettings.settingsState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Settings") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Lucide.ArrowLeft, contentDescription = "Back")
                    }
                }
            )
        }
    ) {

        Column(Modifier.fillMaxWidth().padding(it), horizontalAlignment = Alignment.CenterHorizontally) {

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                Text(text = "System", style = MaterialTheme.typography.titleLarge)
                Switch(
                    checked = themeState.system,
                    onCheckedChange = {
                        appSettings.setSystemTheme(it)
                    }
                )
            }

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                Text(text = "Dark", style = MaterialTheme.typography.titleLarge)
                Switch(
                    checked = themeState.dark,
                    onCheckedChange = {
                        appSettings.setDarkTheme(it)
                    }
                )
            }
        }
    }

}
