package com.example.finup

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStoreOwner
import com.example.finup.arch.ProvideViewModel

class App: Application(), ProvideViewModel {
    lateinit var factory: ProvideViewModel
    override fun onCreate() {
        super.onCreate()
        factory = ProvideViewModel.Base()
    }

    override fun <T : ViewModel> getViewModel(owner: ViewModelStoreOwner, modelClass: Class<T>): T {
        return factory.getViewModel(owner,modelClass)
    }
}