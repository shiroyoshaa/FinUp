package com.example.finup

class ExpensePage(private val mainDateTitle: String): AbstractPage(expenseIds,mainDateTitle){

}



private val expenseIds = PageIds(
    rootId = R.id.rootId,
    titleMonthTextView = R.id.titleMonthTextView,
    floatingButtonId = R.id.floatingButtonId,
    leftImageButtonId =R.id.leftImageButtonId,
    rightImageButtonId = R.id.rightImageButtonId,
    recyclerViewId = R.id.recyclerViewId,
    expenseItemLayout =  R.id.dateLayoutId,
    itemDateTextView =  R.id.dateTextViewId,
    expenseItemButton = R.id.dateButtonId,
    expenseListRootLayout = R.id.expenseListRootLayout,
    expenseSumTextView = R.id.expenseSumTextView,
    expenseNameTextView = R.id.expenseNameTextView,
)