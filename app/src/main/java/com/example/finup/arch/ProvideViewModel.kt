package com.example.finup.arch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.finup.Transactions.createEdit.CreateEditTransactionViewModel
import com.example.finup.Transactions.createEdit.CreateEditUiStateWrapper
import com.example.finup.Transactions.list.TransactionListUiStateWrapper
import com.example.finup.Transactions.list.TransactionsListLiveDataWrapper
import com.example.finup.Transactions.list.TransactionsListViewModel
import com.example.finup.Transactions.mappers.TransactionUiMapper
import com.example.finup.data.DataStoreManager
import com.example.finup.data.TransactionDao
import com.example.finup.data.TransactionRepositoryImpl
import com.example.finup.data.YearMonthDao
import com.example.finup.data.YearMonthRepositoryImpl
import com.example.finup.data.YearMonthStateRepositoryImpl
import com.example.finup.domain.MockProviderBase
import com.example.finup.domain.Now
import com.example.finup.domain.RealProviderBase
import com.example.finup.domain.YearMonthStateManager
import com.example.finup.domain.useCases.GetOrCreatePeriodUseCase
import com.example.finup.domain.useCases.GetTransactionsListByPeriodUseCase
import com.example.finup.domain.useCases.NavigationMonthUseCase
import com.example.finup.main.MainUiStateLiveDataWrapper
import com.example.finup.main.MainViewModel
import com.example.finup.main.Navigation
import com.example.finup.main.TypeLiveDataWrapper


interface ProvideViewModel {


    fun <T : ViewModel> getViewModel(owner: ViewModelStoreOwner, modelClass: Class<T>): T

    class Base(transactionDao: TransactionDao, yearMonthDao: YearMonthDao, now: Now,dataStoreManager: DataStoreManager) :
        ViewModelProvider.Factory, ProvideViewModel {
        private val mainUiStateLiveDataWrapper: MainUiStateLiveDataWrapper.Mutable =
            MainUiStateLiveDataWrapper.Base()
        private val createEditUiStateWrapper = CreateEditUiStateWrapper.Base()
        private val transactionListWrapper = TransactionsListLiveDataWrapper.Base()
        private val transactionListUiStateWrapper = TransactionListUiStateWrapper.Base()
        private val typeLiveDataWrapper = TypeLiveDataWrapper.Base()
        private val transactionMapper = TransactionUiMapper.Base()
        private val realDateProvider = RealProviderBase()
        private val mockDateProviderForUiTests = MockProviderBase() //for Ui Test, Please use that if you want to run Ui Tests
        private val transactionRepository = TransactionRepositoryImpl(transactionDao, now)
        private val yearMonthRepository = YearMonthRepositoryImpl(yearMonthDao, now)
        private val navigationMonthUseCase = NavigationMonthUseCase.Base(yearMonthRepository)
        private val getTransactionsListByPeriodUseCase =
            GetTransactionsListByPeriodUseCase.Base(transactionRepository, mockDateProviderForUiTests)
        private val getOrCreatePeriodUseCase = GetOrCreatePeriodUseCase.Base(yearMonthRepository)
        private val navigation = Navigation.Base()
        private val yearMonthStateRepository = YearMonthStateRepositoryImpl(dataStoreManager)
        override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
            val stateManager = YearMonthStateManager.Base(yearMonthRepository,  yearMonthStateRepository,mockDateProviderForUiTests)
            return when (modelClass) {
                MainViewModel::class.java -> MainViewModel(navigation, mainUiStateLiveDataWrapper,typeLiveDataWrapper)
                TransactionsListViewModel::class.java -> TransactionsListViewModel(
                    transactionListWrapper,
                    transactionListUiStateWrapper,
                    mainUiStateLiveDataWrapper,
                    transactionMapper,
                    getTransactionsListByPeriodUseCase,
                    navigationMonthUseCase,
                    navigation,
                    stateManager
                )

                CreateEditTransactionViewModel::class.java -> CreateEditTransactionViewModel(
                    createEditUiStateWrapper,
                    mainUiStateLiveDataWrapper,
                    transactionRepository,
                    getOrCreatePeriodUseCase,
                    navigation
                )

                else -> throw IllegalStateException("view Model doesnt exist, please watch provideVIewModel")
            } as T
        }

        override fun <T : ViewModel> getViewModel(
            owner: ViewModelStoreOwner,
            modelClass: Class<T>
        ): T {

            return ViewModelProvider(owner, this)[modelClass]
        }
    }
}