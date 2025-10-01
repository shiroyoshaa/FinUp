package com.example.finup.Transactions.list.TransactionListUseCasesTest

import com.example.finup.Transactions.list.useCases.NavigationMonthUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Test

class NavigationMonthUseCaseTest {

    private lateinit var navigationMonthUseCase: NavigationMonthUseCase
    private lateinit var yearMonthRepository: FakeYearMonthRepository

    @Test
    fun test() = runBlocking {
        yearMonthRepository = FakeYearMonthRepository.Base()
    }

}