package com.example.finup.Transactions.list



import com.example.finup.Transactions.core.Transaction

import com.example.finup.Transactions.core.UiState
import com.example.finup.Transactions.core.YearMonth

import com.example.finup.Transactions.list.FakeTransactionMapper.Companion.TRANSACTIONS_MAPPER
import com.example.finup.Transactions.list.FakeTransactionsListLiveDataWrapper.Companion.TRANSACTION_UPDATE_LIST_LIVEDATA
import com.example.finup.Transactions.list.useCases.GetTransactionsListByPeriodUseCase
import com.example.finup.Transactions.list.useCases.GetYearMonthPeriodUseCase
import com.example.finup.Transactions.list.useCases.NavigationMonthUseCase
import com.example.finup.Transactions.list.useCases.Result
import com.example.finup.Transactions.mappers.TransactionMappers
import com.example.finup.core.FakeNavigation
import com.example.finup.core.FakeUiStateLiveDataWrapper
import com.example.finup.core.FakeUiStateLiveDataWrapper.Companion.UI_STATE_UPDATE_LIVEDATA
import com.example.finup.core.Order

import kotlinx.coroutines.Dispatchers
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class TransactionsListViewModelTest {

    lateinit var order: Order
    lateinit var viewModel: TransactionsListViewModel
    lateinit var transactionsListWrapper: FakeTransactionsListLiveDataWrapper
    lateinit var uiStateLiveDataWrapper: FakeUiStateLiveDataWrapper
    lateinit var transactionMapper: FakeTransactionMapper
    lateinit var getYearMonthByPeriodUseCase: FakeGetYearMonthByPeriodUseCase
    lateinit var getTransactionsListByPeriodUseCase: FakeGetTransactionsListByPeriodUseCase
    lateinit var navigation: FakeNavigation
    lateinit var navigationByMonthUseCase: FakeNavigationByMonthUseCase
    @Before
    fun setUp() {
        order = Order()
        transactionsListWrapper = FakeTransactionsListLiveDataWrapper.Base(order)
        uiStateLiveDataWrapper = FakeUiStateLiveDataWrapper.Base(order)
        transactionMapper = FakeTransactionMapper.Base(order)
        getYearMonthByPeriodUseCase = FakeGetYearMonthByPeriodUseCase.Base(order)
        getTransactionsListByPeriodUseCase = FakeGetTransactionsListByPeriodUseCase.Base(order)
        navigationByMonthUseCase = FakeNavigationByMonthUseCase.Base(order)
        navigation = FakeNavigation.Base(order)

        viewModel = TransactionsListViewModel(
            transactionsListWrapper = transactionsListWrapper,
            uiStateLiveDataWrapper = uiStateLiveDataWrapper,
            transactionMapper = transactionMapper,
            getYearMonthByPeriodUseCase = getYearMonthByPeriodUseCase,
            getTransactionsListByPeriodUseCase = getTransactionsListByPeriodUseCase,
            navigationByMonthUseCase = navigationByMonthUseCase,
            navigation = navigation,
            dispatcher = Dispatchers.Unconfined,
            dispatcherMain = Dispatchers.Unconfined,
        )
    }

    @Test
    fun init() {
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
        getYearMonthByPeriodUseCase.expectedYearMonth(
            YearMonth(
                dateId = 1L,
                year = 2025,
                month = 9
            )
        )
        getTransactionsListByPeriodUseCase.expectedResult(
            Result(
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
                ),
                formattedDateYearMonth = "September 2025",
                totalSumByMonth = "10000",
            )
        )
        viewModel.init(type = "Expense")
        getYearMonthByPeriodUseCase.checkCalledTimes(1)
        getTransactionsListByPeriodUseCase.checkCalledTimes(
            YearMonth(
                dateId = 1L,
                month = 9,
                year = 2025
            ), "Expense"
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
                GET_YEAR_MONTH_USE_CASE,
                GET_TRANSACTIONS_LIST_USE_CASE,
                TRANSACTIONS_MAPPER,
                TRANSACTION_UPDATE_LIST_LIVEDATA,
                UI_STATE_UPDATE_LIVEDATA
            )
        )
    }

    @Test
    fun navigateMonth() {

        getYearMonthByPeriodUseCase.expectedYearMonth(
            YearMonth(
                dateId = 1L,
                year = 2025,
                month = 9
            )
        )

        viewModel.navigateMonth(forward = true, type = "Income")
        getYearMonthByPeriodUseCase.checkCalledTimes(1)

    }
}

private const val GET_YEAR_MONTH_USE_CASE = "GetYearMonthByPeriodUseCase#invoke"
private const val GET_TRANSACTIONS_LIST_USE_CASE = "GetTransactionsListByPeriodUseCase#invoke"

interface FakeGetTransactionsListByPeriodUseCase : GetTransactionsListByPeriodUseCase {

    fun checkCalledTimes(expectedYearMonth: YearMonth, expectedType: String)

    fun expectedResult(result: Result)

    class Base(private val order: Order) : FakeGetTransactionsListByPeriodUseCase {

        private lateinit var expectedResult: Result
        private lateinit var actualYearMonth: YearMonth
        private lateinit var actualType: String

        override suspend fun invoke(yearMonth: YearMonth, type: String): Result {
            order.add(GET_TRANSACTIONS_LIST_USE_CASE)
            return expectedResult
        }

        override fun checkCalledTimes(expectedYearMonth: YearMonth, expectedType: String) {
            assertEquals(expectedYearMonth, actualYearMonth)
            assertEquals(expectedType, actualType)
        }

        override fun expectedResult(result: Result) {
            expectedResult = result
        }
    }
}

interface FakeGetYearMonthByPeriodUseCase : GetYearMonthPeriodUseCase {

    fun expectedYearMonth(yearMonth: YearMonth)
    fun checkCalledTimes(expected: Int)

    class Base(private val order: Order) : FakeGetYearMonthByPeriodUseCase {

        private lateinit var expectedYearMonth: YearMonth
        private var actualCalledTimes = 0
        override suspend fun invoke(): YearMonth {
            order.add(GET_YEAR_MONTH_USE_CASE)
            return expectedYearMonth
        }

        override fun expectedYearMonth(yearMonth: YearMonth) {
            expectedYearMonth = yearMonth
            actualCalledTimes++
        }

        override fun checkCalledTimes(expected: Int) {
            assertEquals(expected, actualCalledTimes)
        }
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
interface FakeNavigationByMonthUseCase: NavigationMonthUseCase {

    fun expectedYearMonth()

    class Base: FakeNavigationByMonthUseCase {
        lateinit var expected: YearMonth
        private var actualForward: Boolean? = null
        private lateinit var actualCurrentMonth: YearMonth

        override suspend fun invoke(
            currentMonth: YearMonth,
            forward: Boolean
        ): YearMonth {
            actualCurrentMonth = currentMonth
            actualForward = forward
        }
    }
}