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
    lateinit var order: Order
    lateinit var navigation: FakeNavigation
    lateinit var uiStateLiveDataWrapper: FakeMainUiStateLiveDataWrapper
    lateinit var viewModel: MainViewModel
    lateinit var typeLiveDataWrapper:  FakeTypeLiveDataWrapper
    @Before
    fun setup() {
        order = Order()
        navigation = FakeNavigation.Base(order)
        uiStateLiveDataWrapper = FakeMainUiStateLiveDataWrapper.Base(order)
        typeLiveDataWrapper = FakeTypeLiveDataWrapper.Base()
        viewModel = MainViewModel(
            navigation = navigation,
            uiStateLiveDataWrapper = uiStateLiveDataWrapper,
            typeLiveDataWrapper = typeLiveDataWrapper,
        )
    }

    @Test
    fun `init test`() {
        viewModel.init(firstRun = true)
        typeLiveDataWrapper.check("Expense")
        navigation.check(TransactionsListScreen(type = "Expense"))
    }

    @Test
    fun `navigate to incomeList test`() {
        viewModel.navigateToIncomes()
        typeLiveDataWrapper.check("Income")
        navigation.check(TransactionsListScreen(type = "Income"))
    }

    @Test
    fun `navigate to expenseList test`(){
        viewModel.navigateToExpenses()
        typeLiveDataWrapper.check("Expense")
        navigation.check(TransactionsListScreen(type = "Expense"))
    }
    @Test
    fun `navigate to create page`() {
        viewModel.createTransaction(type = "Expense")
        uiStateLiveDataWrapper.check(MainUiState.Hide)
        navigation.check(CreateEditTransactionScreen(screenType = "Create", 0L, "Expense"))

    }
}
interface FakeTypeLiveDataWrapper: TypeLiveDataWrapper.Mutable {


    fun check(expected: String)

    class Base: FakeTypeLiveDataWrapper {

        lateinit var  actualType: String

        override fun update(value: String) {
            actualType = value
        }

        override fun check(expected: String) {
            assertEquals(expected,actualType)
        }
        override fun liveData(): LiveData<String> {
            throw IllegalStateException("not used in test")
        }
    }
}
interface FakeMainUiStateLiveDataWrapper : MainUiStateLiveDataWrapper.Mutable {

    fun check(expected: MainUiState)

    companion object {
        const val MAIN_UI_STATE_UPDATE = "MainUiStateLiveDataWrapper#Update"
    }

    class Base(private val order: Order) : FakeMainUiStateLiveDataWrapper {

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