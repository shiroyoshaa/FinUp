package com.example.finup.main

import androidx.lifecycle.ViewModel
import com.example.finup.Transactions.createEdit.CreateEditTransactionScreen
import com.example.finup.Transactions.list.TransactionsListScreen


class MainViewModel(private val navigation: Navigation.Mutable,
private val uiStateLiveDataWrapper: MainUiStateLiveDataWrapper.Mutable,
    private val typeLiveDataWrapper: TypeLiveDataWrapper.Mutable,
) : ViewModel() {

    fun init(firstRun: Boolean,) {
        if(firstRun)
            typeLiveDataWrapper.update("Expense")
            navigation.update(TransactionsListScreen("Expense"))
    }

    fun navigateToIncomes() {
        typeLiveDataWrapper.update("Income")
        navigation.update(TransactionsListScreen("Income"))
    }

    fun navigateToExpenses(){
        typeLiveDataWrapper.update("Expense")
        navigation.update(TransactionsListScreen("Expense"))
    }

    fun createTransaction(type: String){
        uiStateLiveDataWrapper.update(MainUiState.Hide)
        navigation.update(CreateEditTransactionScreen("Create",0L,type))
    }

    fun navigationLiveData() = navigation.liveData()
    fun uiStateLiveData() = uiStateLiveDataWrapper.liveData()
    fun typeLiveData() = typeLiveDataWrapper.liveData()
}