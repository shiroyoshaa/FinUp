package com.example.finup.Transactions.mappers

import com.example.finup.Transactions.core.Transaction
import com.example.finup.Transactions.list.DisplayItem

interface TransactionMappers {
    interface ToUiLayer {
        fun toUiLayer(transactions: List<Transaction>): List<DisplayItem>
    }
}