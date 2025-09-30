package com.example.finup.Transactions.createEdit

import com.example.finup.main.Screen

data class CreateEditTransactionScreen(
    val screenType: String,
    val transactionId: Long,
    val transactionType: String,
): Screen
