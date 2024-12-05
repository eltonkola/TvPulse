package org.eltonkola.tvpulse.data


import co.touchlab.kermit.Logger
import com.russhwolf.settings.Settings
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class SettingsState(
    val system: Boolean,
    val dark: Boolean,
    val firstLaunch: Boolean
)

class AppSettings(private val settings: Settings) {

    private val _settingsState = MutableStateFlow(
        SettingsState(
            isDarkTheme(),
            isSystemTheme(),
            isFirstLaunch()
        )
    )
    val settingsState: StateFlow<SettingsState> = _settingsState.asStateFlow()

    companion object {
        const val DARK_THEME = "dark_theme"
        const val SYSTEM_THEME = "system_theme"
        const val ACCEPTED_TERMS = "ACCEPTED_TERMS"
        const val FIRST_LAUNCH = "FIRST_LAUNCH"

    }

    fun acceptedTerms() : Boolean {
        val acceptedTerms =  settings.getBoolean(ACCEPTED_TERMS, false)
        Logger.i { "acceptedTerms: $acceptedTerms" }
        return acceptedTerms
    }

    fun setAcceptTerms(accepted: Boolean) {
        Logger.i { "setAcceptTerms: $accepted" }
        settings.putBoolean(ACCEPTED_TERMS, accepted)
    }

    fun isDarkTheme(): Boolean {
        return settings.getBoolean(DARK_THEME, false)
    }

    fun setDarkTheme(value: Boolean) {
        settings.putBoolean(DARK_THEME, value)
        _settingsState.update { it.copy(dark = value) }
    }

    fun isSystemTheme(): Boolean {
        return settings.getBoolean(SYSTEM_THEME, true)
    }

    fun setSystemTheme(value: Boolean) {
        settings.putBoolean(SYSTEM_THEME, value)
        _settingsState.update { it.copy(system = value) }
    }


    fun isFirstLaunch(): Boolean {
        return settings.getBoolean(FIRST_LAUNCH, true)
    }

    fun setFirstLaunch(value: Boolean) {
        settings.putBoolean(FIRST_LAUNCH, value)
        _settingsState.update { it.copy(firstLaunch = value) }
    }

}
