package com.example.finup.Transactions.createEdit

import androidx.lifecycle.LiveData
import com.example.finup.Transactions.createEdit.UseCases.CleanUpEmptyPeriodUseCase
import com.example.finup.Transactions.createEdit.UseCases.GetOrCreatePeriodUseCase
import com.example.finup.Transactions.model.TransactionInputDetails
import com.example.finup.core.Order
import com.example.finup.domain.Transaction
import com.example.finup.domain.TransactionRepository
import com.example.finup.domain.YearMonth
import kotlinx.coroutines.Dispatchers
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test


class CreateEditTransactionViewModelTest {

    private lateinit var order: Order
    private lateinit var viewModel: CreateEditTransactionViewModel
    private lateinit var uiStateLiveDataWrapper: FakeCreateEditUiStateWrapper
    private lateinit var transactionRepository: FakeTransactionRepository
    private lateinit var getOrCreatePeriodUseCase: FakeGetOrCreatePeriodUseCase
    private lateinit var cleanUpEmptyPeriodUseCase: FakeCleanUpEmptyPeriodUseCase

    @Before
    fun setUp() {
        order = Order()
        uiStateLiveDataWrapper = FakeCreateEditUiStateWrapper.Base(order)
        transactionRepository = FakeTransactionRepository.Base(order)
        getOrCreatePeriodUseCase = FakeGetOrCreatePeriodUseCase.Base(order)
        cleanUpEmptyPeriodUseCase = FakeCleanUpEmptyPeriodUseCase.Base(order)
        viewModel = CreateEditTransactionViewModel(
            uiStateLiveDataWrapper = uiStateLiveDataWrapper,
            repository = transactionRepository,
            getOrCreatePeriodUseCase = getOrCreatePeriodUseCase,
            cleanUpEmptyPeriodUseCase = cleanUpEmptyPeriodUseCase,
            dispatcher = Dispatchers.Unconfined,
            dispatcherMain = Dispatchers.Unconfined,
        )
    }

    @Test
    fun `init for create transaction page`() {
        viewModel.createInit(title = "Create Expense")
        uiStateLiveDataWrapper.check(
            CreateEditUiState.ShowCreateTransactionPage("Create Expense")
        )
    }

    @Test
    fun `init for edit transaction page`() {
        transactionRepository.expectedTransaction(
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
        transactionRepository.checkGetOrDeleteIsCalled(1L, "Expense")
        uiStateLiveDataWrapper.check(
            CreateEditUiState.ShowEditTransactionPage(
                title = "Edit Expense",
                selectedCategory = "Other",
                sum = "5000",
            )
        )
        order.check(listOf(GET_TRANSACTION_REPOSITORY, CREATE_EDIT_UI_STATE_UPDATE))
    }

    @Test
    fun `create transaction`() {
        getOrCreatePeriodUseCase.expectedYearMonth(YearMonth(1L, 10, 2025))
        viewModel.create(
            newInput = TransactionInputDetails(
                type = "Expense",
                selectedCategory = "Utilities",
                sum = 3000,
                day = 25,
                month = 10,
                year = 2025
            )
        )
        getOrCreatePeriodUseCase.check(expectedYear = 2025, expectedMonth = 10)
        transactionRepository.checkOtherMethodsIsCalled(
            Transaction(
                105L,
                3000, "Utilities", "Expense", 25, 1L
            )
        )
        order.check(listOf(GET_OR_CREATE_PERIOD_USE_CASE, CREATE_TRANSACTION_REPOSITORY))
    }

    @Test
    fun `edit transaction`() {
        getOrCreatePeriodUseCase.expectedYearMonth(YearMonth(5L, 12, 2025))
        viewModel.edit(
            2L,
            previousDateId = 35L, TransactionInputDetails(
                type = "Expense",
                selectedCategory = "Other",
                sum = 5000,
                day = 15,
                month = 12,
                year = 2025
            )
        )

        getOrCreatePeriodUseCase.check(expectedYear = 2025, expectedMonth = 12)
        cleanUpEmptyPeriodUseCase.check(35L)


        transactionRepository.checkOtherMethodsIsCalled(
            Transaction(
                2L,
                5000,
                "Other",
                "Expense",
                15,
                5L
            )
        )
        order.check(
            listOf(
                GET_OR_CREATE_PERIOD_USE_CASE,
                CLEAN_UP_USE_CASE,
                EDIT_TRANSACTION_REPOSITORY
            )
        )
    }

    @Test
    fun `delete transaction test`() {
        transactionRepository.expectedTransaction(
            transaction = Transaction(
                3L,
                200,
                "Transfers",
                "Expense",
                22,
                44L,
            )
        )
        viewModel.delete(transactionId = 3L, transactionType = "Expense")
        transactionRepository.checkGetOrDeleteIsCalled(expectedId = 3L, "Expense") //this is get method
        transactionRepository.checkGetOrDeleteIsCalled(expectedId = 3L, "Expense") //this is delete method
        cleanUpEmptyPeriodUseCase.check(44L)
        order.check(listOf(GET_TRANSACTION_REPOSITORY,DELETE_TRANSACTION_REPOSITORY,CLEAN_UP_USE_CASE))
    }
}

private const val CLEAN_UP_USE_CASE = "CleanUpEmptyPeriodUseCase#invoke"

private interface FakeCleanUpEmptyPeriodUseCase : CleanUpEmptyPeriodUseCase {

    fun check(expectedDateId: Long)

    class Base(private val order: Order) : FakeCleanUpEmptyPeriodUseCase {

        private var actualDateId = -1L
        override suspend fun invoke(dateId: Long) {
            actualDateId = dateId
            order.add(CLEAN_UP_USE_CASE)
        }

        override fun check(expectedDateId: Long) {
            assertEquals(expectedDateId, actualDateId)
        }
    }
}

private const val GET_OR_CREATE_PERIOD_USE_CASE = "GetOrCreatePeriodUseCase#Invoke"

private interface FakeGetOrCreatePeriodUseCase : GetOrCreatePeriodUseCase {

    fun check(expectedYear: Int, expectedMonth: Int)
    fun expectedYearMonth(yearMonth: YearMonth)

    class Base(private val order: Order) : FakeGetOrCreatePeriodUseCase {

        private lateinit var expectedYearMonth: YearMonth
        private var actualYear = 1
        private var actualMonth = 1

        override suspend fun invoke(year: Int, month: Int): YearMonth {
            actualYear = year
            actualMonth = month
            order.add(GET_OR_CREATE_PERIOD_USE_CASE)
            return expectedYearMonth
        }

        override fun expectedYearMonth(yearMonth: YearMonth) {
            expectedYearMonth = yearMonth
        }

        override fun check(expectedYear: Int, expectedMonth: Int) {
            assertEquals(expectedYear, actualYear)
            assertEquals(expectedMonth, actualMonth)
        }
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
private const val CREATE_TRANSACTION_REPOSITORY = "TransactionRepository#CreateTransaction"

private interface FakeTransactionRepository : TransactionRepository.EditAndCreate {

    fun expectedTransaction(transaction: Transaction)
    fun checkGetOrDeleteIsCalled(expectedId: Long, expectedType: String)
    fun checkOtherMethodsIsCalled(expectedTransaction: Transaction)

    class Base(private val order: Order) : FakeTransactionRepository {

        private lateinit var actualTransaction: Transaction

        private var actualId: Long = 0L
        private lateinit var actualType: String

        override suspend fun getOneTransaction(id: Long, type: String): Transaction {
            actualId = id
            actualType = type
            order.add(GET_TRANSACTION_REPOSITORY)
            return actualTransaction
        }

        override suspend fun editTransaction(
            transactionId: Long,
            sum: Int,
            name: String,
            type: String,
            day: Int,
            dateId: Long
        ) {
            actualTransaction = Transaction(transactionId, sum, name, type, day, dateId)
            order.add(EDIT_TRANSACTION_REPOSITORY)
        }

        override suspend fun createTransaction(
            sum: Int,
            name: String,
            type: String,
            day: Int,
            dateId: Long
        ): Long {
            val mockedIdForCreate = 105L
            actualTransaction = Transaction(id = mockedIdForCreate, sum, name, type, day, dateId)
            order.add(CREATE_TRANSACTION_REPOSITORY)
            return mockedIdForCreate
        }

        override suspend fun deleteTransaction(id: Long) {
            actualId = id
            actualType = actualTransaction.type
            order.add(DELETE_TRANSACTION_REPOSITORY)
        }

        override fun expectedTransaction(transaction: Transaction) {
            actualTransaction = transaction
        }

        override fun checkGetOrDeleteIsCalled(expectedId: Long, expectedType: String) {

            assertEquals(expectedId, actualId)
            assertEquals(expectedType, actualType)

        }

        override fun checkOtherMethodsIsCalled(expectedTransaction: Transaction) {
            assertEquals(expectedTransaction, actualTransaction)
        }
    }
}