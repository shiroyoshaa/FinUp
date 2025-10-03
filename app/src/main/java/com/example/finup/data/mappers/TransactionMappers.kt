package com.example.finup.data.mappers

import com.example.finup.data.TransactionCache
import com.example.finup.domain.Transaction

fun List<TransactionCache>.transactionListDataToDomain() =
    this.map { Transaction(it.id, it.sum, it.name, it.type, it.day, it.dateId) }

fun TransactionCache.transactionDataToDomain() =
    Transaction(this.id, this.sum, this.name, this.type, this.day, this.dateId)