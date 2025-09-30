package com.example.finup.Transactions.createEdit.UseCases

import com.example.finup.domain.YearMonth

interface GetOrCreatePeriodUseCase {
    suspend operator fun invoke(year: Int, month: Int): YearMonth
}