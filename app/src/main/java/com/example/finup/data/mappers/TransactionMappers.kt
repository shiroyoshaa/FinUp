package com.example.finup.data.mappers

import com.example.finup.data.db.entities.TransactionCache
import com.example.finup.domain.models.Transaction

fun List<TransactionCache>.transactionListDataToDomain() =
    this.map { Transaction(it.id, it.sum, it.name, it.type, it.day, it.dateId) }

fun TransactionCache.transactionDataToDomain() =
    Transaction(this.id, this.sum, this.name, this.type, this.day, this.dateId)