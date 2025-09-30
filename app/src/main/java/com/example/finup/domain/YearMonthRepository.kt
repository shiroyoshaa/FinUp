package com.example.finup.domain

import com.example.finup.data.mappers.listToDomain
import com.example.finup.data.mappers.toDomain
import com.example.finup.data.YearMonthCache
import com.example.finup.data.YearMonthDao

interface YearMonthRepository {


    interface GetAllPeriods {
        suspend fun getAllPeriods(): List<YearMonth>
    }

    interface Create {
        suspend fun create(year: Int, month: Int): YearMonth
    }

    interface LoadYearMonth {
        suspend fun getById(yearMonthId: Long): YearMonth

    }

    interface CreateAndLoad: Create, LoadYearMonth
    class Base(
        private val yearMonthDao: YearMonthDao,
        private val now: Now

    ) : CreateAndLoad,GetAllPeriods {


        override suspend fun getAllPeriods(): List<YearMonth> {

            val currentPeriods = yearMonthDao.getAllPeriods()

            return currentPeriods.listToDomain()
        }

        override suspend fun create(year: Int, month: Int): YearMonth {
            val newId = now.timeInMills()
            val newCache = YearMonthCache(newId, year, month)
            yearMonthDao.insert(YearMonthCache(newId, year, month))
            return newCache.toDomain()
        }

        override suspend fun getById(yearMonthId: Long): YearMonth {
            return yearMonthDao.getDateItem(yearMonthId).toDomain()
        }
    }
}