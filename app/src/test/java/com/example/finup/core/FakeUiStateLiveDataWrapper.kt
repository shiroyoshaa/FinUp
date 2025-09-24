package com.example.finup.core

import android.R.attr.order
import androidx.lifecycle.LiveData
import com.example.finup.Transactions.core.UiStateLiveDataWrapper
import junit.framework.TestCase.assertEquals

interface FakeUiStateLiveDataWrapper: UiStateLiveDataWrapper.All {

    fun check(expectedUiState: UiState)

    companion object {
        private const val UI_STATE_UPDATE_LIVEDATA = "UiStateLiveDataWrapper#Update"
    }

    class Base(private val order: Order): FakeUiStateLiveDataWrapper {

        lateinit var actualUiState: UiState

        override fun update(value: UiState) {
            actualUiState = value
            order.add(UI_STATE_UPDATE_LIVEDATA)
        }

        override fun liveData(): LiveData<UiState> {
            throw IllegalStateException("not used in test")
        }

        override fun check(expectedUiState: UiState) {
            assertEquals(expectedUiState,actualUiState)
        }
    }

}