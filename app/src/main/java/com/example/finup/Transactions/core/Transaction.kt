package com.example.finup.Transactions.core

data class Transaction(
    val id: Long,
    val sum: Int,
    val name: String,
    val type: String,
    val day: Int,
    val dateId: Long
)
