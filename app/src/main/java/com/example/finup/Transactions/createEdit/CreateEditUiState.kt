package com.example.finup.Transactions.createEdit

import android.view.View
import android.widget.Button
import android.widget.TextView
import com.google.android.material.button.MaterialButton

interface CreateEditUiState {

    fun show(buttons: List<MaterialButton>, titleTextView: TextView,deleteButton: Button)

    data class ShowCreateTransactionPage(
        val title: String,
    ) : CreateEditUiState {
        override fun show(buttons: List<MaterialButton>, titleTextView: TextView, deleteButton: Button) {
            titleTextView.text = title
            buttons.forEach {
                it.visibility = View.VISIBLE
            }
            deleteButton.visibility = View.GONE
        }
    }
    data class ShowEditTransactionPage(
        val title: String,
        val selectedCategory: String,
        val sum: String,
    ) : CreateEditUiState {
        override fun show(
            buttons: List<MaterialButton>,
            titleTextView: TextView,
            deleteButton: Button
        ) {
            titleTextView.text = title
            buttons.forEach {
                it.visibility = View.VISIBLE
                if(it.text == "selectedCategory") {
                    it.isSelected
                }
                it.visibility = View.VISIBLE
            }

        }
    }
}