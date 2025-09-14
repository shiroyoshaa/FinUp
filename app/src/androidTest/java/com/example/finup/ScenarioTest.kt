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
        val expenseListPage = ExpenseAndIncomePage()
        expenseListPage.setDate(2025, 9)
        expenseListPage.checkVisibleNow()
        expenseListPage.clickCreateButton()
        expenseListPage.checkNotVisibleNow()

        val createPage = CreateAndEditExpensePage("Create expense")
        createPage.checkVisibleNow()
        createPage.inputExpenseSum("5000")
        createPage.clickUtilitiesButton()
        createPage.openDatePickerAndCLickButton(9, 13)
        createPage.checkDateVisibleNow("13/9/2025")
        createPage.clickSaveButton()
        createPage.checkNoteVisibleNow()

        expenseListPage.checkVisibleNow()
        expenseListPage.checkDateTitleVisible("13 september", 0)
        expenseListPage.clickRecyclerButton(0)
        expenseListPage.checkItemVisible("Utilities", "5000", 0)
    }

    @Test
    fun create_second_expense() {
        create_expense_item()
        val expenseListPage = ExpenseAndIncomePage()
        expenseListPage.setDate(2025, 9)
        expenseListPage.checkVisibleNow()
        expenseListPage.clickCreateButton()
        expenseListPage.checkNotVisibleNow()

        val createExpensePage = CreateAndEditExpensePage("create expense")
        createExpensePage.checkVisibleNow()
        createExpensePage.inputExpenseSum("2000")
        createExpensePage.clickGroceriesButton()
        createExpensePage.openDatePickerAndCLickButton(9, 13)
        createExpensePage.checkDateVisibleNow("13/9/2025")

        createExpensePage.clickSaveButton()
        createExpensePage.checkNoteVisibleNow()

        expenseListPage.checkVisibleNow()
        expenseListPage.checkDateTitleVisible("13 september", 0)
        expenseListPage.checkItemVisible("Utilities", "5000", 0)
        expenseListPage.checkItemVisible("Groceries", "2000", 0)
    }

    @Test
    fun create_expense_and_expense_item_delete() {
        create_expense_item()
        val expenseListPage = ExpenseAndIncomePage()
        expenseListPage.setDate(2025, 9)
        expenseListPage.checkVisibleNow()

        expenseListPage.clickItemAt(0, "Utilities")
        expenseListPage.checkNotVisibleNow()
        val expenseEditPage = CreateAndEditExpensePage("edit expense")
        expenseEditPage.checkVisibleNow()
        expenseEditPage.clickDeleteButton()
        expenseListPage.checkVisibleNow()
        expenseListPage.checkDateTitleNoteVisible("13 september", 0)
        expenseListPage.checkItemNotVisible("Utilities", "5000", 0)

        expenseListPage.clickCreateButton()
        expenseListPage.checkNotVisibleNow()
        val createExpensePage = CreateAndEditExpensePage("create expense")
        createExpensePage.checkVisibleNow()

        createExpensePage.clickBackButton()
        createExpensePage.checkNoteVisibleNow()
        expenseListPage.checkVisibleNow()
    }

    @Test
    fun create_expense_item_different_days() {
        create_second_expense()
        val expenseListPage = ExpenseAndIncomePage()
        expenseListPage.setDate(2025, 9)
        expenseListPage.checkVisibleNow()

        expenseListPage.clickCreateButton()
        expenseListPage.checkNotVisibleNow()

        val createExpensePage = CreateAndEditExpensePage("create expense")
        createExpensePage.checkVisibleNow()
        createExpensePage.inputExpenseSum("15000₸")
        createExpensePage.clickOtherButton()
        createExpensePage.openDatePickerAndCLickButton(9, 2)

        createExpensePage.clickSaveButton()
        createExpensePage.checkNoteVisibleNow()

        expenseListPage.checkVisibleNow()
        expenseListPage.checkDateTitleVisible("2 september", 0)
        expenseListPage.checkItemVisible("Utilities", "5000", 0)
        expenseListPage.checkItemVisible("Groceries", "2000", 0)
        expenseListPage.checkDateTitleVisible("13 september", 1)
        expenseListPage.checkItemVisible("Other", "15000", 1)
    }

    @Test
    fun create_expense_month_ago() {
        val expenseListPage = ExpenseAndIncomePage()
        expenseListPage.setDate(2025, 9)
        expenseListPage.checkVisibleNow()

        val createExpensePage = CreateAndEditExpensePage("create expense")
        createExpensePage.inputExpenseSum("1000")
        createExpensePage.clickGroceriesButton()
        createExpensePage.openDatePickerAndCLickButton(8, 25)
        createExpensePage.clickSaveButton()
        createExpensePage.checkNoteVisibleNow()

        expenseListPage.checkVisibleNow()
        expenseListPage.setDate(2025, 8)
        expenseListPage.clickLeftArrow()
        expenseListPage.checkVisibleNow()
        expenseListPage.checkDateTitleVisible("25 august", 0)
        expenseListPage.checkItemVisible("Groceries", "1000", 0)
    }

    @Test
    fun create_income() {

        val incomeListPage = ExpenseAndIncomePage()
        incomeListPage.setDate(2025, 9)
        incomeListPage.checkVisibleNow()
        incomeListPage.clickCreateButton()
        incomeListPage.checkNotVisibleNow()

        val createIncomePage = CreateAndEditIncomePage("Create income")
        createIncomePage.checkVisibleNow()
        createIncomePage.inputIncomeSum("5000")
        createIncomePage.selectKaspiBank()
        createIncomePage.openDatePickerAndCLickButton(9, 13)
        createIncomePage.checkDateVisibleNow("13/9/2025")
        createIncomePage.clickSaveButton()
        createIncomePage.checkNoteVisibleNow()

        incomeListPage.checkVisibleNow()
        incomeListPage.checkDateTitleVisible("13 september", 0)
        incomeListPage.checkItemVisible("Kaspi Bank", "5000", 0)
    }

    @Test
    fun create_second_income() {
        create_income()
        val incomeListPage = ExpenseAndIncomePage()
        incomeListPage.setDate(2025, 9)
        incomeListPage.checkVisibleNow()
        incomeListPage.clickCreateButton()
        incomeListPage.checkNotVisibleNow()

        val createIncomePage = CreateAndEditIncomePage("create income")
        createIncomePage.checkVisibleNow()
        createIncomePage.inputIncomeSum("2000")
        createIncomePage.selectBccBank()
        createIncomePage.openDatePickerAndCLickButton(9, 13)
        createIncomePage.checkDateVisibleNow("13/9/2025")

        createIncomePage.clickSaveButton()
        createIncomePage.checkNoteVisibleNow()

        incomeListPage.checkVisibleNow()
        incomeListPage.checkDateTitleVisible("13 september", 0)
        incomeListPage.checkItemVisible("Kaspi bank", "5000", 0)
        incomeListPage.checkItemVisible("BCC", "2000", 0)
    }

    @Test
    fun create_income_month_ago() {
        val incomeListPage = ExpenseAndIncomePage()
        incomeListPage.setDate(2025, 9)
        incomeListPage.checkVisibleNow()

        val createIncomePage = CreateAndEditIncomePage("create income")
        createIncomePage.inputIncomeSum("1000")
        createIncomePage.selectKaspiBank()
        createIncomePage.openDatePickerAndCLickButton(8, 25)
        createIncomePage.clickSaveButton()
        createIncomePage.checkNoteVisibleNow()

        incomeListPage.checkVisibleNow()
        incomeListPage.setDate(2025, 8)
        incomeListPage.clickLeftArrow()
        incomeListPage.checkVisibleNow()
        incomeListPage.checkDateTitleVisible("25 august", 0)
        incomeListPage.checkItemVisible("Kaspi Bank", "1000", 0)
    }
    @Test
    fun create_income_item_different_days() {
        create_second_income()
        val incomeListPage = ExpenseAndIncomePage()
        incomeListPage.setDate(2025, 9)
        incomeListPage.checkVisibleNow()

        incomeListPage.clickCreateButton()
        incomeListPage.checkNotVisibleNow()

        val createIncomePage = CreateAndEditIncomePage("create income")
        createIncomePage.checkVisibleNow()
        createIncomePage.inputIncomeSum("15000")
        createIncomePage.selectBccBank()
        createIncomePage.openDatePickerAndCLickButton(9, 25)

        createIncomePage.clickSaveButton()
        createIncomePage.checkNoteVisibleNow()

        incomeListPage.checkVisibleNow()
        incomeListPage.checkDateTitleVisible("13 september", 0)
        incomeListPage.checkItemVisible("Kaspi bank", "5000", 0)
        incomeListPage.checkItemVisible("BCC", "2000", 0)
        incomeListPage.checkDateTitleVisible("25 september", 1)
        incomeListPage.checkItemVisible("Kaspi Bank", "15000", 1)
    }
}
