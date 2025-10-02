package com.example.finup.main

import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

interface MainUiState {
    fun apply(bottomNavigationView: BottomNavigationView,floatingActionButton: FloatingActionButton)

    object Hide: MainUiState {
        override fun apply(
            bottomNavigationView: BottomNavigationView,
            floatingActionButton: FloatingActionButton
        ) {
            bottomNavigationView.visibility = View.GONE
            floatingActionButton.visibility = View.GONE
        }
    }

    object Show : MainUiState {
        override fun apply(
            bottomNavigationView: BottomNavigationView,
            floatingActionButton: FloatingActionButton
        ) {
            bottomNavigationView.visibility = View.VISIBLE
            floatingActionButton.visibility = View.VISIBLE
        }
    }
}