package com.example.finup.main

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStoreOwner
import com.example.finup.R
import com.example.finup.arch.ProvideViewModel
import com.example.finup.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), ProvideViewModel {
    private lateinit var viewModel: MainViewModel
    lateinit var binding: ActivityMainBinding
    private var type = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        viewModel = getViewModel(this,MainViewModel::class.java)
        viewModel.init(savedInstanceState == null)
        binding.addFloatingButton.setOnClickListener {
            viewModel.createTransaction(type)
        }

        binding.bottomNav.setOnItemSelectedListener {  item ->
            when (item.itemId) {
                R.id.incomeIcon -> {
                    viewModel.navigateToIncomes()
                    true
                }
                R.id.expenseIcon -> {
                    viewModel.navigateToExpenses()
                    true
                }
                else -> false
            }
        }
        viewModel.navigationLiveData().observe(this) {
            it.apply(supportFragmentManager,binding.container.id)
        }
        viewModel.uiStateLiveData().observe(this) {
            it.apply(binding.bottomNav,binding.addFloatingButton)
        }
        viewModel.typeLiveData().observe(this) {
            type = it
        }
    }

    override fun <T : ViewModel> getViewModel(owner: ViewModelStoreOwner, modelClass: Class<T>): T {
        return (application as ProvideViewModel).getViewModel(owner,modelClass)
    }
}