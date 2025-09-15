package com.example.finup.main

import com.example.finup.core.FakeNavigation
import com.example.finup.core.Order
import org.junit.Test
import com.example.finup.expense.list.ExpenseListScreen
class MainViewModelTest {


    @Test
    fun`test`(){
        val order = Order()
        val navigation = FakeNavigation.Base(order)
        val viewModel = MainViewModel(
            navigation = navigation
        )
        viewModel(firstRun = true)
        navigation.update(ExpenseListScreen)
    }
}