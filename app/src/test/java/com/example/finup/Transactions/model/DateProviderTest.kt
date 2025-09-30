package com.example.finup.Transactions.model

import com.example.finup.domain.DateProvider
import junit.framework.TestCase.assertEquals
import org.junit.Test
import java.util.Locale


class DateProviderTest {

    @Test
    fun test() {
        val locale = Locale.ENGLISH
        val dateProvider = DateProvider.Base(locale)
        val currentYear = dateProvider.getCurrentYear()
        val expectedYear = 2025
        assertEquals(expectedYear,currentYear)

        val currentMonth = dateProvider.getCurrentMonth()
        val expectedMonth = 9
        assertEquals(expectedMonth,currentMonth)

        val actualFormattedDate = dateProvider.formatDate(currentYear,currentMonth)
        val expectedFormattedDate = "September 2025"
        assertEquals(expectedFormattedDate,actualFormattedDate)
    }
}
