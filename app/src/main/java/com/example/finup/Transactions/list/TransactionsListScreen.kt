package com.example.finup.Transactions.list

import com.example.finup.main.Screen

data class TransactionsListScreen(
    val type: String,
): Screen.Replace(TransactionsListFragment())

