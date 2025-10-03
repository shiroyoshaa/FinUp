package com.example.finup.Transactions.createEdit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finup.Transactions.list.TransactionsListScreen
import com.example.finup.Transactions.model.TransactionInputDetails
import com.example.finup.domain.TransactionRepository
import com.example.finup.domain.useCases.CleanUpEmptyPeriodUseCase
import com.example.finup.domain.useCases.GetOrCreatePeriodUseCase
import com.example.finup.main.MainUiState
import com.example.finup.main.MainUiStateLiveDataWrapper
import com.example.finup.main.Navigation
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CreateEditTransactionViewModel(
    private val uiStateLiveDataWrapper: CreateEditUiStateWrapper.Mutable,
    private val mainUiStateLiveDataWrapper: MainUiStateLiveDataWrapper.Update,
    private val repository: TransactionRepository.EditAndCreate,
    private val getOrCreatePeriodUseCase: GetOrCreatePeriodUseCase,
    private val cleanUpEmptyPeriodUseCase: CleanUpEmptyPeriodUseCase,
    private val navigation: Navigation.Update,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val dispatcherMain: CoroutineDispatcher = Dispatchers.Main,
) : ViewModel() {

    fun createInit(title: String) {
        uiStateLiveDataWrapper.update(CreateEditUiState.ShowCreateTransactionPage(title))
    }

    fun editInit(title: String, transactionId: Long, transactionType: String) {
        viewModelScope.launch(dispatcher) {
            val currentTransaction = repository.getOneTransaction(transactionId, transactionType)
            withContext(dispatcherMain) {
                uiStateLiveDataWrapper.update(
                    CreateEditUiState.ShowEditTransactionPage(
                        title,
                        currentTransaction.name,
                        currentTransaction.sum.toString()
                    )
                )
            }
        }
    }

    fun edit(
        transactionId: Long,
        previousDateId: Long,
        newInput: TransactionInputDetails
    ) {
        viewModelScope.launch(dispatcher) {
            val currentYearMonth = getOrCreatePeriodUseCase(
                newInput.year,
                newInput.month,
            )
            cleanUpEmptyPeriodUseCase(previousDateId)
            repository.editTransaction(
                transactionId,
                newInput.sum,
                newInput.selectedCategory,
                newInput.type,
                newInput.day,
                currentYearMonth.id
            )
        }
        navigation.update(TransactionsListScreen(type = newInput.type))
        mainUiStateLiveDataWrapper.update(MainUiState.Show)
    }

    fun create(newInput: TransactionInputDetails) {

        viewModelScope.launch(dispatcher) {

            val yearMonth = getOrCreatePeriodUseCase(newInput.year, newInput.month)

            repository.createTransaction(
                newInput.sum,
                newInput.selectedCategory,
                newInput.type,
                newInput.day,
                yearMonth.id
            )
        }
        navigation.update(TransactionsListScreen(type = newInput.type))
        mainUiStateLiveDataWrapper.update(MainUiState.Show)
    }

    fun delete(transactionId: Long, transactionType: String) {
        viewModelScope.launch(dispatcher) {
            val currentTransaction = repository.getOneTransaction(transactionId, transactionType)
            repository.deleteTransaction(transactionId)
            cleanUpEmptyPeriodUseCase(currentTransaction.dateId)
        }
        navigation.update(TransactionsListScreen(transactionType))
        mainUiStateLiveDataWrapper.update(MainUiState.Show)
    }

    fun comeback(type: String) {
        navigation.update(TransactionsListScreen(type))
        mainUiStateLiveDataWrapper.update(MainUiState.Show)
    }
    fun uiStateLiveData() = uiStateLiveDataWrapper.liveData()
}