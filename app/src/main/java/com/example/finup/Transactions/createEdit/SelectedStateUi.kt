package com.example.finup.Transactions.createEdit

data class SelectedStateUi(
    val selectedCategory: String,
    val sum: Int,
    val day: Int,
    val month: Int,
    val year: Int,
) {
    fun checkIsValid(): Boolean {
        return selectedCategory != "" && year != 0 && sum != 0
    }
}
