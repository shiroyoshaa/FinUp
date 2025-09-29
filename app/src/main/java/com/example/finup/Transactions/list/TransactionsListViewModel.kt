package com.example.finup.Transactions.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finup.Transactions.core.YearMonthStateManager
import com.example.finup.Transactions.createEdit.CreateEditTransactionScreen
import com.example.finup.Transactions.list.useCases.GetTransactionsListByPeriodUseCase
import com.example.finup.Transactions.list.useCases.NavigationMonthUseCase
import com.example.finup.Transactions.mappers.TransactionMappers
import com.example.finup.main.Navigation
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TransactionsListViewModel(
    private val transactionsListWrapper: TransactionsListLiveDataWrapper.Update,
    private val uiStateLiveDataWrapper: TransactionListUiStateWrapper.Mutable,
    private val transactionMapper: TransactionMappers.ToUiLayer,
    private val getTransactionsListByPeriodUseCase: GetTransactionsListByPeriodUseCase,
    private val navigationByMonthUseCase: NavigationMonthUseCase,
    private val navigation: Navigation.Update,
    private val stateManagerWrapper: YearMonthStateManager.All,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val dispatcherMain: CoroutineDispatcher = Dispatchers.Main,
) : ViewModel() {

    fun init(type: String) {

        viewModelScope.launch(dispatcher) {

            val currentYearMonth = stateManagerWrapper.getInitialYearMonth()
            val result = getTransactionsListByPeriodUseCase(currentYearMonth, type)
            withContext(dispatcherMain) {
                transactionsListWrapper.update(transactionMapper.toUiLayer(result.listTransactions))
                uiStateLiveDataWrapper.update(
                    TransactionsListUiState.ShowDateTitle(
                        title = result.formattedDateYearMonth,
                        total = result.totalSumByMonth,
                    )
                )
            }
        }
    }

    fun navigateMonth(forward: Boolean, type: String) {

        viewModelScope.launch(dispatcher) {

            val currentYearMonth = stateManagerWrapper.getInitialYearMonth()
            val navigatedYearMonth = navigationByMonthUseCase(currentYearMonth, forward)
            stateManagerWrapper.saveYearMonthState(navigatedYearMonth)
            val result = getTransactionsListByPeriodUseCase(navigatedYearMonth, type)
            withContext(dispatcherMain) {
                transactionsListWrapper.update(transactionMapper.toUiLayer(result.listTransactions))
                uiStateLiveDataWrapper.update(
                    TransactionsListUiState.ShowDateTitle(
                        title = result.formattedDateYearMonth,
                        total = result.totalSumByMonth,
                    )
                )
            }
        }
    }

    fun editTransaction(transactionUi: DisplayItem.TransactionDetails) {

        navigation.update(CreateEditTransactionScreen(screenType = "Edit", transactionUi.id, transactionUi.type))
    }
}