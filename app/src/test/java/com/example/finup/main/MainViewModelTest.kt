package com.example.finup.main

import androidx.lifecycle.LiveData
import com.example.finup.Transactions.createEdit.CreateEditTransactionScreen
import com.example.finup.Transactions.list.TransactionsListScreen
import com.example.finup.core.FakeNavigation
import com.example.finup.core.Order
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner


@RunWith(RobolectricTestRunner::class)
class MainViewModelTest {

    @get:Before
    private val order = Order()
    private val navigation = FakeNavigation.Base(order)
    private val uiStateLiveDataWrapper = FakeMainUiStateLiveDataWrapper.Base(order)
    private val viewModel = MainViewModel(
        navigation = navigation,
        uiStateLiveDataWrapper = uiStateLiveDataWrapper,
    )

    @Test
    fun `init test`() {
        viewModel.init(firstRun = true)
        navigation.check(TransactionsListScreen(type = "Expense"))
    }

    @Test
    fun `navigate to incomeList test`() {
        viewModel.navigateToIncomes()
        navigation.check(TransactionsListScreen(type = "Income"))
    }

    @Test
    fun `navigate to create page`() {
        viewModel.createTransaction(type = "Expense")
        uiStateLiveDataWrapper.check(MainUiState.Hide)
        navigation.check(CreateEditTransactionScreen(screenType = "Create", 0L, "Expense"))

    }
}

interface FakeMainUiStateLiveDataWrapper : MainUiStateLiveDataWrapper.Mutable {

    fun check(expected: MainUiState)
    companion object {
        const val MAIN_UI_STATE_UPDATE = "MainUiStateLiveDataWrapper#Update"
    }
    class Base(private val order: Order): FakeMainUiStateLiveDataWrapper {

        lateinit var actual: MainUiState
        override fun update(value: MainUiState) {
            order.add(MAIN_UI_STATE_UPDATE)
            actual = value
        }

        override fun check(expected: MainUiState) {
            assertEquals(expected, actual)
        }

        override fun liveData(): LiveData<MainUiState> {
            throw IllegalStateException("not used in test")
        }
    }
}