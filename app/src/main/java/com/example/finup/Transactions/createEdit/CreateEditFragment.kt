package com.example.finup.Transactions.createEdit

import android.icu.util.Calendar
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import com.example.finup.R
import com.example.finup.Transactions.model.TransactionInputDetails
import com.example.finup.arch.ProvideViewModel
import com.example.finup.databinding.CreateEditPageBinding
import com.google.android.material.datepicker.MaterialDatePicker

class CreateEditFragment : Fragment(R.layout.create_edit_page) {
    private var yearDate = 0
    private var monthDate = 0
    private var dayDate = 0
    private var selectedCategory = ""

    private var _binding: CreateEditPageBinding? = null
    private val binding
        get() = _binding!!

    private val watcher = object : SimpleTextWatcher() {
        override fun afterTextChanged(s: Editable?) = updateSaveButtonState()
    }

    companion object {
        private const val SCREEN_TYPE_KEY = "ScreenTypeKey"
        private const val TRANSACTION_ID_KEY = "TransactionIdKey"
        private const val TRANSACTION_TYPE_KEY = "TransactionTypeKey"

        fun newInstance(
            screenType: String,
            transactionId: Long,
            transactionType: String
        ): Fragment {
            val fragment = CreateEditFragment()
            fragment.arguments = Bundle().apply {
                putString(SCREEN_TYPE_KEY, screenType)
                putLong(TRANSACTION_ID_KEY, transactionId)
                putString(TRANSACTION_TYPE_KEY, transactionType)
            }
            return fragment
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = CreateEditPageBinding.bind(view)

        val screenType = requireArguments().getString(SCREEN_TYPE_KEY)
        val transactionId = requireArguments().getLong(TRANSACTION_ID_KEY)
        val transactionType = requireArguments().getString(TRANSACTION_TYPE_KEY)!!

        val viewModel = (activity as ProvideViewModel).getViewModel(
            this,
            CreateEditTransactionViewModel::class.java
        )

        val key = "$screenType $transactionType"
        val titleResId = when (key) {
            "Create Expense" -> R.string.CreateExpensePageTitle
            "Create Income" -> R.string.CreateIncomePageTitle
            "Edit Expense" -> R.string.EditExpenseTitle
            "Edit Income" -> R.string.EditIncomeTitle
            else -> throw IllegalStateException("screen is not found: $key")
        }

        val currentTitle = getString(titleResId)
        if (screenType == "Create") {
            viewModel.createInit(currentTitle)
        } else {
            viewModel.editInit(currentTitle, transactionId, transactionType)
        }

        val expenseCategoriesButtons = listOf(
            binding.utilitiesButton,
            binding.transfersButton,
            binding.groceriesButton,
            binding.otherButton
        )

        expenseCategoriesButtons.forEach { button ->
            button.setOnClickListener {
                expenseCategoriesButtons.forEach { if (button != it) it.isChecked = false }
                selectedCategory = button.text.toString()
                updateSaveButtonState()
            }
        }

        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText(getString(R.string.dateTitle))
            .build()
        datePicker.addOnPositiveButtonClickListener { selection ->
            val (day, month, year) = formatLongToDateComponents(selection)
            dayDate = day
            monthDate = month
            yearDate = year
            binding.dateTextView.text = "$day.$month.$year"
            updateSaveButtonState()
        }

        binding.saveButton.setOnClickListener {
            val sum = binding.sumInputEditText.text.toString()
            val intSum = sum.toIntOrNull() ?: 0
            viewModel.create(
                TransactionInputDetails(
                    transactionType,
                    selectedCategory,
                    intSum,
                    dayDate,
                    monthDate,
                    yearDate
                )
            )
        }

        binding.backButton.setOnClickListener {
            viewModel.comeback(transactionType)
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            viewModel.comeback(transactionType)
        }

        binding.openDateButton.setOnClickListener {
            datePicker.show(requireActivity().supportFragmentManager, "DATE_PICKER_TAG")
        }

    }

    private fun formatLongToDateComponents(selection: Long): Triple<Int, Int, Int> {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = selection

        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val month = calendar.get(Calendar.MONTH) + 1
        val year = calendar.get(Calendar.YEAR)

        return Triple(day, month, year)
    }

    private fun updateSaveButtonState() {
        val sum = binding.sumInputEditText.text.toString().trim()
        val intSum = sum.toIntOrNull() ?: 0
        val isSumValid = intSum > 0
        val isSelectedCategory = selectedCategory != ""
        val isDateSelected = yearDate != 0
        binding.saveButton.isEnabled = isSumValid && isSelectedCategory && isDateSelected
    }

    override fun onResume() {
        super.onResume()
        binding.sumInputEditText.addTextChangedListener(watcher)
    }

    override fun onPause() {
        super.onPause()
        binding.sumInputEditText.removeTextChangedListener(watcher)
    }
}
abstract class SimpleTextWatcher : TextWatcher {
    override fun afterTextChanged(s: Editable?) = Unit
    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
}