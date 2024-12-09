package org.eltonkola.tvpulse.ui.main.profile


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Settings
import com.composables.icons.lucide.Share
import org.eltonkola.tvpulse.data.local.model.AppsScreen
import org.eltonkola.tvpulse.expect.shareLink
import org.jetbrains.compose.resources.painterResource
import tvpulse.composeapp.generated.resources.Res
import tvpulse.composeapp.generated.resources.avatar
import tvpulse.composeapp.generated.resources.landing_background
import tvpulse.composeapp.generated.resources.logo

@Composable
fun ProfileHeader(
    navController: NavController,
) {
    Box(
        modifier = Modifier.fillMaxWidth().aspectRatio(2.2f),
        contentAlignment = Alignment.BottomStart
    ) {
        Image(
            painter = painterResource(Res.drawable.landing_background),
            contentDescription = "Background",
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Black.copy(alpha = 0.6f),
                            Color.Black.copy(alpha = 0.4f),
                            Color.Black.copy(alpha = 0.6f),
                            Color.Black.copy(alpha = 1f)
                        )
                    )
                )
        )

        Row(
            modifier = Modifier.fillMaxWidth().align(Alignment.TopStart).padding(8.dp),
        ) {
            IconButton(onClick = {
                shareLink("https://play.google.com/store/apps/details?id=org.eltonkola.tvpulse")
            }) {
                Icon(
                    imageVector = Lucide.Share,
                    contentDescription = "Share",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = {
                navController.navigate(AppsScreen.Settings.name)
            }) {
                Icon(imageVector = Lucide.Settings, contentDescription = "Settings")
            }
        }


        Row(
            modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp, start = 18.dp),
        ) {
            Box(
                modifier = Modifier.size(80.dp)
            ) {
                // User avatar with white border
                Image(
                    painter = painterResource(Res.drawable.avatar),
                    contentDescription = "avatar",
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                        .border(
                            width = 1.dp, // White border of 1 pixel
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
                            shape = CircleShape
                        ),
                    contentScale = ContentScale.Crop
                )

                // Overlay: App icon in the lower-right corner
                Image(
                    painter = painterResource(Res.drawable.logo),
                    contentDescription = "app icon",
                    modifier = Modifier
                        .align(Alignment.BottomEnd) // Position in the lower-right corner
                        .size(32.dp) // Adjust size as needed
                        .padding(4.dp) // Optional padding around the app icon
                        .clip(CircleShape)
                        .background(Color.White) // Optional background for the icon
                )
            }

            Spacer(modifier = Modifier.size(16.dp))

            Column {
                Text(
                    text = "Anonymous User",
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                OutlinedButton(
                    onClick = {
                        //TODO - edit name, has no functionality, but looks cool
                    },

                    ) {
                    Text("Edit", color = MaterialTheme.colorScheme.onBackground)
                }
            }
        }


    }
}

