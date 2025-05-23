package org.eltonkola.tvpulse.ui.splash



import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.eltonkola.tvpulse.DiGraph
import org.eltonkola.tvpulse.data.local.AppSettings
import org.eltonkola.tvpulse.data.local.MediaRepository

sealed class SplashOpState{
    data object Loading : SplashOpState()
    data object Ready : SplashOpState()
    data object FirstTime : SplashOpState()
}

data class SplashUiState(
    val state: SplashOpState = SplashOpState.Loading
)

class SplashViewModel(
    private val appSettings: AppSettings = DiGraph.appSettings,
    private val mediaRepository: MediaRepository = DiGraph.mediaRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(SplashUiState())
    val uiState: StateFlow<SplashUiState> = _uiState.asStateFlow()

    init{
        viewModelScope.launch {
                val isFirstLaunch = appSettings.isFirstLaunch()
                Logger.i{ "isFirstLaunch: $isFirstLaunch" }
                if(isFirstLaunch) {
                    _uiState.update { it.copy(state = SplashOpState.FirstTime) }
                }else{
                    try {
                        mediaRepository.syncTvShows()
                    }catch (e: Exception) {
                        e.printStackTrace()
                    }
                    _uiState.update { it.copy(state = SplashOpState.Ready) }
                }

        }
    }

}
