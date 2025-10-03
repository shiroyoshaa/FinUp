package com.example.finup.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "YearMonthStateId")

class DataStoreManager(private val context: Context) {
    private val dataStore = context.dataStore
    companion object {
        val CURRENT_MONTH_ID_KEY = longPreferencesKey("current_month_id")
        const val DEFAULT_ID = 0L
    }

    suspend fun setActiveYearMonthId(id: Long){
        dataStore.edit {
            it[CURRENT_MONTH_ID_KEY] = id
        }
    }

    suspend fun getActiveYearMonthId(): Long {
        return dataStore.data
            .first()[CURRENT_MONTH_ID_KEY] ?: DEFAULT_ID
    }
}