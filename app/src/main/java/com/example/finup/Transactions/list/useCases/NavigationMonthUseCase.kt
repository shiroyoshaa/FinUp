package com.example.finup.Transactions.list.useCases

import com.example.finup.Transactions.core.YearMonth
import com.example.finup.Transactions.core.YearMonthRepository


interface NavigationMonthUseCase {

    suspend operator fun invoke(currentMonth: YearMonth, forward: Boolean): YearMonth

    class Base(private val repository: YearMonthRepository.GetAllPeriods) : NavigationMonthUseCase {

        private var currentIndexOfMonth = 0

        override suspend operator fun  invoke(
            currentMonth: YearMonth,
            forward: Boolean,
        ): YearMonth {

            val months = repository.getAllPeriods()
            val indexOfCurrentYearMonth = months.indexOf(currentMonth)
            return when {

                forward && indexOfCurrentYearMonth <= months.lastIndex -> {
                    months[indexOfCurrentYearMonth + 1]
                }
                !forward && indexOfCurrentYearMonth > 0 -> {
                    months[indexOfCurrentYearMonth - 1]
                }

                else -> currentMonth
            }
        }
    }
}