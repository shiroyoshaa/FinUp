package com.example.finup.domain

import android.annotation.SuppressLint
import java.time.Clock
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.Locale

interface DateProvider {

    interface GetYear {
        fun getCurrentYear(): Int
    }

    interface GetMonth {
        fun getCurrentMonth(): Int
    }

    interface FormatDate {
        fun formatDate(year: Int, month: Int): String
    }

    interface Getters: GetYear, GetMonth
    interface All: Getters, FormatDate

    @SuppressLint("NewApi")
    class Base(private val locale: Locale = Locale.getDefault(),
               private val clock: Clock = Clock.systemDefaultZone(),
    ) : All {

        override fun getCurrentYear() = YearMonth.now(clock).year


        override fun getCurrentMonth() = YearMonth.now(clock).monthValue


        override fun formatDate(year: Int, month: Int): String {
            val date = YearMonth.of(year,month)
            val formatter = DateTimeFormatter.ofPattern("MMMM yyyy",locale)
            return date.format(formatter)
        }
    }
}