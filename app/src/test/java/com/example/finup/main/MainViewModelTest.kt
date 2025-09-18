package com.example.finup.main

import com.example.finup.core.FakeNavigation
import com.example.finup.core.Order
import org.junit.Test
import com.example.finup.expense.list.ExpensesListScreen
import com.example.finup.income.list.IncomesListScreen
import org.junit.Before

class MainViewModelTest {

    @get:Before
    val order = Order()
    val navigation = FakeNavigation.Base(order)
    val viewModel = MainViewModel(
        navigation = navigation
    )

    @Test
    fun `init test`() {
        viewModel.init()
        navigation.update(ExpensesListScreen)
        navigation.check(ExpensesListScreen)
    }

    @Test
    fun `navigate to income list page test`() {
        viewModel.navigateToIncomes()
        navigation.update(IncomesListScreen)
        navigation.check(IncomesListScreen)
    }
}