package com.example.finup

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStoreOwner
import com.example.finup.arch.Core
import com.example.finup.arch.ProvideViewModel
import com.example.finup.domain.Now

class App: Application(), ProvideViewModel {
    lateinit var factory: ProvideViewModel
    lateinit var core: Core
    lateinit var now: Now
    override fun onCreate() {
        super.onCreate()
        core = Core(applicationContext)
        now = Now.Base()
        factory = ProvideViewModel.Base(core.transactionDao(),core.yearMonthDao(),now)
    }

    override fun <T : ViewModel> getViewModel(owner: ViewModelStoreOwner, modelClass: Class<T>): T {
        return factory.getViewModel(owner,modelClass)
    }
}