package com.example.finup.Transactions.core

import com.example.finup.Transactions.mappers.toDomain
import com.example.finup.core.YearMonthCache
import com.example.finup.core.YearMonthDao

interface YearMonthRepository {


    interface GetOrCreateYearMonth {
        suspend fun getOrCreateYearMonth(month: Int, year: Int): YearMonth
    }

    interface GetAllPeriods {
        suspend fun getAllPeriods(): List<YearMonth>
    }

    interface All : GetOrCreateYearMonth, GetAllPeriods

    class Base(
        private val yearMonthDao: YearMonthDao,
        private val now: Now
    ) : All {


        override suspend fun getOrCreateYearMonth(
            month: Int,
            year: Int
        ): YearMonth {
            return yearMonthDao.getDateItem(month, year)?.let { foundCache ->
                foundCache.toDomain()
            } ?: run {
                val newId = now.timeInMills()
                val newCache = YearMonthCache(newId, month, year)
                yearMonthDao.insert(newCache)
                newCache.toDomain()
            }
        }

        override suspend fun getAllPeriods(): List<YearMonth> {

            val currentPeriods = yearMonthDao.getAllPeriods()

            val mappedValues = currentPeriods.map {
                YearMonth(
                    month = it.month,
                    year = it.year,
                    dateId = it.id,
                )
            }
            return mappedValues
        }
    }
}