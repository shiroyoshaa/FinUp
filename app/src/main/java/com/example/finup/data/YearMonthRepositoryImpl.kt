package com.example.finup.data

import com.example.finup.data.mappers.yearMonthDataToDomain
import com.example.finup.data.mappers.yearMonthListDataToDomain
import com.example.finup.domain.Now
import com.example.finup.domain.YearMonth
import com.example.finup.domain.YearMonthRepository.CreateAndLoad
import com.example.finup.domain.YearMonthRepository.Delete
import com.example.finup.domain.YearMonthRepository.GetAllPeriods
import com.example.finup.domain.YearMonthRepository.GetAndCreate

class YearMonthRepositoryImpl(
    private val yearMonthDao: YearMonthDao,
    private val now: Now

) : CreateAndLoad,GetAllPeriods, GetAndCreate, Delete {

    override suspend fun getAllPeriods(): List<YearMonth> {

        val currentPeriods = yearMonthDao.getAllPeriods()

        return currentPeriods.yearMonthListDataToDomain()
    }

    override suspend fun create(year: Int, month: Int): YearMonth {
        val newId = now.timeInMills()
        val newCache = YearMonthCache(newId, year, month)
        yearMonthDao.insert(YearMonthCache(newId, year, month))
        return newCache.yearMonthDataToDomain()
    }

    override suspend fun getById(yearMonthId: Long): YearMonth {
        return yearMonthDao.getDateItem(yearMonthId).yearMonthDataToDomain()
    }

    override suspend fun delete(dateId: Long) {

    }
}