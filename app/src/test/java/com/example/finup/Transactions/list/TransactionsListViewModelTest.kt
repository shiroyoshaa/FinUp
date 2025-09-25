package com.example.finup.Transactions.list


import com.example.finup.Transactions.core.FakeDateProvider
import com.example.finup.Transactions.core.FakeDateProvider.Companion.CURRENT_MONTH
import com.example.finup.Transactions.core.FakeDateProvider.Companion.CURRENT_YEAR
import com.example.finup.Transactions.core.FakeDateProvider.Companion.FORMAT_DATE
import com.example.finup.Transactions.core.Transaction
import com.example.finup.Transactions.core.TransactionRepository
import com.example.finup.Transactions.core.UiState
import com.example.finup.Transactions.core.YearMonth
import com.example.finup.Transactions.core.YearMonthRepository
import com.example.finup.Transactions.list.FakeTransactionMapper.Companion.TRANSACTIONS_MAPPER
import com.example.finup.Transactions.list.FakeTransactionsListLiveDataWrapper.Companion.TRANSACTION_UPDATE_LIST_LIVEDATA
import com.example.finup.Transactions.mappers.TransactionMappers
import com.example.finup.core.FakeUiStateLiveDataWrapper
import com.example.finup.core.FakeUiStateLiveDataWrapper.Companion.UI_STATE_UPDATE_LIVEDATA
import com.example.finup.core.Order
import kotlinx.coroutines.Dispatchers
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.util.Locale

class TransactionsListViewModelTest {

    lateinit var order: Order
    lateinit var viewModel: TransactionsListViewModel
    lateinit var yearMonthRepository: FakeYearMonthRepository
    lateinit var transactionsRepository: FakeTransactionRepository
    lateinit var transactionsListWrapper: FakeTransactionsListLiveDataWrapper
    lateinit var uiStateLiveDataWrapper: FakeUiStateLiveDataWrapper
    lateinit var dateProvider: FakeDateProvider
    lateinit var transactionMapper: FakeTransactionMapper

    @Before
    fun setUp() {
        order = Order()
        yearMonthRepository = FakeYearMonthRepository.Base(order)
        transactionsRepository = FakeTransactionRepository.Base(order)
        transactionsListWrapper = FakeTransactionsListLiveDataWrapper.Base(order)
        uiStateLiveDataWrapper = FakeUiStateLiveDataWrapper.Base(order)
        dateProvider = FakeDateProvider.Base(order, Locale.ENGLISH, 2025, 9)
        transactionMapper = FakeTransactionMapper.Base(order)
        viewModel = TransactionsListViewModel(
            yearMonthRepository = yearMonthRepository,
            transactionsRepository = transactionsRepository,
            transactionsListWrapper = transactionsListWrapper,
            uiStateLiveDataWrapper = uiStateLiveDataWrapper,
            transactionMapper = transactionMapper,
            dateProvider = dateProvider,
            dispatcher = Dispatchers.Unconfined,
            dispatcherMain = Dispatchers.Unconfined,
        )
    }

    @Test
    fun init() {

        transactionsRepository.expectTransactions(
            listOf(
                Transaction(
                    id = 3L,
                    name = "Utilities",
                    sum = 1000,
                    type = "type",
                    day = 10,
                    dateId = 1L
                ),
                Transaction(
                    id = 4L,
                    name = "Other",
                    sum = 3000,
                    type = "Expense",
                    day = 10,
                    dateId = 1L
                ),
                Transaction(
                    id = 5L,
                    name = "Groceries",
                    sum = 2000,
                    type = "Expense",
                    day = 25,
                    dateId = 1L
                ),
                Transaction(
                    id = 6L,
                    name = "Transfers",
                    sum = 4000,
                    type = "Expense",
                    day = 25,
                    dateId = 1L
                ),
            )
        )
        transactionMapper.expectedUiLayer(
            listOf(
                DisplayItem.TransactionDate(day = "10 September", "4000"),
                DisplayItem.TransactionDetails(
                    id = 3L,
                    sum = 1000,
                    name = "Utilities",
                    type = "Expense",
                    dateId = 1L
                ),
                DisplayItem.TransactionDetails(
                    id = 4L,
                    sum = 3000,
                    name = "Other",
                    type = "Expense",
                    dateId = 1L
                ),
                DisplayItem.TransactionDate(day = "25 September", "4000"),
                DisplayItem.TransactionDetails(
                    id = 5L,
                    sum = 2000,
                    name = "Groceries",
                    type = "Expense",
                    dateId = 1L
                ),
                DisplayItem.TransactionDetails(
                    id = 6L,
                    sum = 4000,
                    name = "Transfers",
                    type = "Expense",
                    dateId = 1L
                ),
            )
        )
        viewModel.init(type = "Expense")
        dateProvider.checkCurrentYearCalled(2025)
        dateProvider.checkCurrentMonthCalled(9)
        yearMonthRepository.check(YearMonth(dateId = 1L, month = 9, 2025))
        transactionsRepository.checkGetTransactionsIsCalled(
            expectedDateId = 1L,
            expectedType = "Expense"
        )

        transactionMapper.checkCalledTime(1)

        transactionsListWrapper.check(
            listOf(
                DisplayItem.TransactionDate(day = "10 September", "4000"),
                DisplayItem.TransactionDetails(
                    id = 3L,
                    sum = 1000,
                    name = "Utilities",
                    type = "Expense",
                    dateId = 1L
                ),
                DisplayItem.TransactionDetails(
                    id = 4L,
                    sum = 3000,
                    name = "Other",
                    type = "Expense",
                    dateId = 1L
                ),
                DisplayItem.TransactionDate(day = "25 September", "4000"),
                DisplayItem.TransactionDetails(
                    id = 5L,
                    sum = 2000,
                    name = "Groceries",
                    type = "Expense",
                    dateId = 1L
                ),
                DisplayItem.TransactionDetails(
                    id = 6L,
                    sum = 4000,
                    name = "Transfers",
                    type = "Expense",
                    dateId = 1L
                ),
            )
        )

        uiStateLiveDataWrapper.check(
            UiState.ShowDateTitle(
                title = "September 2025",
                total = "10000"
            )
        )
        order.check(
            listOf(
                CURRENT_YEAR,
                CURRENT_MONTH,
                GET_OR_CREATE_YEAR_MONTH,
                GET_TRANSACTIONS_REPOSITORY,
                TRANSACTIONS_MAPPER,
                TRANSACTION_UPDATE_LIST_LIVEDATA,
                FORMAT_DATE,
                UI_STATE_UPDATE_LIVEDATA,
            )
        )
    }
}

interface FakeTransactionsListLiveDataWrapper : TransactionsListLiveDataWrapper.Update {

    fun check(expected: List<DisplayItem>)

    companion object {
        const val TRANSACTION_UPDATE_LIST_LIVEDATA = "TransactionsListLiveDataWrapper#Update"
    }

    class Base(private val order: Order) : FakeTransactionsListLiveDataWrapper {

        private lateinit var actualList: List<DisplayItem>

        override fun update(value: List<DisplayItem>) {
            actualList = value
            order.add(TRANSACTION_UPDATE_LIST_LIVEDATA)
        }

        override fun check(expected: List<DisplayItem>) {
            assertEquals(expected, actualList)
        }
    }
}

private const val GET_OR_CREATE_YEAR_MONTH = "YearMonthRepository#GetOrCreateYearMonth"
private const val GET_TRANSACTIONS_REPOSITORY = "TransactionsRepository#GetTransactions"

interface FakeYearMonthRepository : YearMonthRepository.GetOrCreateYearMonth {

    fun check(expected: YearMonth)

    class Base(private val order: Order) : FakeYearMonthRepository {

        lateinit var actual: YearMonth

        private var id: Long = 1L

        override suspend fun getOrCreateYearMonth(month: Int, year: Int): YearMonth {
            actual = YearMonth(id, month, year)
            order.add(GET_OR_CREATE_YEAR_MONTH)
            return actual
        }

        override fun check(expected: YearMonth) {
            assertEquals(expected, actual)
        }
    }
}

interface FakeTransactionRepository : TransactionRepository.ReadList {

    fun expectTransactions(list: List<Transaction>)

    fun checkGetTransactionsIsCalled(expectedDateId: Long, expectedType: String)

    class Base(private val order: Order) : FakeTransactionRepository {

        private lateinit var expectTransactions: List<Transaction>
        private var actualDateId: Long = -1
        private lateinit var actualType: String

        override suspend fun getTransactions(
            dateId: Long,
            type: String
        ): List<Transaction> {

            actualDateId = dateId
            actualType = type
            order.add(GET_TRANSACTIONS_REPOSITORY)

            return expectTransactions
        }

        override fun expectTransactions(list: List<Transaction>) {
            expectTransactions = list
        }

        override fun checkGetTransactionsIsCalled(expectedDateId: Long, expectedType: String) {
            assertEquals(expectedDateId, actualDateId)
            assertEquals(expectedType, actualType)
        }
    }
}

interface FakeTransactionMapper : TransactionMappers.ToUiLayer {


    fun checkCalledTime(expectedCalledTimes: Int)

    fun expectedUiLayer(expectedDisplayItem: List<DisplayItem>)

    companion object {
        const val TRANSACTIONS_MAPPER = "TransactionMappers#ToUiLayer"
    }

    class Base(private val order: Order) : FakeTransactionMapper {

        private lateinit var mock: List<DisplayItem>
        private var actualCalledTimes: Int = 0

        override fun toUiLayer(transactions: List<Transaction>): List<DisplayItem> {
            actualCalledTimes++
            order.add(TRANSACTIONS_MAPPER)
            return mock
        }

        override fun expectedUiLayer(expectedDisplayItem: List<DisplayItem>) {
            mock = expectedDisplayItem
        }

        override fun checkCalledTime(expectedCalledTimes: Int) {
            assertEquals(expectedCalledTimes, actualCalledTimes)
        }
    }
}