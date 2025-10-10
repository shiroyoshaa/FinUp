package com.example.finup.data.repositories

import com.example.finup.domain.DataStoreManager
import com.example.finup.domain.repositories.SettingsStateRepository


class SettingsStateRepositoryImpl(private val dataStoreManager: DataStoreManager.All): SettingsStateRepository.All {

    override suspend fun restoreActiveYearMonthId(): Long {
        return dataStoreManager.restoreActiveYearMonthId()
    }

    override suspend fun saveActiveYearMonthId(id: Long) {
        dataStoreManager.saveActiveYearMonthId(id)
    }

    override suspend fun restoreScreenType(): String {
        return dataStoreManager.restoreActiveScreenType()
    }

    override suspend fun saveScreenType(screenType: String) {
        dataStoreManager.saveActiveScreeType(screenType)
    }

}