package com.example.finup.CreateEdit

data class CreateEditPageIds(
    val editCreateRootLayout: Int,
    val titleTextView: Int,
    val amountLabelTextView: Int,
    val backButton: Int,
    val dateLinearLayout: Int,
    val openDateButton: Int,
    val pickDateTextView: Int,
    val sumInputEditText: Int,
    val dateTextView: Int,
    val saveButton: Int,
    val deleteButton: Int,
)

data class ExpenseCategoryIds(
    val gridRootId: Int,
    val otherButton: Int,
    val transfersButton: Int,
    val groceriesButton: Int,
    val utilitiesButton: Int,
)

data class IncomeCategoryIds(
    val gridRootId: Int,
    val kaspiButton: Int,
    val bccBankButton: Int,
)
