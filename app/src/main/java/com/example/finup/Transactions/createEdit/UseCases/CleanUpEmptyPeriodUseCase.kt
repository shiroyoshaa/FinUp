package com.example.finup.Transactions.createEdit.UseCases

interface CleanUpEmptyPeriodUseCase {
    suspend operator fun invoke(dateId: Long)
}