package com.example.finup.core

import androidx.lifecycle.ViewModel

interface ViewModelFactory: ProvideViewModel, ClearViewModel {

    class Base(private val provideViewModel: ProvideViewModel): ViewModelFactory {
        private val map = mutableMapOf<Class<out ViewModel>, ViewModel>()

        override fun <T : ViewModel> viewModel(viewModelClass: Class<T>): T {
            if(map.containsKey(viewModelClass)) {
                return map[viewModelClass] as T
            } else {
                val viewModel = provideViewModel.viewModel(viewModelClass)
                map[viewModelClass] = viewModel
                return viewModel
            }
         }

        override fun clear(viewModelClass: Class<out ViewModel>) {
            map.remove(viewModelClass)
        }
    }
}