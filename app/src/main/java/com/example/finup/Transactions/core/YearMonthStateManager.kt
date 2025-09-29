package com.example.finup.Transactions.core

import androidx.lifecycle.SavedStateHandle

interface YearMonthStateManager {

    interface InitialGetter {

        suspend fun getInitialYearMonth(): YearMonth
    }

    interface StateSaver {
        fun saveYearMonthState(yearMonth: YearMonth)
    }

    interface All : InitialGetter, StateSaver
    companion object {
        private const val CURRENT_YEAR_MONTH_ID_KEY = "YearMonthId"
    }

    class Base(
        private val repository: YearMonthRepository.CreateAndLoad,
        private val saveStateHandle: SavedStateHandle,
        private val dateProvider: DateProvider.Getters,
    ) : All {

        override suspend fun getInitialYearMonth(): YearMonth {
            val savedId = saveStateHandle.get<Long?>(CURRENT_YEAR_MONTH_ID_KEY)
            return if (savedId != null) {
                repository.getById(savedId)
            } else {
                repository.create(dateProvider.getCurrentYear(),dateProvider.getCurrentMonth())
            }
        }

        override fun saveYearMonthState(yearMonth: YearMonth) {
            saveStateHandle[CURRENT_YEAR_MONTH_ID_KEY] = yearMonth.id
        }
    }
}