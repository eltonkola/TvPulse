package org.eltonkola.tvpulse.ui.main.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.composables.icons.lucide.Activity
import com.composables.icons.lucide.Lucide
import org.eltonkola.tvpulse.DiGraph
import org.eltonkola.tvpulse.data.local.AppSettings


@Composable
fun SettingsTab(
    navController: NavController,
    appSettings: AppSettings = DiGraph.appSettings
) {

    val themeState by appSettings.settingsState.collectAsState()

    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {

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
