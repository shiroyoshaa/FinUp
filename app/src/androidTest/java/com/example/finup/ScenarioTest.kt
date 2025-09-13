package com.example.finup

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class ScenarioTest {

    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun create_expense_item() {
        val expenseListPage = ExpensePage("september")
        expenseListPage.checkVisibleNow()
        expenseListPage.clickCreateButton()
        val createPage = CreateAndEditExpensePage("Create expense")
        expenseListPage.checkNotVisibleNow()
        createPage.checkVisibleNow()
        createPage.inputExpenseSum("5000")
        createPage.clickUtilitiesButton()
        createPage.openDatePickerAndCLickButton("13")
        createPage.checkDateVisibleNow("13/9/2025")
        createPage.clickSaveButton()
        createPage.checkNoteVisibleNow()
        expenseListPage.checkVisibleNow()
        expenseListPage.checkDateTitleVisible("13 september",0)
        expenseListPage.clickRecyclerButton(0)
        expenseListPage.checkExpenseItemVisible("Utilities","5000",0)
    }

    fun create_second_expense(){
        create_expense_item()
        val expenseListPage = ExpensePage("september")
        expenseListPage.checkVisibleNow()
        expenseListPage.clickCreateButton()
    }

    @Test
    fun create_expense_item_delete_expense() {
        create_expense_item()
        val expenseListPage = ExpensePage("september")
        expenseListPage.checkVisibleNow()
        expenseListPage.clickExpenseAt(0)
        expenseListPage.checkNotVisibleNow()
        val expenseEditPage = CreateAndEditExpensePage("edit expense")
        expenseEditPage.checkVisibleNow()
        expenseEditPage.clickDeleteButton()
        expenseListPage.checkVisibleNow()
        expenseListPage.checkDateTitleNoteVisible("13 september",0)
        expenseListPage.checkExpenseItemNotVisible("Utilities","5000",0)
    }
}