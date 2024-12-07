package org.eltonkola.tvpulse.ui.tutorial


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.composables.icons.lucide.ArrowRight
import com.composables.icons.lucide.Lucide
import com.dokar.sonner.Toaster
import com.dokar.sonner.rememberToasterState
import org.eltonkola.tvpulse.data.local.model.AppsScreen
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import tvpulse.composeapp.generated.resources.Res
import tvpulse.composeapp.generated.resources.app_name
import tvpulse.composeapp.generated.resources.landing_background
import tvpulse.composeapp.generated.resources.slogan_1
import tvpulse.composeapp.generated.resources.terms_body
import tvpulse.composeapp.generated.resources.terms_failed
import tvpulse.composeapp.generated.resources.terms_title

@Composable
fun TutorialScreen(
    navController: NavHostController,
    viewModel: TutorialScreenViewModel = viewModel { TutorialScreenViewModel() },
) {

    val uiState by viewModel.uiState.collectAsState()

    if (uiState.showTerms || !uiState.acceptedTerms) {
        TOSScreen(
            onAccept = {
                viewModel.accept()
            },
            force =  !uiState.acceptedTerms
        )
    }


    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(Res.drawable.landing_background),
            contentDescription = "Background",
            contentScale = ContentScale.Crop
        )

        GradientWithSpacer()

        Column(
            modifier = Modifier.fillMaxSize().padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            Text(
                style = MaterialTheme.typography.displayLarge,
                text = stringResource(Res.string.app_name),
                color = Color.White,
                textAlign = TextAlign.Left,
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                style = MaterialTheme.typography.displaySmall,
                text = stringResource(Res.string.slogan_1),
//                fontSize = 28.sp,
                color = Color.White,
                textAlign = TextAlign.Left,
                modifier = Modifier.fillMaxWidth(),
//                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.weight(0.5f))

            Spacer(modifier = Modifier.size(16.dp))

            Button(
                onClick = {
                    viewModel.onNext()
                    navController.navigate(AppsScreen.Main.name){
                        popUpTo(AppsScreen.Splash.name) { inclusive = true }
                    }
                }
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Lucide.ArrowRight,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                    Text("Lets get started!")
                }
            }

            Spacer(modifier = Modifier.size(8.dp))

            TextButton({
                viewModel.showTerms()
            }){
                Text("Terms of service")
            }
        }
    }
}


@Composable
fun TOSScreen(onAccept:() -> Unit, force: Boolean) {

    val toaster = rememberToasterState()
    Toaster(state = toaster)

    val termsError = stringResource(Res.string.terms_failed)
    AlertDialog(
        onDismissRequest = {
            if(force){
                toaster.show(termsError)
            }else{
                onAccept()
            }
        },
        title = { Text(text = stringResource(Res.string.terms_title)) },
        text = {

            Box(modifier = Modifier
            ) {
                Text(stringResource(Res.string.terms_body))
            }

        },
        confirmButton = {
            Button(
                onClick = {
                    onAccept()
                }
            ) {
                if(force){
                    Text("Accept")
                }else{
                    Text("Cool")
                }

            }
        },
        dismissButton = {
            if(force) {
                Button(
                    onClick = {
                        toaster.show("Close the app, and uninstall it you don't agree to these terms!")
                    }
                ) {
                    Text("Don't accept")
                }
            }
        }
    )
}

@Composable
fun GradientWithSpacer() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        // First Gradient
        Box(
            modifier = Modifier
                .weight(3f) // 15% of the screen height
                .fillMaxWidth()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color.Black.copy(alpha = 0.8f), Color.Black.copy(alpha = 0.0f)),
                    )
                )
        )

        // Spacer (70% of the screen height)
        Spacer(
            modifier = Modifier
                .weight(4f)
                .fillMaxWidth()
        )

        // Second Gradient (Reverse)
        Box(
            modifier = Modifier
                .weight(3f) // 15% of the screen height
                .fillMaxWidth()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color.Black.copy(alpha = 0.0f), Color.Black.copy(alpha = 0.8f)),
                    )
                )
        )
    }
}
