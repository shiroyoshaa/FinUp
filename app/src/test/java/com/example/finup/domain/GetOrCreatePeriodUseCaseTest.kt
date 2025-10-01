package com.example.finup.domain

import com.example.finup.domain.useCases.GetOrCreatePeriodUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class GetOrCreatePeriodUseCaseTest {

    private lateinit var repository: FakeYearMonthRepository

    @Test
    fun `test useCase`() = runBlocking {

        repository = FakeYearMonthRepository.Base()
        repository.expectedPeriods(listOf(YearMonth(1L, 2, 2025), YearMonth(2L, 10, 2026)))
        val getOrCreatePeriodUseCase = GetOrCreatePeriodUseCase.Base(repository)
        val actualResult = getOrCreatePeriodUseCase(2025, 2)
        val expectedResult = YearMonth(1L, 2, 2025)
        repository.checkGetMethodCalledTimes(1)
        assertEquals(expectedResult, actualResult)

        //test if we didn`t have expected YearMonth in repository
        val secondActualResult = getOrCreatePeriodUseCase(2027,12)
        repository.checkCreateMethodIsCalled(2027,12)
        val secondExpectedResult = YearMonth(35L,12, 2027)
        assertEquals(secondExpectedResult,secondActualResult)
    }
}


private interface FakeYearMonthRepository : YearMonthRepository.GetAndCreate {

    fun expectedPeriods(expectedPeriods: List<YearMonth>)
    fun checkGetMethodCalledTimes(expected: Int)
    fun checkCreateMethodIsCalled(expectedYear: Int,expectedMonth: Int)

    class Base : FakeYearMonthRepository {

        private lateinit var mockedPeriods: List<YearMonth>
        private var actualCalledTimes = 0
        private var actualYear = 0
        private var actualMonth = 0

        override suspend fun getAllPeriods(): List<YearMonth> {
            actualCalledTimes++
            return mockedPeriods
        }

        override fun expectedPeriods(expectedPeriods: List<YearMonth>) {
            mockedPeriods = expectedPeriods
        }

        override suspend fun create(year: Int, month: Int): YearMonth {
            actualYear = year
            actualMonth = month
            return YearMonth(35L,month,year)

        }
        override fun checkGetMethodCalledTimes(expected: Int) {
            assertEquals(expected, actualCalledTimes)
        }

        override fun checkCreateMethodIsCalled(expectedYear: Int, expectedMonth: Int) {
            assertEquals(expectedYear,actualYear)
            assertEquals(expectedMonth,actualMonth)
        }
    }
}
