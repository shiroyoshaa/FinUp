package com.example.finup

import android.content.Context
import androidx.room.Room
import androidx.room.Transaction
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import kotlin.jvm.Throws
import kotlin.jvm.java
import kotlin.math.exp

@RunWith(AndroidJUnit4::class)
class RoomTest {

    private lateinit var monthYearDao: MonthYearDao
    private lateinit var transactionDao: TransactionDao
    private lateinit var db: TestDataBase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val db = Room.inMemoryDatabaseBuilder(
            context,
            TestDataBase::class.java,
        ).build()
        monthYearDao = db.monthYearDao()
        transactionDao = db.transactionDao()
    }

    @After
    @Throws(IOException::class)
    fun clear() {
        db.clear()
    }

    @Test
    fun testDates() = runBlocking {
        monthYearDao.insert(dateTitleCache = DateItemCache(id = 3L, month = 12, year = 2021))
        val actual = monthYearDao.getDateItem(month = 12, year = 2021)
        val expected = DateItemCache(id = 3L, month = 12, year = 2021)
        assertEquals(expected, actual)

    }

    @Test
    fun testDifferentType() {
        transactionDao.insert(
            TransactionCache = TransactionCache(
                id = 1L, sum = 1500, name = "Groceries",
                type = "Expense", day = 2, DateId = 1L
            )
        )
        transactionDao.insert(
            TransactionCache = TransactionCache(
                id = 2L, sum = 5000, name = "Other",
                type = "Income", day = 7, DateId = 1L
            )
        )
        transactionDao.insert(
            TransactionCache = TransactionCache(
                id = 3L, sum = 3500, name = "payments",
                type = "Expense", day = 18, DateId = 1L
            )
        )
        transactionDao.insert(
            TransactionCache = TransactionCache(
                id = 4L, sum = 15000, name = "Kaspi Bank",
                type = "Income", day = 29, DateId = 1L
            )
        )
        val actual = transactionDao.getTransactions(DateId = 1L, type = "Expense")

        val expected = listOf(
            TransactionCache(
                id = 1L, sum = 1500, name = "Groceries",
                type = "Expense", day = 2, DateId = 1L
            ),
            TransactionCache(
                id = 3L, sum = 3500, name = "payments",
                type = "Expense", day = 18, DateId = 1L
            ),
        )
        assertEquals(expected, actual)
    }


    @Test
    fun testIncomes() = runBlocking {
        transactionDao.insert(
            TransactionCache = TransactionCache(
                id = 2L, sum = 5000, name = "Kaspi Bank",
                type = "Income", day = 25, DateId = 1L
            )
        )
        transactionDao.insert(
            TransactionCache = TransactionCache(
                id = 3L, sum = 1000, name = "Kaspi Bank",
                type = "Income", day = 15, DateId = 1L
            )
        )
        transactionDao.insert(
            TransactionCache = TransactionCache(
                id = 4L, sum = 15000, name = "BCC bank",
                type = "Income", day = 29, DateId = 2L
            )
        )
        transactionDao.delete(id = 3L)
        val expected = transactionDao.getTransactions(DateId = 1L, type = "Income")
        val actual = listOf(
            TransactionCache(
                id = 2L, sum = 5000, name = "Kaspi Bank",
                type = "Income", day = 25, DateId = 1L
            )
        )
        assertEquals(expected, actual)
    }

    @Test
    fun editTransaction() = runBlocking {
        transactionDao.insert(
            TransactionCache = TransactionCache(
                id = 1L, sum = 5000, name = "BCC bank",
                type = "Income", day = 25, DateId = 1L
            )
        )
        transactionDao.insert(
            TransactionCache = TransactionCache(
                id = 2L, sum = 1, name = "Kaspi Bank",
                type = "Income", day = 25, DateId = 1L
            )
        )


        transactionDao.insert(
            TransactionCache(
                id = 1L, sum = 250000, name = "Kaspi Bank",
                type = "Income", day = 10, DateId = 1L
            )
        )
        val expected = transactionDao.getOneTransaction(DateId = 1L, type = "Income")

        val actual =  TransactionCache(
            id = 1L, sum = 250000, name = "Kaspi Bank",
            type = "Income", day = 10, DateId = 1L
        )
        assertEquals(expected,actual)
    }
}