package com.example.finup.Transactions.list.useCases


import com.example.finup.domain.DateProvider
import com.example.finup.domain.Result
import com.example.finup.domain.TransactionRepository
import com.example.finup.domain.YearMonth

interface GetTransactionsListByPeriodUseCase {

    suspend operator fun invoke(yearMonth: YearMonth, type: String): Result

    class Base(
        private val transactionRepository: TransactionRepository.GetTransactions,
        private val dateProvider: DateProvider.FormatDate
    ) :

        GetTransactionsListByPeriodUseCase {

        override suspend fun invoke(yearMonth: YearMonth, type: String): Result {

            val transactionList = transactionRepository.getTransactions(yearMonth.id, type)

            val formattedDateTitle = dateProvider.formatDate(yearMonth.year, yearMonth.month)
            val totalSumByMonth = transactionList.sumOf { it.sum }.toString()

            return Result(transactionList, formattedDateTitle, totalSumByMonth)
        }
    }
}