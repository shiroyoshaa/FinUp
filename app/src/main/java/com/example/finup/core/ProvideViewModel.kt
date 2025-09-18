package com.example.finup.core

import androidx.lifecycle.ViewModel

interface ProvideViewModel {
    fun <T : ViewModel> viewModel(viewModelClass: Class<T>): T

    class Base: ProvideViewModel{
        override fun <T : ViewModel> viewModel(viewModelClass: Class<T>): T {
            return  viewModelClass.getDeclaredConstructor().newInstance()
        }
    }
}