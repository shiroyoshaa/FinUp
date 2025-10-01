package com.example.finup.Transactions.createEdit.UseCases

import com.example.finup.domain.TransactionRepository
import com.example.finup.domain.YearMonthRepository

interface CleanUpEmptyPeriodUseCase {

    suspend operator fun invoke(dateId: Long)

    class Base(
        private val transactionRepository: TransactionRepository.GetTransactions,
        private val yearMonthRepository: YearMonthRepository.Delete
    ) : CleanUpEmptyPeriodUseCase {

        override suspend fun invoke(dateId: Long) {
            val currentExpenseTransactionList = transactionRepository.getTransactions(dateId,"Expense")
            val currentIncomeTransactionList = transactionRepository.getTransactions(dateId,"Income")
            if(currentExpenseTransactionList.isEmpty() && currentIncomeTransactionList.isEmpty()) {
                yearMonthRepository.delete(dateId)
            }
        }
    }
}