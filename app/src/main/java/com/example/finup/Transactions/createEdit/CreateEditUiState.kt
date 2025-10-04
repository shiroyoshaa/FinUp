package com.example.finup.Transactions.createEdit

import android.view.View
import android.widget.Button
import android.widget.TextView
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

interface CreateEditUiState {

    fun show(
        buttons: List<MaterialButton>,
        titleTextView: TextView,
        dateTextView: TextView,
        deleteButton: Button,
        textInputEditText: TextInputEditText
    )

    data class ShowCreateTransactionPage(
        val title: String,
    ) : CreateEditUiState {
        override fun show(
            buttons: List<MaterialButton>,
            titleTextView: TextView,
            dateTextView: TextView,
            deleteButton: Button,
            textInputEditText: TextInputEditText
        ) {
            titleTextView.text = title
            buttons.forEach {
                it.visibility = View.VISIBLE
            }
            deleteButton.visibility = View.GONE
        }
    }
        data class ShowEditTransactionPage(
            val title: String,
            val date: String,
            val selectedCategory: String,
            val sum: String,
        ) : CreateEditUiState {
            override fun show(
                buttons: List<MaterialButton>,
                titleTextView: TextView,
                dateTextView: TextView,
                deleteButton: Button,
                textInputEditText: TextInputEditText
            ) {
                textInputEditText.setText(sum)
                dateTextView.text = date
                titleTextView.text = title
                buttons.forEach {
                    it.isChecked = false
                    it.visibility = View.VISIBLE
                    if (it.text == selectedCategory) {
                        it.isChecked = true
                    }
                }
                deleteButton.visibility = View.VISIBLE
            }
        }
}