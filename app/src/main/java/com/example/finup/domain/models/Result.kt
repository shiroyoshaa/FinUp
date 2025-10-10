package com.example.finup.domain.models

data class Result(
    val listTransactions: List<Transaction>,
    val formattedDateYearMonth: String,
    val totalSumByMonth:String,
)