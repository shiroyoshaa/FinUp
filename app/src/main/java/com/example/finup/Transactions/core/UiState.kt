package com.example.finup.Transactions.core

interface UiState {
    data class ShowDateTitle(
        val title: String,
        val total: String,
    ): UiState
}