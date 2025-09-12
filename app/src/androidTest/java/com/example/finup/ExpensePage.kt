package com.example.finup

class ExpensePage: AbstractPage(expenseIds)



private val expenseIds = PageIds(
    rootId = R.id.rootId,
    titleMonthTextView = R.id.titleMonthTextView,
    floatingButtonId = R.id.floatingButtonId,
    leftImageButtonId =R.id.leftImageButtonId,
    rightImageButtonId = R.id.rightImageButtonId,
    recyclerViewId = R.id.recyclerViewId,
    transactionText = R.id.transactionText,
    dateLayoutId =  R.id.dateLayoutId,
    dateTextViewId =  R.id.dateTextViewId,
    dateButtonId = R.id.dateButtonId,
    expenseListRootLayout = R.id.expenseListRootLayout,
    expenseSumTextView = R.id.expenseSumTextView,
    expenseNameTextView = R.id.expenseNameTextView,
)