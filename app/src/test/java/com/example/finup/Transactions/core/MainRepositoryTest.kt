package com.example.finup.Transactions.core

import com.example.finup.core.DateItemCache
import com.example.finup.core.TransactionCache
import com.example.finup.core.TransactionDao
import com.example.finup.core.YearMonthDao
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test


class MainRepositoryTest {

    private lateinit var now: FakeNow
    private lateinit var yearMonthDao: FakeYearMonth
    private lateinit var transactionDao: FakeTransactionDao
    private lateinit var repository: MainRepository.All

    @Before
    fun initialize() {
        now = FakeNow.Base(2L)
        yearMonthDao = FakeYearMonth.Base()
        transactionDao = FakeTransactionDao.Base()
        repository = MainRepository.Base(
            yearMonthDao = yearMonthDao,
            transactionDao = transactionDao,
            now = now,
        )
    }

    @Test
    fun yearMonthTest() = runBlocking {
        repository.createYearMonth(month = 9, year = 2025)
        val actualYearMonth = repository.getYearMonth(month = 9,year = 2025)
        val expectedYearMonth = YearMonth(
            dateId = 2L,
            month = 9,
            year = 2025,
        )
        assertEquals(expectedYearMonth, actualYearMonth)
    }

    @Test
    fun transactionsTest() = runBlocking {

        repository.createTransaction(sum = 5000, name = "Groceries", type = "Expense", day = 16, dateId = 5L)
        repository.createTransaction(sum = 3500, name = "Other", type = "Expense", day = 1, dateId = 5L)
        repository.createTransaction(sum = 2000, name = "Utilities", type = "Expense", day = 25, dateId = 5L)
        repository.createTransaction(sum = 8000, name = "Transfers", type = "Expense", day = 11, dateId = 5L)
        repository.createTransaction(sum = 1000, name = "Kaspi Bank", type = "Income", day = 25, dateId = 5L)

        val actualExpenseTransactionsListForPeriod: List<Transaction> = repository.getTransactions(dateId = 5L, type = "Expense")
        val expectedExpenseTransactionsListForPeriod: List<Transaction> = listOf(
            Transaction(id = 2L,sum = 5000, name = "Groceries", type = "Expense", day = 16, dateId = 5L),
            Transaction(id = 3L,sum = 3500, name = "Other", type = "Expense", day = 1, dateId = 5L),
            Transaction(id = 4L,sum = 2000, name = "Utilities", type = "Expense", day = 25, dateId = 5L),
            Transaction(id = 5L,sum = 8000, name = "Transfers", type = "Expense", day = 11, dateId = 5L),
        )
        assertEquals(expectedExpenseTransactionsListForPeriod,actualExpenseTransactionsListForPeriod)

        repository.editTransaction(transactionId = 4L,sum = 100000,"Transfers",type = "Expense",day = 10, dateId = 5L)
        val expectedEditedTransaction = Transaction(id = 4L,sum = 100000, name = "Transfers", type = "Expense", day = 10, dateId = 5L)
        val actualEditedTransaction = repository.getOneTransaction(id = 4L,type = "Expense")
        assertEquals(expectedEditedTransaction,actualEditedTransaction)

        repository.deleteTransaction(id = 5L)
        val actualFinalTransactions = repository.getTransactions(dateId = 5L,type = "Expense")
        val exptectedFinalTransactions = listOf(
            Transaction(id = 2L,sum = 5000, name = "Groceries", type = "Expense", day = 16, dateId = 5L),
            Transaction(id = 3L,sum = 3500, name = "Other", type = "Expense", day = 1, dateId = 5L),
            Transaction(id = 4L,sum = 100000, name = "Transfers", type = "Expense", day = 10, dateId = 5L),
        )
        assertEquals(exptectedFinalTransactions,actualFinalTransactions)
    }
}

interface FakeNow : Now {
    class Base(private var time: Long) : FakeNow {
        override fun timeInMills(): Long {
            return time++
        }
    }
}

private interface FakeYearMonth : YearMonthDao {

    class Base : FakeYearMonth {

        private val newList = mutableListOf<DateItemCache>()

        override suspend fun insert(dateTitleCache: DateItemCache) {
            newList.add(dateTitleCache)
        }

        override suspend fun getDateItem(dateMonth: Int, dateYear: Int): DateItemCache {
            return newList.find { (_, month, year) -> month == dateMonth && year == dateYear } !!
        }
    }
}


private interface FakeTransactionDao : TransactionDao {

    class Base : FakeTransactionDao {

        private val transactionsList = mutableListOf<TransactionCache>()

        override suspend fun insert(transactionCache: TransactionCache) {

            transactionsList.find { it.id == transactionCache.id }?.let {
                transactionsList.remove(it)
            }
            transactionsList.add(transactionCache)
        }

        override suspend fun getOneTransaction(
            transactionId: Long,
            transactionType: String
        ): TransactionCache {
            return transactionsList.find { it.id == transactionId && it.type == transactionType }!!
        }

        override suspend fun getTransactions(dateId: Long, type: String): List<TransactionCache> {
            return transactionsList.filter { it.dateId == dateId && it.type == type }.sortedBy { it.id }
        }

        override suspend fun delete(id: Long) {
            transactionsList.find { it.id == id }.let {
                transactionsList.remove(it)
            }
        }
    }
}
