package com.example.finup.Transactions.createEdit

interface CreateEditUiState {

    data class ShowCreateTransactionPage(
        val title: String,
    ) : CreateEditUiState

    data class ShowEditTransactionPage(
        val title: String,
        val selectedCategory: String,
        val sum: String,
    ) : CreateEditUiState

}