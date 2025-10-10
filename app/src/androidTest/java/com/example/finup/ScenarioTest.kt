package com.example.finup

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.finup.CreateEdit.CreateEditPage
import com.example.finup.main.ExpenseAndIncomePage
import com.example.finup.main.MainActivity
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
        expenseListPage.clickBottomExpenseIcon()
        expenseListPage.setDate(2025, 9)
        expenseListPage.checkVisibleNow("0")
        expenseListPage.clickCreateButton()
        expenseListPage.checkNotVisibleNow()

        val createExpensePage = CreateEditPage("Create expense")
        createExpensePage.checkVisibleNow()
        createExpensePage.inputSum("5000")
        createExpensePage.clickUtilitiesButton()
        createExpensePage.openDatePickerAndCLickButton("09", "13")
        createExpensePage.checkDateVisibleNow("13.9.2025")
        createExpensePage.clickSaveButton()
        createExpensePage.checkNotVisibleNow()

        expenseListPage.checkVisibleNow("5000")
        expenseListPage.checkDateTitleVisible("13 september", 0, "5000")
        expenseListPage.checkItemVisible("Utilities", "5000", 1)
    }

    @Test
    fun create_second_expense() {
        create_expense_item()
        val expenseListPage = ExpenseAndIncomePage()
        expenseListPage.setDate(2025, 9)
        expenseListPage.checkVisibleNow("5000")
        expenseListPage.clickCreateButton()
        expenseListPage.checkNotVisibleNow()

        val createExpensePage = CreateEditPage("Create expense")
        createExpensePage.checkVisibleNow()
        createExpensePage.inputSum("2000")
        createExpensePage.clickGroceriesButton()
        createExpensePage.openDatePickerAndCLickButton("09", "13")
        createExpensePage.checkDateVisibleNow("13.9.2025")

        createExpensePage.clickSaveButton()
        createExpensePage.checkNotVisibleNow()

        expenseListPage.checkVisibleNow("7000")
        expenseListPage.checkDateTitleVisible("13 september", 0, "7000")
        expenseListPage.checkItemVisible("Utilities", "5000", 1)
        expenseListPage.checkItemVisible("Groceries", "2000", 2)

        activityScenarioRule.scenario.recreate()

        expenseListPage.checkVisibleNow("7000")
        expenseListPage.checkDateTitleVisible("13 september", 0, "7000")
        expenseListPage.checkItemVisible("Utilities", "5000", 1)
        expenseListPage.checkItemVisible("Groceries", "2000", 2)
    }

    @Test
    fun create_expense_and_delete_expense() {
        create_expense_item()
        val expenseListPage = ExpenseAndIncomePage()
        expenseListPage.setDate(2025, 9)
        expenseListPage.checkVisibleNow("5000")

        expenseListPage.clickItemAt(1, "Utilities")
        expenseListPage.checkNotVisibleNow()

        val expenseEditPage = CreateEditPage("Edit expense")
        expenseEditPage.checkVisibleNow()
        expenseEditPage.clickDeleteButton()
        expenseListPage.checkVisibleNow("0")
        expenseListPage.checkDateTitleNotVisible(0,"13 september","5000")
        expenseListPage.checkItemNotVisible(1)

        expenseListPage.clickCreateButton()
        expenseListPage.checkNotVisibleNow()

        val createExpensePage = CreateEditPage("Create expense")
        createExpensePage.checkVisibleNow()

        createExpensePage.clickBackButton()
        createExpensePage.checkNotVisibleNow()
        expenseListPage.checkVisibleNow("0")
    }

    @Test
    fun create_expenses_different_days() {
        create_second_expense()
        val expenseListPage = ExpenseAndIncomePage()
        expenseListPage.setDate(2025, 9)
        expenseListPage.checkVisibleNow("7000")

        expenseListPage.clickCreateButton()
        expenseListPage.checkNotVisibleNow()

        val createExpensePage = CreateEditPage("Create expense")
        createExpensePage.checkVisibleNow()
        createExpensePage.inputSum("15000")
        createExpensePage.clickOtherButton()
        createExpensePage.openDatePickerAndCLickButton("09", "02")

        createExpensePage.clickSaveButton()
        createExpensePage.checkNotVisibleNow()

        expenseListPage.checkVisibleNow("22000")
        expenseListPage.checkDateTitleVisible("2 september", 0, "15000")
        expenseListPage.checkItemVisible("Other", "15000", 1)
        expenseListPage.checkDateTitleVisible("13 september", 2, "7000")
        expenseListPage.checkItemVisible("Utilities", "5000", 3)
        expenseListPage.checkItemVisible("Groceries", "2000", 4)

        activityScenarioRule.scenario.recreate()

        expenseListPage.checkVisibleNow("22000")
        expenseListPage.checkDateTitleVisible("2 september", 0, "15000")
        expenseListPage.checkItemVisible("Other", "15000", 1)
        expenseListPage.checkDateTitleVisible("13 september", 2, "7000")
        expenseListPage.checkItemVisible("Utilities", "5000", 3)
        expenseListPage.checkItemVisible("Groceries", "2000", 4)
    }

    @Test
    fun create_expense_month_ago() {
        val expenseListPage = ExpenseAndIncomePage()
        expenseListPage.setDate(2025, 9)
        expenseListPage.checkVisibleNow("0")
        expenseListPage.clickCreateButton()

        val createExpensePage = CreateEditPage("Create expense")
        createExpensePage.checkVisibleNow()
        createExpensePage.inputSum("1000")
        createExpensePage.clickGroceriesButton()
        createExpensePage.openDatePickerAndCLickButton("08", "25")
        createExpensePage.clickSaveButton()
        createExpensePage.checkNotVisibleNow()

        expenseListPage.checkVisibleNow("0")
        expenseListPage.setDate(2025, 8)
        expenseListPage.clickLeftArrow()
        expenseListPage.checkVisibleNow("1000")
        expenseListPage.checkDateTitleVisible("25 august", 0,"1000")
        expenseListPage.checkItemVisible("Groceries", "1000", 1)
    }

    @Test
    fun create_income() {
        val incomeListPage = ExpenseAndIncomePage()
        incomeListPage.clickBottomIncomeIcon()
        incomeListPage.setDate(2025, 9)
        incomeListPage.checkVisibleNow("0")
        incomeListPage.clickCreateButton()
        incomeListPage.checkNotVisibleNow()

        val createIncomePage = CreateEditPage("Create income")
        createIncomePage.checkVisibleNow()
        createIncomePage.inputSum("5000")
        createIncomePage.selectKaspiBank()
        createIncomePage.openDatePickerAndCLickButton("09", "13")
        createIncomePage.checkDateVisibleNow("13.9.2025")
        createIncomePage.clickSaveButton()
        createIncomePage.checkNotVisibleNow()

        incomeListPage.checkVisibleNow("5000")
        incomeListPage.checkDateTitleVisible("13 september", 0,"5000")
        incomeListPage.checkItemVisible("Kaspi Bank", "5000", 1)
    }

    @Test
    fun create_second_income() {
        create_income()
        val incomeListPage = ExpenseAndIncomePage()
        incomeListPage.setDate(2025, 9)
        incomeListPage.checkVisibleNow("5000")
        incomeListPage.clickCreateButton()
        incomeListPage.checkNotVisibleNow()

        val createIncomePage = CreateEditPage("Create income")
        createIncomePage.checkVisibleNow()
        createIncomePage.inputSum("2000")
        createIncomePage.selectBccBank()
        createIncomePage.openDatePickerAndCLickButton("09", "13")
        createIncomePage.checkDateVisibleNow("13.9.2025")

        createIncomePage.clickSaveButton()
        createIncomePage.checkNotVisibleNow()

        incomeListPage.checkVisibleNow("7000")
        incomeListPage.checkDateTitleVisible("13 september", 0,"7000")
        incomeListPage.checkItemVisible("Kaspi Bank", "5000", 1)
        incomeListPage.checkItemVisible("BCC Bank", "2000", 2)
    }

    @Test
    fun create_income_month_ago() {
        val incomeListPage = ExpenseAndIncomePage()
        incomeListPage.clickBottomIncomeIcon()
        incomeListPage.setDate(2025, 9)
        incomeListPage.checkVisibleNow("0")
        incomeListPage.clickCreateButton()

        val createIncomePage = CreateEditPage("Create income")
        createIncomePage.inputSum("1000")
        createIncomePage.selectKaspiBank()
        createIncomePage.openDatePickerAndCLickButton("08", "25")
        createIncomePage.checkDateVisibleNow("25.8.2025")
        createIncomePage.clickSaveButton()
        createIncomePage.checkNotVisibleNow()

        incomeListPage.checkVisibleNow("0")
        incomeListPage.setDate(2025, 8)
        incomeListPage.clickLeftArrow()
        incomeListPage.checkVisibleNow("1000")
        incomeListPage.checkDateTitleVisible("25 august", 0,"1000")
        incomeListPage.checkItemVisible("Kaspi Bank", "1000", 1)
    }

    @Test
    fun create_income_item_different_days() {
        create_second_income()
        val incomeListPage = ExpenseAndIncomePage()
        incomeListPage.setDate(2025, 9)
        incomeListPage.checkVisibleNow("7000")

        incomeListPage.clickCreateButton()
        incomeListPage.checkNotVisibleNow()

        val createIncomePage = CreateEditPage("Create income")
        createIncomePage.checkVisibleNow()
        createIncomePage.inputSum("15000")
        createIncomePage.selectBccBank()
        createIncomePage.openDatePickerAndCLickButton("09", "25")
        createIncomePage.checkDateVisibleNow("25.9.2025")
        createIncomePage.clickSaveButton()
        createIncomePage.checkNotVisibleNow()

        incomeListPage.checkVisibleNow("22000")
        incomeListPage.checkDateTitleVisible("13 september", 0,"7000")
        incomeListPage.checkItemVisible("Kaspi Bank", "5000", 1)
        incomeListPage.checkItemVisible("BCC Bank", "2000", 2)
        incomeListPage.checkDateTitleVisible("25 september", 3,"15000")
        incomeListPage.checkItemVisible("BCC Bank", "15000", 4)
    }

    @Test
    fun create_income_and_delete_income() {
        create_income()
        val incomeListPage = ExpenseAndIncomePage()
        incomeListPage.setDate(2025, 9)
        incomeListPage.checkVisibleNow("5000")

        incomeListPage.clickItemAt(1, "Kaspi Bank")
        incomeListPage.checkNotVisibleNow()
        val incomeEditListPage = CreateEditPage("Edit income")
        incomeEditListPage.checkVisibleNow()
        incomeEditListPage.clickDeleteButton()
        incomeListPage.checkVisibleNow("0")
        incomeListPage.checkDateTitleNotVisible(0,"13 september","5000")
        incomeListPage.checkItemNotVisible( 0)

        incomeListPage.clickCreateButton()
        incomeListPage.checkNotVisibleNow()

        val createIncomePage = CreateEditPage("Create income")
        createIncomePage.checkVisibleNow()

        createIncomePage.clickBackButton()
        createIncomePage.checkNotVisibleNow()
        incomeListPage.checkVisibleNow("0")
    }

}