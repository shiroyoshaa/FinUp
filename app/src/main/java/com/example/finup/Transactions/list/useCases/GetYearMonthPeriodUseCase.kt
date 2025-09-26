package com.example.finup.Transactions.list.useCases

import com.example.finup.Transactions.core.DateProvider
import com.example.finup.Transactions.core.YearMonth
import com.example.finup.Transactions.core.YearMonthRepository

interface GetYearMonthPeriodUseCase {
    suspend operator fun invoke(): YearMonth

    class Base(private val yearMonthRepository: YearMonthRepository.GetOrCreateYearMonth,
    private val dateProvider: DateProvider.All): GetYearMonthPeriodUseCase{

        override suspend fun invoke(): YearMonth {

            val currentYear = dateProvider.getCurrentYear()
            val currentMonth = dateProvider.getCurrentMonth()

            val currentYearMonth =  yearMonthRepository.getOrCreateYearMonth(currentMonth, currentYear)
            return currentYearMonth
        }
    }
}