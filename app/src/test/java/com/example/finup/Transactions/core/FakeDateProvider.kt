package com.example.finup.Transactions.core

import com.example.finup.core.Order
import org.junit.Assert.assertEquals
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.Locale

interface FakeDateProvider : DateProvider.All {

    fun checkFormatDateIsCalled(expectedDate: String)
    fun checkCurrentMonthCalled(expectedMonth: Int)
    fun checkCurrentYearCalled(expectedYear: Int)

    companion object {
        const val CURRENT_YEAR = "DateProvider#getCurrentYear"
        const val CURRENT_MONTH = "DateProvider#getCurrentMonth"
        const val FORMAT_DATE = "DateProvider#getCurrentMonth"

    }
    class Base(
        private val order: Order,
        private val locale: Locale,
        private val year: Int,
        private val month: Int
    ) : FakeDateProvider {

        private var actualYear: Int = 0
        private var actualMonth: Int = 0
        lateinit var actualDate: String
        override fun getCurrentYear(): Int {
            order.add(CURRENT_YEAR)
            actualYear = year
            return actualYear
        }

        override fun getCurrentMonth(): Int {
            order.add(CURRENT_MONTH)
            actualMonth = month
            return actualMonth
        }

        override fun formatDate(year: Int, month: Int): String {
            val date = YearMonth.of(actualYear, actualMonth)
            val formatter = DateTimeFormatter.ofPattern("MMMM yyyy", locale)
            actualDate = date.format(formatter)
            order.add(FORMAT_DATE)
            return actualDate
        }

        override fun checkFormatDateIsCalled(expectedDate: String) {
            assertEquals(expectedDate,actualDate)
        }

        override fun checkCurrentMonthCalled(expectedMonth: Int) {
            assertEquals(expectedMonth,actualMonth )
        }

        override fun checkCurrentYearCalled(expectedYear: Int) {
            assertEquals(expectedYear,actualYear)
        }

    }
}