package com.example.finup

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.finup.core.AppDataBase
import com.example.finup.core.DateItemCache
import com.example.finup.core.DateItemDao
import com.example.finup.core.TransactionCache
import com.example.finup.core.TransactionDao
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class RoomTest {

    private lateinit var dateItemDao: DateItemDao
    private lateinit var transactionDao: TransactionDao
    private lateinit var db: AppDataBase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context,
            AppDataBase::class.java,
        ).build()
        dateItemDao = db.dateItemDao()
        transactionDao = db.transactionDao()
    }

    @After
    @Throws(IOException::class)
    fun clear() {
        db.close()
    }

    @Test
    fun testDates() = runBlocking {
        dateItemDao.insert(dateTitleCache = DateItemCache(id = 3L, month = 12, year = 2021))
        val actual = dateItemDao.getDateItem(month = 12, year = 2021)
        val expected = DateItemCache(id = 3L, month = 12, year = 2021)
        assertEquals(expected, actual)

    }

    @Test
    fun testDifferentType() = runBlocking {
        transactionDao.insert(
            transactionCache = TransactionCache(
                id = 1L, sum = 1500, name = "Groceries",
                type = "Expense", day = 2, dateId = 1L
            )
        )
        transactionDao.insert(
            transactionCache = TransactionCache(
                id = 2L, sum = 5000, name = "Other",
                type = "Income", day = 7, dateId = 1L
            )
        )
        transactionDao.insert(
            transactionCache = TransactionCache(
                id = 3L, sum = 3500, name = "payments",
                type = "Expense", day = 18, dateId = 1L
            )
        )
        transactionDao.insert(
            transactionCache = TransactionCache(
                id = 4L, sum = 15000, name = "Kaspi Bank",
                type = "Income", day = 29, dateId = 1L
            )
        )
        val actual = transactionDao.getTransactions(dateId = 1L, type = "Expense")

        val expected = listOf(
            TransactionCache(
                id = 1L, sum = 1500, name = "Groceries",
                type = "Expense", day = 2, dateId = 1L
            ),
            TransactionCache(
                id = 3L, sum = 3500, name = "payments",
                type = "Expense", day = 18, dateId = 1L
            ),
        )
        assertEquals(expected, actual)
    }


    @Test
    fun testIncomes() = runBlocking {
        transactionDao.insert(
            transactionCache = TransactionCache(
                id = 2L, sum = 5000, name = "Kaspi Bank",
                type = "Income", day = 25, dateId = 1L
            )
        )
        transactionDao.insert(
            transactionCache = TransactionCache(
                id = 3L, sum = 1000, name = "Kaspi Bank",
                type = "Income", day = 15, dateId = 1L
            )
        )
        transactionDao.insert(
            transactionCache = TransactionCache(
                id = 4L, sum = 15000, name = "BCC bank",
                type = "Income", day = 29, dateId = 2L
            )
        )
        transactionDao.delete(id = 3L)
        val actual = transactionDao.getTransactions(dateId = 1L, type = "Income")
        val expected = listOf(
            TransactionCache(
                id = 2L, sum = 5000, name = "Kaspi Bank",
                type = "Income", day = 25, dateId = 1L
            ), TransactionCache(
                id = 3L, sum = 1000, name = "Kaspi Bank",
                type = "Income", day = 15, dateId = 1L
            )
        )
        assertEquals(expected, actual)
    }

    @Test
    fun editTransaction() = runBlocking {
        transactionDao.insert(
            transactionCache = TransactionCache(
                id = 1L, sum = 5000, name = "BCC bank",
                type = "Income", day = 25, dateId = 1L
            )
        )
        transactionDao.insert(
            transactionCache = TransactionCache(
                id = 2L, sum = 1, name = "Kaspi Bank",
                type = "Income", day = 25, dateId = 1L
            )
        )


        transactionDao.insert(
            TransactionCache(
                id = 1L, sum = 250000, name = "Kaspi Bank",
                type = "Income", day = 10, dateId = 1L
            )
        )
        val expected = transactionDao.getOneTransaction(id = 1L, type = "Income")

        val actual = TransactionCache(
            id = 1L, sum = 250000, name = "Kaspi Bank",
            type = "Income", day = 10, dateId = 1L
        )
        assertEquals(expected, actual)
    }
}