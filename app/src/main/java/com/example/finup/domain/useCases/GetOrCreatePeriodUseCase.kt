package com.example.finup.domain.useCases

import com.example.finup.domain.YearMonth
import com.example.finup.domain.YearMonthRepository

interface GetOrCreatePeriodUseCase {
    suspend operator fun invoke(year: Int, month: Int): YearMonth

    class Base(private val repository: YearMonthRepository.GetAndCreate): GetOrCreatePeriodUseCase {

        override suspend fun invoke(year: Int, month: Int): YearMonth {
            val listPeriods = repository.getAllPeriods()
            listPeriods.find { it.month == month && it.year == year }?.let {
                return it
            }
            val createdPeriod = repository.create(year,month)
            return createdPeriod
        }
    }
}