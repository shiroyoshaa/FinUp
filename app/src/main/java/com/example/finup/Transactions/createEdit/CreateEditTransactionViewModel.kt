package com.example.finup.Transactions.createEdit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finup.Transactions.createEdit.UseCases.CleanUpEmptyPeriodUseCase
import com.example.finup.Transactions.createEdit.UseCases.GetOrCreatePeriodUseCase
import com.example.finup.Transactions.model.TransactionInputDetails
import com.example.finup.domain.TransactionRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CreateEditTransactionViewModel(
    private val uiStateLiveDataWrapper: CreateEditUiStateWrapper.Mutable,
    private val repository: TransactionRepository.EditAndCreate,
    private val getOrCreatePeriodUseCase: GetOrCreatePeriodUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val dispatcherMain: CoroutineDispatcher = Dispatchers.Main,
    private val cleanUpEmptyPeriodUseCase: CleanUpEmptyPeriodUseCase,
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
    }

    fun delete(transactionId: Long, transactionType: String) {
        viewModelScope.launch(dispatcher) {
            val currentTransaction = repository.getOneTransaction(transactionId, transactionType)
            repository.deleteTransaction(transactionId)
            cleanUpEmptyPeriodUseCase(currentTransaction.dateId)
        }
    }
}