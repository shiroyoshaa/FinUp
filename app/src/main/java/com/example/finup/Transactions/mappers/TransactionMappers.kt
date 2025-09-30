package com.example.finup.Transactions.mappers

import com.example.finup.Transactions.model.DisplayItemUi
import com.example.finup.domain.Transaction

interface TransactionMappers {
    interface ToUiLayer {
        fun toUiLayer(transactions: List<Transaction>): List<DisplayItemUi>
    }
}