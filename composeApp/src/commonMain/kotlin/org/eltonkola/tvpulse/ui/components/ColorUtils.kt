package org.eltonkola.tvpulse.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun Material3ColorsPreview() {
    val colors = MaterialTheme.colorScheme
    val colorList = listOf(
        "Primary" to colors.primary,
        "OnPrimary" to colors.onPrimary,
        "PrimaryContainer" to colors.primaryContainer,
        "OnPrimaryContainer" to colors.onPrimaryContainer,
        "Secondary" to colors.secondary,
        "OnSecondary" to colors.onSecondary,
        "SecondaryContainer" to colors.secondaryContainer,
        "OnSecondaryContainer" to colors.onSecondaryContainer,
        "Tertiary" to colors.tertiary,
        "OnTertiary" to colors.onTertiary,
        "TertiaryContainer" to colors.tertiaryContainer,
        "OnTertiaryContainer" to colors.onTertiaryContainer,
        "Error" to colors.error,
        "OnError" to colors.onError,
        "ErrorContainer" to colors.errorContainer,
        "OnErrorContainer" to colors.onErrorContainer,
        "Background" to colors.background,
        "OnBackground" to colors.onBackground,
        "Surface" to colors.surface,
        "OnSurface" to colors.onSurface,
        "SurfaceVariant" to colors.surfaceVariant,
        "OnSurfaceVariant" to colors.onSurfaceVariant,
        "Outline" to colors.outline,
        "InverseSurface" to colors.inverseSurface,
        "InverseOnSurface" to colors.inverseOnSurface,
        "InversePrimary" to colors.inversePrimary,
        "SurfaceTint" to colors.surfaceTint
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        colorList.forEach { (name, color) ->
            ColorItem(name = name, color = color)
        }
    }
}

@Composable
fun ColorItem(name: String, color: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .background(color = color, shape = RoundedCornerShape(8.dp))
                .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(8.dp))
        )

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = name,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f)
        )

        Text(
            text = "#${color.toHex().uppercase()}",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

fun Color.toHex(): String {
    val red = (this.red * 255).toInt()
    val green = (this.green * 255).toInt()
    val blue = (this.blue * 255).toInt()
    val alpha = (this.alpha * 255).toInt()

    return "#${alpha.toString(16).padStart(2, '0')}${red.toString(16).padStart(2, '0')}${green.toString(16).padStart(2, '0')}${blue.toString(16).padStart(2, '0')}"
}