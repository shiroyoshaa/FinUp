package com.example.finup.Transactions.list.TransactionListUseCasesTest

import com.example.finup.Transactions.list.useCases.NavigationMonthUseCase
import com.example.finup.domain.YearMonth
import com.example.finup.domain.YearMonthRepository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Test

class NavigationMonthUseCaseTest {

    private lateinit var navigationMonthUseCase: NavigationMonthUseCase
    private lateinit var yearMonthRepository: FakeYearMonthRepository

    @Test
    fun test() = runBlocking {
        yearMonthRepository = FakeYearMonthRepository.Base()
        navigationMonthUseCase = NavigationMonthUseCase.Base(yearMonthRepository)
        yearMonthRepository.expectedListYearMonth(listOf(YearMonth(1L, 11, 2025), YearMonth(2L,12,2025)))
        val actualYearMonth = navigationMonthUseCase(YearMonth(2L, 12, 2025), false)
        val expectedYearMonth = YearMonth(1L,11,2025)
        assertEquals(expectedYearMonth,actualYearMonth)


        val actual = navigationMonthUseCase(YearMonth(1L,11,2025),false)
        val expected = YearMonth(1L,11,2025)
        assertEquals(actual,expected)
    }
}

private interface FakeYearMonthRepository : YearMonthRepository.GetAllPeriods {

    fun expectedListYearMonth(expected: List<YearMonth>)
    fun checkCalledTimes(expected: Int)

    class Base : FakeYearMonthRepository {

        private lateinit var mockedList: List<YearMonth>
        private var actualCalledTime = 0

        override suspend fun getAllPeriods(): List<YearMonth> {
            actualCalledTime++
            return mockedList
        }

        override fun expectedListYearMonth(expected: List<YearMonth>) {
            mockedList = expected
        }

        override fun checkCalledTimes(expected: Int) {
            assertEquals(actualCalledTime, expected)
        }
    }
}