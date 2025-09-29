package com.example.finup.Transactions.list

interface TransactionsListUiState {

    data class ShowDateTitle(
        val title: String,
        val total: String,
    ) : TransactionsListUiState

}