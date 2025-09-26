package com.example.finup.Transactions.list.useCases


import com.example.finup.Transactions.core.DateProvider
import com.example.finup.Transactions.core.TransactionRepository
import com.example.finup.Transactions.core.YearMonth

interface GetTransactionsListByPeriodUseCase {

    suspend operator fun invoke(yearMonth: YearMonth, type: String): Result

    class Base(
        private val transactionRepository: TransactionRepository.ReadList,
        private val dateProvider: DateProvider.FormatDate
    ) :

        GetTransactionsListByPeriodUseCase {

        override suspend fun invoke(yearMonth: YearMonth, type: String): Result {

            val transactionList = transactionRepository.getTransactions(yearMonth.dateId, type)

            val formattedDateTitle = dateProvider.formatDate(yearMonth.year, yearMonth.month)
            val totalSumByMonth = transactionList.sumOf { it.sum }.toString()

            return Result(transactionList, formattedDateTitle, totalSumByMonth)
        }
    }
}