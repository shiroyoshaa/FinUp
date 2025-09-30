package com.example.finup.Transactions.model

interface DisplayItemUi {

    data class TransactionDate(
        val day: String,
        val totalSumByDay: String,
    ): DisplayItemUi

    data class TransactionDetails(
        val id: Long,
        val sum: Int,
        val name: String,
        val type: String,
        val dateId: Long,
    ): DisplayItemUi
}