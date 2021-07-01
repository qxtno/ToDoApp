package com.example.todoapp.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.todoapp.utils.PreferencesManager
import com.example.todoapp.utils.Theme
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsFragmentViewModel @Inject constructor(
    private val preferencesManager: PreferencesManager
) : ViewModel() {
    val themeFlow = preferencesManager.themeFlow.asLiveData()

    fun updateTheme(theme: Theme) = viewModelScope.launch {
        preferencesManager.updateTheme(theme)
    }
}