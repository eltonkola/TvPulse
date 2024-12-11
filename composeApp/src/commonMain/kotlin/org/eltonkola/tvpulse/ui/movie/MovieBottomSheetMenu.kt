package org.eltonkola.tvpulse.ui.movie

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import com.composables.icons.lucide.Heart
import com.composables.icons.lucide.HeartOff
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Minus
import com.composables.icons.lucide.Plus
import com.composables.icons.lucide.Share
import org.eltonkola.tvpulse.ui.components.BottomSheetMenu
import org.eltonkola.tvpulse.ui.components.BottomSheetMenuOption


@Composable
fun MovieBottomSheetMenu(
    isVisible: Boolean,
    isFavorite: Boolean,
    isSaved: Boolean,
    onDismiss: () -> Unit,
    onShareClick: () -> Unit,
//    onAddToListClick: () -> Unit,
    onRemoveMovieClick: () -> Unit,
    onAddToFavoritesClick: () -> Unit,
    onRemoveFromFavoritesClick: () -> Unit
) {
    BottomSheetMenu(
        isVisible = isVisible,
        title = "Options",
        onDismiss = onDismiss,
        options = listOf(
            BottomSheetMenuOption(
                title = "Share",
                icon = Lucide.Share,
                onClick = onShareClick
            ),
            //TODO - future feature
//            BottomSheetMenuOption(
//                title = "Add to List",
//                icon = Lucide.Plus,
//                onClick = onAddToListClick
//            ),
            if(isSaved){
                BottomSheetMenuOption(
                    title = "Delete movie",
                    icon = Lucide.Minus,
                    onClick = onRemoveMovieClick
                )
                if (isFavorite) {
                    BottomSheetMenuOption(
                        title = "Remove from favorites",
                        icon = Lucide.HeartOff,
                        onClick = onRemoveFromFavoritesClick
                    )
                } else {
                    BottomSheetMenuOption(
                        title = "Add to favorites",
                        icon = Lucide.Heart,
                        onClick = onAddToFavoritesClick
                    )
                }
            }else{
                BottomSheetMenuOption(
                    title = "Add movie",
                    icon = Lucide.Plus,
                    onClick = onRemoveMovieClick
                )
            },

        )
    )
}

