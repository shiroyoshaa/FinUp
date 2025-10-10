package com.example.finup.Transactions.createEdit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.finup.Transactions.list.TransactionsListScreen
import com.example.finup.core.FakeNavigation
import com.example.finup.core.FakeNavigation.Companion.NAVIGATION
import com.example.finup.core.Order
import com.example.finup.domain.DateProvider
import com.example.finup.domain.models.Transaction
import com.example.finup.domain.models.YearMonth
import com.example.finup.domain.repositories.TransactionRepository
import com.example.finup.domain.useCases.GetOrCreatePeriodUseCase
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
    private lateinit var navigation: FakeNavigation
    private lateinit var selectedStateLiveDataWrapper: FakeSelectedStateLiveDataWrapper
    private lateinit var dateProvider: FakeDateProvider

    @Before
    fun setUp() {
        order = Order()
        uiStateLiveDataWrapper = FakeCreateEditUiStateWrapper.Base(order)
        transactionRepository = FakeTransactionRepository.Base(order)
        getOrCreatePeriodUseCase = FakeGetOrCreatePeriodUseCase.Base(order)
        navigation = FakeNavigation.Base(order)
        selectedStateLiveDataWrapper = FakeSelectedStateLiveDataWrapper.Base(order)
        dateProvider = FakeDateProvider.Base(order)
        viewModel = CreateEditTransactionViewModel(
            uiStateLiveDataWrapper = uiStateLiveDataWrapper,
            repository = transactionRepository,
            getOrCreatePeriodUseCase = getOrCreatePeriodUseCase,
            navigation = navigation,
            selectedStateLiveDataWrapper,
            dateProvider = dateProvider,
            dispatcher = Dispatchers.Unconfined,
            dispatcherMain = Dispatchers.Unconfined,
        )
    }

    @Test
    fun `init for create transaction page test`() {
        viewModel.createInit(title = "Create Expense")
        uiStateLiveDataWrapper.check(
            CreateEditUiState.ShowCreateTransactionPage("Create Expense")
        )
    }

    @Test
    fun `init for edit transaction page test`() {
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
        getOrCreatePeriodUseCase.expectedYearMonth(YearMonth(5L, 10, 2025))
        viewModel.editInit(title = "Edit Expense", transactionId = 1L, transactionType = "Expense")

        transactionRepository.checkGetOrDeleteIsCalled(
            1L,
            "Expense"
        )//this is get transaction method
        getOrCreatePeriodUseCase.checkGetByIdIsCalled(1)
        selectedStateLiveDataWrapper.checkUpdateIsCalled(
            SelectedStateUi(
                selectedCategory = "Other",
                sum = 5000,
                day = 15,
                month = 10,
                year = 2025,
            )
        )
        uiStateLiveDataWrapper.check(
            CreateEditUiState.ShowEditTransactionPage(
                title = "Edit Expense",
                sum = "5000",
            )
        )
        order.check(
            listOf(
                GET_TRANSACTION_REPOSITORY,
                GET_BY_ID,
                UPDATE,
                CREATE_EDIT_UI_STATE_UPDATE
            )
        )
    }

    @Test
    fun `create transaction test`() {
        var mockedLiveData = MutableLiveData(SelectedStateUi("Utilities",3000,25,10,2025))
        selectedStateLiveDataWrapper.mockingLiveData(mockedLiveData)
        getOrCreatePeriodUseCase.expectedYearMonth(YearMonth(1L, 10, 2025))

        viewModel.create(transactionType = "Expense")

        getOrCreatePeriodUseCase.checkInvokeIsCalled(expectedYear = 2025, expectedMonth = 10)
        transactionRepository.checkOtherMethodsIsCalled(
            Transaction(
                105L,
                3000, "Utilities", "Expense", 25, 1L
            )
        )//this is create transaction method
        navigation.check(TransactionsListScreen)
        order.check(
            listOf(
                LIVE_DATA,
                GET_OR_CREATE_PERIOD_USE_CASE,
                CREATE_TRANSACTION_REPOSITORY,
                NAVIGATION,
            )
        )
    }

    @Test
    fun `edit transaction test`() {
        val mockLiveData = MutableLiveData(SelectedStateUi(selectedCategory = "Other",5000,15,12,2025))
        selectedStateLiveDataWrapper.mockingLiveData(mockLiveData)
        getOrCreatePeriodUseCase.expectedYearMonth(YearMonth(5L, 12, 2025))

        viewModel.edit(
            2L, transactionType = "Expense"
        )
        getOrCreatePeriodUseCase.checkInvokeIsCalled(expectedYear = 2025, expectedMonth = 12)
        transactionRepository.checkOtherMethodsIsCalled(
            Transaction(
                2L,
                5000,
                "Other",
                "Expense",
                15,
                5L
            ) //method - edit transaction
        )


        navigation.check(TransactionsListScreen)
        order.check(
            listOf(
                LIVE_DATA,
                GET_OR_CREATE_PERIOD_USE_CASE,
                EDIT_TRANSACTION_REPOSITORY,
                NAVIGATION,
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
        viewModel.delete(transactionId = 3L)
        transactionRepository.checkGetOrDeleteIsCalled(
            expectedId = 3L,
            "Expense"
        ) //this is delete method
        navigation.check(TransactionsListScreen)
        order.check(
            listOf(
                DELETE_TRANSACTION_REPOSITORY,
                NAVIGATION,
            )
        )
    }

    @Test
    fun `arrow back press`() {
        viewModel.comeback()
        navigation.check(TransactionsListScreen)
    }

    @Test
    fun`select category test`() {
        viewModel.selectCategory(category = "Kaspi Bank")
        selectedStateLiveDataWrapper.checkUpdateSelectedCategoryIsCalled("Kaspi Bank")
    }

    @Test
    fun`select date test`() {
        dateProvider.mockDate(12,10,2025)
        viewModel.selectDate(date = 1728662400000)
        dateProvider.check(1728662400000)
        selectedStateLiveDataWrapper.checkUpdateSelectedDateIsCalled("12.10.2025")
    }
}

private const val FORMAT_LONG_TO_DATE_COMPONENTS = "DateProvider#FormatLongToDateComponents"

private interface FakeDateProvider : DateProvider.FormatLongToDateComponents {

    fun mockDate(day: Int, month: Int, year: Int)
    fun check(expectedCalledDate: Long)

    class Base(private val order: Order) : FakeDateProvider {

        private var mockedDay = 0
        private var mockedMonth = 0
        private var mockedYear = 0
        private var actualFormatCalledValue = 0L

        override fun formatLongToDateComponents(date: Long): Triple<Int, Int, Int> {
            actualFormatCalledValue = date
            order.add(FORMAT_LONG_TO_DATE_COMPONENTS)
            return Triple(mockedDay, mockedMonth, mockedYear)
        }

        override fun mockDate(day: Int, month: Int, year: Int) {
            mockedDay = day
            mockedMonth = month
            mockedYear = year
        }

        override fun check(expectedCalledDate: Long) {
            assertEquals(expectedCalledDate, actualFormatCalledValue)
        }
    }
}

private const val LIVE_DATA = "SelectedStateWrapper#LiveData"
private const val UPDATE = "SelectedStateWrapper#Update"

private interface FakeSelectedStateLiveDataWrapper : SelectedStateWrapper.Mutable {

    fun mockingLiveData(expectedLiveData: LiveData<SelectedStateUi>)
    fun checkUpdateSumIsCalled(expectedSum: Int)
    fun checkLiveDataIsCalled(expected: Int)
    fun checkUpdateSelectedDateIsCalled(expectedDate: String)
    fun checkUpdateSelectedCategoryIsCalled(expectedCategory: String)
    fun checkUpdateIsCalled(expected: SelectedStateUi)
    class Base(private val order: Order) : FakeSelectedStateLiveDataWrapper {

        private lateinit var mockedLiveData: LiveData<SelectedStateUi>
        private lateinit var actual: SelectedStateUi
        private var actualSum = 0
        private lateinit var actualSelectedCategory: String
        private lateinit var actualSelectedDate: String
        private var liveDataCalledTimes = 0
        override fun update(value: SelectedStateUi) {
            order.add(UPDATE)
            actual = value
        }

        override fun liveData(): LiveData<SelectedStateUi> {
            liveDataCalledTimes++
            order.add(LIVE_DATA)
            return mockedLiveData
        }

        override fun updateSum(value: Int) {
            actualSum = value
        }

        override fun updateSelectedDate(day: Int, month: Int, year: Int) {
            actualSelectedDate = "$day.$month.$year"
        }

        override fun updateSelectedCategory(category: String) {
            actualSelectedCategory = category
        }

        override fun mockingLiveData(expectedLiveData: LiveData<SelectedStateUi>) {
            mockedLiveData = expectedLiveData
        }

        override fun checkUpdateSumIsCalled(expectedSum: Int) {
            assertEquals(expectedSum, actualSum)
        }

        override fun checkLiveDataIsCalled(expected: Int) {
            assertEquals(expected, liveDataCalledTimes)
        }

        override fun checkUpdateSelectedDateIsCalled(expectedDate: String) {
            assertEquals(expectedDate, actualSelectedDate)
        }

        override fun checkUpdateSelectedCategoryIsCalled(expectedCategory: String) {
            assertEquals(expectedCategory, actualSelectedCategory)
        }

        override fun checkUpdateIsCalled(expected: SelectedStateUi) {
            assertEquals(actual, expected)
        }
    }
}

private const val GET_OR_CREATE_PERIOD_USE_CASE = "GetOrCreatePeriodUseCase#Invoke"
private const val GET_BY_ID = "GetOrCreatePeriodUseCase#GetById"

private interface FakeGetOrCreatePeriodUseCase : GetOrCreatePeriodUseCase {

    fun checkInvokeIsCalled(expectedYear: Int, expectedMonth: Int)
    fun checkGetByIdIsCalled(calledTimes: Int)
    fun expectedYearMonth(yearMonth: YearMonth)

    class Base(private val order: Order) : FakeGetOrCreatePeriodUseCase {

        private lateinit var expectedYearMonth: YearMonth
        private var actualYear = 1
        private var actualMonth = 1
        private var getByIdCalledTimes = 0
        override suspend fun invoke(year: Int, month: Int): YearMonth {
            actualYear = year
            actualMonth = month
            order.add(GET_OR_CREATE_PERIOD_USE_CASE)
            return expectedYearMonth
        }

        override suspend fun getById(id: Long): YearMonth {
            getByIdCalledTimes++
            order.add(GET_BY_ID)
            return expectedYearMonth
        }

        override fun expectedYearMonth(yearMonth: YearMonth) {
            expectedYearMonth = yearMonth
        }

        override fun checkInvokeIsCalled(expectedYear: Int, expectedMonth: Int) {
            assertEquals(expectedYear, actualYear)
            assertEquals(expectedMonth, actualMonth)
        }

        override fun checkGetByIdIsCalled(calledTimes: Int) {
            assertEquals(calledTimes, getByIdCalledTimes)
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
private const val EDIT_TRANSACTION_REPOSITORY = "TransactionRepository#EditTransaction"
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