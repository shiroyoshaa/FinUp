package com.example.finup.Transactions.list


import androidx.lifecycle.LiveData
import com.example.finup.Transactions.createEdit.CreateEditTransactionScreen
import com.example.finup.Transactions.list.FakeTransactionMapper.Companion.TRANSACTIONS_MAPPER
import com.example.finup.Transactions.list.FakeTransactionsListLiveDataWrapper.Companion.TRANSACTION_UPDATE_LIST_LIVEDATA
import com.example.finup.Transactions.list.FakeUiStateLiveDataWrapper.Companion.UI_STATE_UPDATE_LIVEDATA
import com.example.finup.Transactions.list.FakeYearMonthStateManager.Companion.GET_YEAR_MONTH_MANAGER
import com.example.finup.Transactions.list.FakeYearMonthStateManager.Companion.SAVE_YEAR_MONTH_MANAGER
import com.example.finup.Transactions.mappers.TransactionMappers
import com.example.finup.Transactions.model.DisplayItemUi
import com.example.finup.core.FakeNavigation
import com.example.finup.core.FakeNavigation.Companion.NAVIGATION
import com.example.finup.core.Order
import com.example.finup.domain.Result
import com.example.finup.domain.Transaction
import com.example.finup.domain.YearMonth
import com.example.finup.domain.YearMonthStateManager
import com.example.finup.domain.useCases.GetTransactionsListByPeriodUseCase
import com.example.finup.domain.useCases.NavigationMonthUseCase
import junit.framework.TestCase
import kotlinx.coroutines.Dispatchers
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class TransactionsListViewModelTest {

    private lateinit var order: Order
    private lateinit var viewModel: TransactionsListViewModel
    private lateinit var transactionsListWrapper: FakeTransactionsListLiveDataWrapper
    private lateinit var uiStateLiveDataWrapper: FakeUiStateLiveDataWrapper
    private lateinit var transactionMapper: FakeTransactionMapper
    private lateinit var getTransactionsListByPeriodUseCase: FakeGetTransactionsListByPeriodUseCase
    private lateinit var navigation: FakeNavigation
    private lateinit var navigationByMonthUseCase: FakeNavigationByMonthUseCase
    private lateinit var yearMonthStateManager: FakeYearMonthStateManager

    @Before
    fun setUp() {

        order = Order()
        transactionsListWrapper = FakeTransactionsListLiveDataWrapper.Base(order)
        uiStateLiveDataWrapper = FakeUiStateLiveDataWrapper.Base(order)
        transactionMapper = FakeTransactionMapper.Base(order)
        getTransactionsListByPeriodUseCase = FakeGetTransactionsListByPeriodUseCase.Base(order)
        navigationByMonthUseCase = FakeNavigationByMonthUseCase.Base(order)
        navigation = FakeNavigation.Base(order)
        yearMonthStateManager = FakeYearMonthStateManager.Base(order)

        viewModel = TransactionsListViewModel(
            transactionsListWrapper = transactionsListWrapper,
            uiStateLiveDataWrapper = uiStateLiveDataWrapper,
            transactionMapper = transactionMapper,
            getTransactionsListByPeriodUseCase = getTransactionsListByPeriodUseCase,
            navigationByMonthUseCase = navigationByMonthUseCase,
            navigation = navigation,
            stateManagerWrapper = yearMonthStateManager,
            dispatcher = Dispatchers.Unconfined,
            dispatcherMain = Dispatchers.Unconfined,
        )
    }

    @Test
    fun init() {
        yearMonthStateManager.expectedSavedYearMonth(yearMonth = YearMonth(id = 1L, month = 9,year = 2025))
        getTransactionsListByPeriodUseCase.expectedResult(
            Result(
                listOf(
                    Transaction(
                        id = 3L,
                        name = "Utilities",
                        sum = 1000,
                        type = "Expense",
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
        transactionMapper.expectedUiLayer(
            listOf(
                DisplayItemUi.TransactionDate(day = "10 September", "4000"),
                DisplayItemUi.TransactionDetails(
                    id = 3L,
                    sum = 1000,
                    name = "Utilities",
                    type = "Expense",
                    dateId = 1L
                ),
                DisplayItemUi.TransactionDetails(
                    id = 4L,
                    sum = 3000,
                    name = "Other",
                    type = "Expense",
                    dateId = 1L
                ),
                DisplayItemUi.TransactionDate(day = "25 September", "6000"),
                DisplayItemUi.TransactionDetails(
                    id = 5L,
                    sum = 2000,
                    name = "Groceries",
                    type = "Expense",
                    dateId = 1L
                ),
                DisplayItemUi.TransactionDetails(
                    id = 6L,
                    sum = 4000,
                    name = "Transfers",
                    type = "Expense",
                    dateId = 1L
                ),
            )
        )

        viewModel.init(type = "Expense")
        yearMonthStateManager.checkGetCalledTimes(1)

        getTransactionsListByPeriodUseCase.checkCalledTimes(
            YearMonth(
                id = 1L,
                month = 9,
                year = 2025
            ), "Expense"
        )
        transactionMapper.checkCalledTime(1)

        transactionsListWrapper.check(
            listOf(
                DisplayItemUi.TransactionDate(day = "10 September", "4000"),
                DisplayItemUi.TransactionDetails(
                    id = 3L,
                    sum = 1000,
                    name = "Utilities",
                    type = "Expense",
                    dateId = 1L
                ),
                DisplayItemUi.TransactionDetails(
                    id = 4L,
                    sum = 3000,
                    name = "Other",
                    type = "Expense",
                    dateId = 1L
                ),
                DisplayItemUi.TransactionDate(day = "25 September", "6000"),
                DisplayItemUi.TransactionDetails(
                    id = 5L,
                    sum = 2000,
                    name = "Groceries",
                    type = "Expense",
                    dateId = 1L
                ),
                DisplayItemUi.TransactionDetails(
                    id = 6L,
                    sum = 4000,
                    name = "Transfers",
                    type = "Expense",
                    dateId = 1L
                ),
            )
        )

        uiStateLiveDataWrapper.check(
            TransactionsListUiState.ShowDateTitle(
                title = "September 2025",
                total = "10000"
            )
        )
        order.check(
            listOf(
                GET_YEAR_MONTH_MANAGER,
                GET_TRANSACTIONS_LIST_USE_CASE,
                TRANSACTIONS_MAPPER,
                TRANSACTION_UPDATE_LIST_LIVEDATA,
                UI_STATE_UPDATE_LIVEDATA
            )
        )
    }

    @Test
    fun `month navigation for toolBar`() {

        yearMonthStateManager.expectedSavedYearMonth(YearMonth(1L, 9, 2025))
        navigationByMonthUseCase.expectedYearMonth(YearMonth(2L, 10, 2025))

        getTransactionsListByPeriodUseCase.expectedResult(
            Result(
                listOf(
                    Transaction(
                        id = 6L,
                        name = "Kaspi Bank",
                        sum = 1000,
                        type = "Income",
                        day = 23,
                        dateId = 2L
                    ),
                    Transaction(
                        id = 7L,
                        name = "BCC Bank",
                        sum = 3000,
                        type = "Income",
                        day = 1,
                        dateId = 2L
                    ),
                    Transaction(
                        id = 8L,
                        name = "Kaspi Bank",
                        sum = 2000,
                        type = "Income",
                        day = 5,
                        dateId = 2L
                    ),
                ),
                formattedDateYearMonth = "October 2025",
                totalSumByMonth = "6000",
            )
        )
        transactionMapper.expectedUiLayer(
            listOf(
                DisplayItemUi.TransactionDate(day = "1 October", "3000"),
                DisplayItemUi.TransactionDetails(
                    id = 7L,
                    sum = 3000,
                    name = "BCC Bank",
                    type = "Income",
                    dateId = 2L
                ),
                DisplayItemUi.TransactionDate(day = "5 October", "2000"),
                DisplayItemUi.TransactionDetails(
                    id = 5L,
                    sum = 2000,
                    name = "Kaspi Bank",
                    type = "Income",
                    dateId = 1L
                ),
                DisplayItemUi.TransactionDate(day = "23 October", "1000"),
                DisplayItemUi.TransactionDetails(
                    id = 5L,
                    sum = 1000,
                    name = "Kaspi Bank",
                    type = "Income",
                    dateId = 1L
                ),
            )
        )
        viewModel.navigateMonth(forward = true, type = "Income")

        yearMonthStateManager.checkGetCalledTimes(expectedGetCalledTimes = 1)
        navigationByMonthUseCase.check(true, expectedCurrentYearMonth = YearMonth(1L, 9, 2025))
        yearMonthStateManager.checkSaveIsCalled(
            expectedYearMonth = YearMonth(
                id = 2L,
                10,
                2025
            )
        )

        getTransactionsListByPeriodUseCase.checkCalledTimes(YearMonth(2L, 10, 2025), "Income")
        transactionMapper.checkCalledTime(1)

        transactionsListWrapper.check(
            listOf(
                DisplayItemUi.TransactionDate(day = "1 October", "3000"),
                DisplayItemUi.TransactionDetails(
                    id = 7L,
                    sum = 3000,
                    name = "BCC Bank",
                    type = "Income",
                    dateId = 2L
                ),
                DisplayItemUi.TransactionDate(day = "5 October", "2000"),
                DisplayItemUi.TransactionDetails(
                    id = 5L,
                    sum = 2000,
                    name = "Kaspi Bank",
                    type = "Income",
                    dateId = 1L
                ),
                DisplayItemUi.TransactionDate(day = "23 October", "1000"),
                DisplayItemUi.TransactionDetails(
                    id = 5L,
                    sum = 1000,
                    name = "Kaspi Bank",
                    type = "Income",
                    dateId = 1L
                ),
            )
        )

        uiStateLiveDataWrapper.check(
            TransactionsListUiState.ShowDateTitle(
                title = "October 2025",
                total = "6000"
            )
        )
        order.check(
            listOf(
                GET_YEAR_MONTH_MANAGER,
                NAVIGATE_BY_MONTH_USE_CASE,
                SAVE_YEAR_MONTH_MANAGER,
                GET_TRANSACTIONS_LIST_USE_CASE,
                TRANSACTIONS_MAPPER,
                TRANSACTION_UPDATE_LIST_LIVEDATA,
                UI_STATE_UPDATE_LIVEDATA
            )
        )
    }
    @Test
    fun `navigating to edit transaction page`(){
        viewModel.editTransaction(transactionUi = DisplayItemUi.TransactionDetails(id = 2L,sum = 2000,type = "Income", name = "Other", dateId = 10L))
        navigation.check(CreateEditTransactionScreen(screenType = "Edit", 2L, "Income"))
        order.check(listOf(NAVIGATION))
    }
}
private interface FakeUiStateLiveDataWrapper: TransactionListUiStateWrapper.Mutable {

    fun check(expectedUiState: TransactionsListUiState)

    companion object {
        const val UI_STATE_UPDATE_LIVEDATA = "UiStateLiveDataWrapper#Update"
    }

    class Base(private val order: Order): FakeUiStateLiveDataWrapper {

        lateinit var actualUiState: TransactionsListUiState

        override fun update(value: TransactionsListUiState) {
            actualUiState = value
            order.add(UI_STATE_UPDATE_LIVEDATA)
        }

        override fun liveData(): LiveData<TransactionsListUiState> {
            throw IllegalStateException("not used in test")
        }

        override fun check(expectedUiState: TransactionsListUiState) {
            TestCase.assertEquals(expectedUiState, actualUiState)
        }
    }
}

private const val GET_TRANSACTIONS_LIST_USE_CASE = "GetTransactionsListByPeriodUseCase#invoke"

private interface FakeGetTransactionsListByPeriodUseCase : GetTransactionsListByPeriodUseCase {

    fun checkCalledTimes(expectedYearMonth: YearMonth, expectedType: String)

    fun expectedResult(result: Result)

    class Base(private val order: Order) : FakeGetTransactionsListByPeriodUseCase {

        private lateinit var expectedResult: Result
        private lateinit var actualYearMonth: YearMonth
        private lateinit var actualType: String

        override suspend fun invoke(yearMonth: YearMonth, type: String): Result {
            order.add(GET_TRANSACTIONS_LIST_USE_CASE)
            actualYearMonth = yearMonth
            actualType = type
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

private interface FakeTransactionsListLiveDataWrapper : TransactionsListLiveDataWrapper.UpdateList {

    fun check(expected: List<DisplayItemUi>)

    companion object {
        const val TRANSACTION_UPDATE_LIST_LIVEDATA = "TransactionsListLiveDataWrapper#Update"
    }

    class Base(private val order: Order) : FakeTransactionsListLiveDataWrapper {

        private lateinit var actualList: List<DisplayItemUi>

        override fun update(value: List<DisplayItemUi>) {
            actualList = value
            order.add(TRANSACTION_UPDATE_LIST_LIVEDATA)
        }

        override fun check(expected: List<DisplayItemUi>) {
            assertEquals(expected, actualList)
        }
    }
}


private interface FakeTransactionMapper : TransactionMappers.ToUiLayer {


    fun checkCalledTime(expectedCalledTimes: Int)

    fun expectedUiLayer(expectedDisplayItem: List<DisplayItemUi>)

    companion object {
        const val TRANSACTIONS_MAPPER = "TransactionMappers#ToUiLayer"
    }

    class Base(private val order: Order) : FakeTransactionMapper {

        private lateinit var mock: List<DisplayItemUi>
        private var actualCalledTimes: Int = 0

        override fun toUiLayer(transactions: List<Transaction>): List<DisplayItemUi> {
            actualCalledTimes++
            order.add(TRANSACTIONS_MAPPER)
            return mock
        }

        override fun expectedUiLayer(expectedDisplayItem: List<DisplayItemUi>) {
            mock = expectedDisplayItem
        }

        override fun checkCalledTime(expectedCalledTimes: Int) {
            assertEquals(expectedCalledTimes, actualCalledTimes)
        }
    }
}

private const val NAVIGATE_BY_MONTH_USE_CASE = "NavigationMonthUseCase#invoke"

private interface FakeNavigationByMonthUseCase : NavigationMonthUseCase {

    fun expectedYearMonth(mock: YearMonth)
    fun check(expectedForward: Boolean, expectedCurrentYearMonth: YearMonth)

    class Base(private val order: Order) : FakeNavigationByMonthUseCase {

        private lateinit var mockYearMonth: YearMonth
        private var actualForward: Boolean? = null

        private lateinit var actualCurrentMonth: YearMonth

        override suspend fun invoke(

            currentMonth: YearMonth,
            forward: Boolean
        ): YearMonth {
            actualForward = forward
            actualCurrentMonth = currentMonth
            order.add(NAVIGATE_BY_MONTH_USE_CASE)
            return mockYearMonth
        }

        override fun expectedYearMonth(mock: YearMonth) {
            mockYearMonth = mock
        }

        override fun check(expectedForward: Boolean, expectedCurrentYearMonth: YearMonth) {
            assertEquals(expectedForward, actualForward)
            assertEquals(expectedCurrentYearMonth, actualCurrentMonth)
        }

    }
}

private interface FakeYearMonthStateManager : YearMonthStateManager.All {

    fun checkGetCalledTimes(expectedGetCalledTimes: Int)

    fun checkSaveIsCalled(expectedYearMonth: YearMonth)
    fun expectedSavedYearMonth(yearMonth: YearMonth)

    companion object {
        const val GET_YEAR_MONTH_MANAGER = "YearMonthStateManager#getInitialYearMonth"
        const val SAVE_YEAR_MONTH_MANAGER = "YearMonthStateManager#saveYearMonthState"
    }

    class Base(private val order: Order) : FakeYearMonthStateManager {

        private var actualGetCalledTimes: Int = 0

        private lateinit var mock: YearMonth
        private lateinit var savedYearMonth: YearMonth

        override suspend fun getInitialYearMonth(): YearMonth {
            order.add(GET_YEAR_MONTH_MANAGER)
            actualGetCalledTimes++
            return mock
        }

        override fun saveYearMonthState(yearMonth: YearMonth) {
            order.add(SAVE_YEAR_MONTH_MANAGER)
            savedYearMonth = yearMonth
        }

        override fun expectedSavedYearMonth(yearMonth: YearMonth) {
            mock = yearMonth
        }

        override fun checkGetCalledTimes(expectedGetCalledTimes: Int) {
            assertEquals(expectedGetCalledTimes, actualGetCalledTimes)
        }

        override fun checkSaveIsCalled(expectedYearMonth: YearMonth) {
            assertEquals(expectedYearMonth, savedYearMonth)
        }
    }
}
