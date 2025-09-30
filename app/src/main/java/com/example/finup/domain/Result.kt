package com.example.finup.domain

data class Result(
    val listTransactions: List<Transaction>,
    val formattedDateYearMonth: String,
    val totalSumByMonth:String,
)