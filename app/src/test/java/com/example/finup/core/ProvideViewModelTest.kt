package com.example.finup.core

import android.R.attr.order
import androidx.lifecycle.ViewModel
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import kotlin.collections.mutableListOf
import kotlin.jvm.java


class ProvideViewModelTest {
    private lateinit var factory: ViewModelFactory
    private lateinit var provideViewModel: FakeProviderViewModel

    @Before
    fun setUp() {
        provideViewModel = FakeProviderViewModel.Base()
        factory = ViewModelFactory.Base(provideViewModel)
    }

    @Test
    fun `test same view model creation`() {
        factory.viewModel(OneViewModel::class.java)
        factory.viewModel(TwoViewModel::class.java)
        factory.viewModel(OneViewModel::class.java)
        provideViewModel.check(
            listOf(
                OneViewModel::class.java,
                TwoViewModel::class.java,
            )
        )
    }

    @Test
    fun `clear second viewModel`() {
        factory.viewModel(OneViewModel::class.java)
        factory.viewModel(TwoViewModel::class.java)
        factory.clear(TwoViewModel::class.java)

        provideViewModel.check(

            listOf(
                OneViewModel::class.java,
            )
        )
    }


    @Test
    fun `create three viewModels and clear two viewModels`() {
        factory.viewModel(OneViewModel::class.java)
        factory.viewModel(TwoViewModel::class.java)
        factory.viewModel(ThreeViewModel::class.java)

        provideViewModel.check(
            listOf(
                OneViewModel::class.java,
                TwoViewModel::class.java,
                ThreeViewModel::class.java,
            )
        )
        factory.clear(OneViewModel::class.java)
        factory.clear(TwoViewModel::class.java)

        provideViewModel.check(
            listOf(
                ThreeViewModel::class.java,
            )
        )
    }
}


private interface FakeProviderViewModel : ProvideViewModel {

    fun check(expected: List<Class<out ViewModel>>)

    class Base : FakeProviderViewModel {

        private val actualList = mutableListOf<Class<out ViewModel>>()

        override fun <T : ViewModel> viewModel(viewModelClass: Class<T>): T {
            return viewModelClass.getDeclaredConstructor().newInstance()
        }

        override fun check(expected: List<Class<out ViewModel>>) {
            assertEquals(expected, actualList)
        }
    }
}

private class OneViewModel() : ViewModel()
private class TwoViewModel() : ViewModel()
private class ThreeViewModel() : ViewModel()