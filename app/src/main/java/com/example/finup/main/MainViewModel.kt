package com.example.finup.main

import androidx.lifecycle.ViewModel
import com.example.finup.Transactions.createEdit.CreateEditTransactionScreen
import com.example.finup.Transactions.list.TransactionsListScreen


class MainViewModel(private val navigation: Navigation.Mutable,
private val uiStateLiveDataWrapper: MainUiStateLiveDataWrapper.Mutable) : ViewModel() {

    fun init(firstRun: Boolean) {
        navigation.update(TransactionsListScreen("Expense"))
    }

    fun navigateToIncomes() {
        navigation.update(TransactionsListScreen("Income"))
    }
    fun navigateToExpenses(){
        navigation.update(TransactionsListScreen("Expense"))
    }
    fun createTransaction(type: String){
        uiStateLiveDataWrapper.update(MainUiState.Hide)
        navigation.update(CreateEditTransactionScreen("Create",0L,type))
    }
}