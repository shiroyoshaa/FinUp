package com.example.finup.domain.useCases

import com.example.finup.domain.YearMonth
import com.example.finup.domain.YearMonthRepository


interface NavigationMonthUseCase {

    suspend operator fun invoke(currentMonth: YearMonth, forward: Boolean): YearMonth

    class Base(private val repository: YearMonthRepository.GetAllPeriods) : NavigationMonthUseCase {

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
                    months[indexOfCurrentYearMonth - 1]                }

                else -> currentMonth
            }
        }
    }
}