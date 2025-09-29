package com.example.finup.Transactions.createEdit

import android.R.attr.type
import androidx.lifecycle.LiveData
import com.example.finup.Transactions.core.Transaction
import com.example.finup.Transactions.core.TransactionRepository
import com.example.finup.core.Order
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test


class CreateEditTransactionViewModelTest {
    private lateinit var order: Order
    private lateinit var viewModel: CreateEditTransactionViewModel
    private lateinit var uiStateLiveDataWrapper: FakeCreateEditUiStateWrappe
    private lateinit var repository: FakeTransactionRepository

    @Before
    fun setUp() {
        order = Order()
        uiStateLiveDataWrapper = FakeCreateEditUiStateWrapper.Base(order)
        repository = FakeTransactionRepository.Base(order)

        val viewModel = CreateEditTransactionViewModel(
            uiStateLiveDataWrapper = uiStateLiveDataWrapper,
            repository = repository,
        )
    }

    @Test
    fun `init for create transaction page`() {
        viewModel.createInit(type = "Create Expense")

        uiStateLiveDataWrapper.check(CreateEditUiState.ShowCreateTransactionPage("Create Expense"))
    }

    @Test
    fun `init for edit transaction page`() {
        repository.expectedTransaction(
            Transaction(
                id = 1L,
                sum = 5000,
                name = "Other",
                type = "Expense",
                day = 15,
                dateId = 5L
            )
        )

        viewModel.editInit(title = "Edit Expense", transactionId = 1L, transactionType = "Expense")
        repository.check(1L, "Expense")
        uiStateLiveDataWrapper.check(
            CreateEditUiState.ShowEditTransactionPage(
                title = "Edit Expense",
                selectedCategory = "Other",
                sum = 5000
            )
        )
    }

    @Test
    fun `save after edit or create`() {
        viewModel.save(
            type = "Income",
            selectedCategory = "Utilities",
            day = 25,
            month = 10,
            year = 2025
        )
    }
}

private const val CREATE_EDIT_UI_STATE_UPDATE = "CreateEditUiStateWrapper#Update"

private interface FakeCreateEditUiStateWrapper : CreateEditUiStateWrapper.Mutable {

    fun check(expected: CreateEditUiState)

    class Base(private val order: Order) : FakeCreateEditUiStateWrapper {

        private lateinit var actualUiState: CreateEditUiState

        override fun update(value: CreateEditUiState) {
            actualUiState = value
            order.add(CREATE_EDIT_UI_STATE_UPDATE)
        }

        override fun liveData(): LiveData<CreateEditUiState> {
            throw IllegalStateException("not used in test")
        }

        override fun check(expectedUiState: CreateEditUiState) {
            assertEquals(expectedUiState, actualUiState)
        }
    }
}

private const val GET_TRANSACTION_REPOSITORY = "TransactionRepository#GetTransaction"
private const val DELETE_TRANSACTION_REPOSITORY = "TransactionRepository#DeleteTransaction"
private const val EDIT_TRANSACTION_REPOSITORY = "TransactionRepository#DeleteTransaction"



private interface FakeTransactionRepository : TransactionRepository.Edit {

    fun expectedTransaction(transaction: Transaction)
    fun check(expectedId: Long, expectedType: String)

    class Base(private val order: Order) : FakeTransactionRepository {

        private lateinit var mockedTransaction: Transaction

        private var actualId: Long = 1000L
        private var actualType: String? = "mocked"

        override suspend fun getOneTransaction(id: Long, type: String): Transaction {
            actualId = id
            actualType = type
            order.add(GET_TRANSACTION_REPOSITORY)
            return mockedTransaction
        }

        override suspend fun editTransaction(
            transactionId: Long,
            sum: Int,
            name: String,
            type: String,
            day: Int,
            dateId: Long
        ) {
            actualId = transactionId
            actualType = type
            mockedTransaction = Transaction(transactionId,sum,name,type,day,dateId)
            order.add(EDIT_TRANSACTION_REPOSITORY)
            }


        override suspend fun deleteTransaction(id: Long) {
            actualId = id
            order.add(DELETE_TRANSACTION_REPOSITORY)
        }

        override fun expectedTransaction(transaction: Transaction) {
            mockedTransaction = transaction
        }

        override fun check(expectedId: Long, expectedType: String) {

            assertEquals(expectedId, actualId)
            assertEquals(expectedType, actualType)

        }
    }
}