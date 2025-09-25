package com.example.finup.Transactions.list

interface DisplayItem {
    data class TransactionDate(
        val day: String,
        val totalSumByDay: String,
    ): DisplayItem

    data class TransactionDetails(
        val id: Long,
        val sum: Int,
        val name: String,
        val type: String,
        val dateId: Long,
    ): DisplayItem
}