package com.thk.data.datasource

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.thk.data.models.Priority
import com.thk.data.util.Constants
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = Constants.PREFERENCE_NAME)

interface DataStoreSource {
    fun readSortState(): Flow<String>
    suspend fun persistSortState(priority: Priority)
}

@ViewModelScoped
class DataStoreSourceImpl @Inject constructor(
    context: Context
) : DataStoreSource {
    private object PreferenceKeys {
        val sortKey = stringPreferencesKey(name = Constants.PREFERENCE_KEY)
    }

    private val dataStore = context.dataStore

    override fun readSortState() = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            preferences[PreferenceKeys.sortKey] ?: Priority.NONE.name
        }

    override suspend fun persistSortState(priority: Priority) {
        dataStore.edit { preference ->
            preference[PreferenceKeys.sortKey] = priority.name
        }
    }
}