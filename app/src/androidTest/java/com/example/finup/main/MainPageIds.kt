package com.example.finup.main

data class MainPageIds(
    val startRootId: Int,
    val rootId: Int,
    val floatingButtonId: Int,
    val recyclerViewId: Int,
)

data class ToolBar(
    val titleMonthTextView: Int,
    val titleSumTextView: Int,
    val leftImageButtonId: Int,
    val rightImageButtonId: Int,
    )

data class BottomNav(
    val coordinatorLayout: Int,
    val bottomNavId: Int,
    val expenseIcon: Int,
    val incomeIcon: Int,
)

data class Header(
    val headerRootLayout: Int,
    val headerDateTextView: Int,
    val headerTotalSumTextView: Int,
    val headerButton: Int,
)

data class Item(
    val itemRootLayout: Int,
    val itemSumTextView: Int,
    val itemNameTextView: Int,
)