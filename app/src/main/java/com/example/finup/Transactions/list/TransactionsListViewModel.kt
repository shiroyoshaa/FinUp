package com.example.finup.Transactions.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finup.Transactions.core.DateProvider
import com.example.finup.Transactions.core.TransactionRepository
import com.example.finup.Transactions.core.UiState
import com.example.finup.Transactions.core.UiStateLiveDataWrapper
import com.example.finup.Transactions.core.YearMonthRepository
import com.example.finup.Transactions.mappers.TransactionMappers
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TransactionsListViewModel(
    private val yearMonthRepository: YearMonthRepository.GetOrCreateYearMonth,
    private val transactionsRepository: TransactionRepository.ReadList,
    private val transactionsListWrapper: TransactionsListLiveDataWrapper.Update,
    private val uiStateLiveDataWrapper: UiStateLiveDataWrapper.All,
    private val transactionMapper: TransactionMappers.ToUiLayer,
    private val dateProvider: DateProvider.All,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val dispatcherMain: CoroutineDispatcher = Dispatchers.Main,

    ) : ViewModel() {

    fun init(type: String) {

        val currentYear = dateProvider.getCurrentYear()
        val currentMonth = dateProvider.getCurrentMonth()

        viewModelScope.launch(dispatcher) {
            val yearMonth = yearMonthRepository.getOrCreateYearMonth(currentMonth, currentYear)
            val transactions = transactionsRepository.getTransactions(yearMonth.dateId, type)
            withContext(dispatcherMain) {
                val mappedTransactions = transactionMapper.toUiLayer(transactions)
                transactionsListWrapper.update(mappedTransactions)
                val formattedDateYearMonth =
                    dateProvider.formatDate(yearMonth.year, yearMonth.month)
                val totalSumByMonth = transactions.sumOf { it.sum }
                uiStateLiveDataWrapper.update(
                    UiState.ShowDateTitle(
                        title = formattedDateYearMonth,
                        total = totalSumByMonth.toString()
                    )
                )
            }
        }
    }
}