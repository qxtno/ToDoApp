package com.example.todoapp.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

enum class Theme {
    AUTO, DARK, LIGHT
}

@Singleton
class PreferencesManager @Inject constructor(@ApplicationContext val context: Context) {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    private val themeKey = stringPreferencesKey("themeKey")

    suspend fun updateTheme(theme: Theme) {
        context.dataStore.edit { settings ->
            settings[themeKey] = when (theme) {
                Theme.AUTO -> Theme.AUTO.name
                Theme.DARK -> Theme.DARK.name
                Theme.LIGHT -> Theme.LIGHT.name
            }
        }
    }

    val themeFlow: Flow<Theme> = context.dataStore.data.map { settings ->
        when (settings[themeKey]) {
            Theme.DARK.name -> Theme.DARK
            Theme.LIGHT.name -> Theme.LIGHT
            else -> Theme.AUTO
        }
    }
}