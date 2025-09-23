package com.example.finup.Transactions.core

import androidx.lifecycle.LiveData

interface UiStateLiveDataWrapper {
    interface Update{
        fun update(value: UiState)
    }
    interface Read {
        fun liveData(): LiveData<UiState>
    }
    interface All: Update, Read

}