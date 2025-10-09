package com.example.finup.data.repositories

import com.example.finup.data.db.dao.YearMonthDao
import com.example.finup.data.db.entities.YearMonthCache
import com.example.finup.data.mappers.yearMonthDataToDomain
import com.example.finup.data.mappers.yearMonthListDataToDomain
import com.example.finup.domain.models.YearMonth
import com.example.finup.domain.repositories.YearMonthRepository
import com.example.finup.domain.repositories.YearMonthRepository.Delete
import com.example.finup.domain.repositories.YearMonthRepository.GetAllPeriods
import com.example.finup.domain.repositories.YearMonthRepository.GetAndCreate

class YearMonthRepositoryImpl(
    private val yearMonthDao: YearMonthDao,
    private val now: Now
) : YearMonthRepository.CreateAndLoad, GetAllPeriods, GetAndCreate, Delete {

    override suspend fun getAllPeriods(): List<YearMonth> {

        val currentPeriods = yearMonthDao.getAllPeriods()
        return currentPeriods.yearMonthListDataToDomain()
    }

    override suspend fun create(year: Int, month: Int): YearMonth {
        val newId = now.timeInMills()
        var newCache = YearMonthCache(newId, year, month)
        yearMonthDao.insert(newCache)
        return newCache.yearMonthDataToDomain()
    }

    override suspend fun getById(yearMonthId: Long): YearMonth {
        return yearMonthDao.getDateItem(yearMonthId).yearMonthDataToDomain()
    }

    override suspend fun delete(dateId: Long) {
        yearMonthDao.delete(dateId)
    }

}