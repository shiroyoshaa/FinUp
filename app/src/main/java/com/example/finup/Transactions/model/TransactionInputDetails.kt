package com.example.finup.Transactions.model

data class TransactionInputDetails(
    val type: String,
    val selectedCategory: String,
    val sum: Int,
    val day: Int,
    val month: Int,
    val year: Int,
)
