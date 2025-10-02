package com.example.finup.Transactions.list


import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.finup.R
import com.example.finup.arch.ProvideViewModel
import com.example.finup.databinding.TransactionsListPageBinding

class TransactionsListFragment : Fragment(R.layout.transactions_list_page) {

    private var _binding: TransactionsListPageBinding? = null
    private val binding get() = _binding!!
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = TransactionsListPageBinding.bind(view)
        val viewModel =
            (activity as ProvideViewModel).getViewModel(this, TransactionsListViewModel::class.java)
        viewModel.init("Expense")
        viewModel.uiStateLiveData().observe(viewLifecycleOwner) {
            binding.titleMonthTextView.text = it.title
            binding.titleSumTextView.text = it.total
        }
    }
}