package com.example.finup.domain

import com.example.finup.domain.useCases.CleanUpEmptyPeriodUseCase
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class CleanUpEmptyPeriodUseCaseTest {

    private lateinit var transactionRepository: FakeTransactionRepository
    private lateinit var yearMonthRepository: FakeDeleteYearMonthRepository
    private lateinit var cleanUpEmptyPeriodUseCase: CleanUpEmptyPeriodUseCase

    @Before
    fun setUp() {
        transactionRepository = FakeTransactionRepository.Base()
        yearMonthRepository = FakeDeleteYearMonthRepository.Base()
        cleanUpEmptyPeriodUseCase =
            CleanUpEmptyPeriodUseCase.Base(transactionRepository, yearMonthRepository)
    }

    @Test
    fun `test if we had transactions with expected dateId`() = runBlocking {
        transactionRepository.expectedTransactionsList(
            listOf(
                Transaction(
                    2L,
                    3000,
                    "Utilities",
                    "Expense",
                    25,
                    3L
                ), Transaction(
                    3L,
                    4500,
                    "Other",
                    "Expense",
                    13,
                    3L
                )
            )
        )
        cleanUpEmptyPeriodUseCase(3L)
        transactionRepository.checkGetTransactionsIsCalled(2)
    }

    @Test
    fun `test if we didnt have transactions with expected dateId`() = runBlocking {
        transactionRepository.expectedTransactionsList(
            listOf()
        )
        cleanUpEmptyPeriodUseCase(2L)
        transactionRepository.checkGetTransactionsIsCalled(2)
        yearMonthRepository.checkCalledTimes(2L)
    }
}

private interface FakeTransactionRepository : TransactionRepository.GetTransactions {

    fun checkGetTransactionsIsCalled(expected: Int)
    fun expectedTransactionsList(mocked: List<Transaction>)

    class Base : FakeTransactionRepository {

        private var actualCalledTimes = 0
        private lateinit var mockedList: List<Transaction>

        override suspend fun getTransactions(dateId: Long, type: String): List<Transaction> {
            actualCalledTimes++
            return mockedList
        }

        override fun expectedTransactionsList(mocked: List<Transaction>) {
            mockedList = mocked
        }

        override fun checkGetTransactionsIsCalled(expected: Int) {
            assertEquals(expected, actualCalledTimes)
        }
    }
}

private interface FakeDeleteYearMonthRepository : YearMonthRepository.Delete {

    fun checkCalledTimes(expected: Long)

    class Base : FakeDeleteYearMonthRepository {

        var actualCalledId = 0L

        override suspend fun delete(dateId: Long) {
            actualCalledId = dateId
        }

        override fun checkCalledTimes(expected: Long) {
            assertEquals(expected, actualCalledId)
        }
    }
}