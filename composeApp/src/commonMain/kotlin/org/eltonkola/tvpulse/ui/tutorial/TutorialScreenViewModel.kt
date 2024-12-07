package org.eltonkola.tvpulse.ui.tutorial


import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.eltonkola.tvpulse.DiGraph
import org.eltonkola.tvpulse.data.local.AppSettings

data class LandingUiState(
    val acceptedTerms: Boolean,
    val showTerms: Boolean = false
)

class TutorialScreenViewModel(
    private val appSettings: AppSettings = DiGraph.appSettings
) : ViewModel() {
    private val _uiState = MutableStateFlow(LandingUiState(appSettings.acceptedTerms()))
    val uiState: StateFlow<LandingUiState> = _uiState.asStateFlow()

    fun showTerms(){
        _uiState.update { it.copy(showTerms = true) }
    }

    fun accept(){
        appSettings.setAcceptTerms(true)
        _uiState.update { it.copy(showTerms = false, acceptedTerms = true) }
    }

    fun onNext(){
        appSettings.setFirstLaunch(false)
    }

}