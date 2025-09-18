package com.example.finup.main

import androidx.lifecycle.ViewModel
import com.example.finup.Transactions.list.TransactionsListScreen


class MainViewModel(private val navigation: Navigation.Mutable) : ViewModel() {
    fun init() {
        navigation.update(TransactionsListScreen("Expense"))
    }

    fun navigateToIncomes() {
        navigation.update(TransactionsListScreen("Income"))
    }
}