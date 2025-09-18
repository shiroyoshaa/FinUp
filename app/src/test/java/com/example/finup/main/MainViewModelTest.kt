package com.example.finup.main

import com.example.finup.Transactions.list.TransactionsListScreen
import com.example.finup.core.FakeNavigation
import com.example.finup.core.Order
import org.junit.Before
import org.junit.Test

class MainViewModelTest {

    @get:Before
    private val order = Order()
    private val navigation = FakeNavigation.Base(order)
    private val viewModel = MainViewModel(
        navigation = navigation
    )

    @Test
    fun `init test`() {
        viewModel.init()
        navigation.update(TransactionsListScreen(type = "Expense"))
        navigation.check(TransactionsListScreen(type = "Expense"))
    }

    @Test
    fun `navigate to incomeList test`() {
        viewModel.navigateToIncomes()
        navigation.update(TransactionsListScreen(type = "Income"))
        navigation.check(TransactionsListScreen(type = "Income"))
    }
}