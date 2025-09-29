package com.example.finup.Transactions.createEdit

interface CreateEditUiState {

    data class ShowCreateTransactionPage(
        val title: String,
    ): CreateEditUiState

}