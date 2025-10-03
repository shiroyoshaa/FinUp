package com.example.finup.data

import com.example.finup.domain.YearMonthStateRepository


class YearMonthStateRepositoryImpl(private val dataStoreManager: DataStoreManager): YearMonthStateRepository {
    override suspend fun getActiveYearMonthId(): Long {
        return dataStoreManager.getActiveYearMonthId()
    }

    override suspend fun setActiveYearMonthId(id: Long) {
        dataStoreManager.setActiveYearMonthId(id)
    }
}