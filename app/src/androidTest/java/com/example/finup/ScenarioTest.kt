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
        expenseListPage.clickBottomIncomeIcon()
        expenseListPage.setDate(2025, 9)
        expenseListPage.checkVisibleNow("0")
        expenseListPage.clickCreateButton()
        expenseListPage.checkNotVisibleNow()

        val createPage = CreateEditPage("Create expense")
        createPage.checkVisibleNow()
        createPage.inputSum("5000")
        createPage.clickUtilitiesButton()
        createPage.openDatePickerAndCLickButton(9, 13)
        createPage.checkDateVisibleNow("13/9/2025")
        createPage.clickSaveButton()
        createPage.checkNotVisibleNow()

        expenseListPage.checkVisibleNow("5000")
        expenseListPage.checkDateTitleVisible("13 september", 0, "5000")
        expenseListPage.clickRecyclerButton(0)
        expenseListPage.checkItemVisible("Utilities", "5000", 0)
    }

    @Test
    fun create_second_expense() {
        create_expense_item()
        val expenseListPage = ExpenseAndIncomePage()
        expenseListPage.setDate(2025, 9)
        expenseListPage.checkVisibleNow("5000")
        expenseListPage.clickCreateButton()
        expenseListPage.checkNotVisibleNow()

        val createExpensePage = CreateEditPage("create expense")
        createExpensePage.checkVisibleNow()
        createExpensePage.inputSum("2000")
        createExpensePage.clickGroceriesButton()
        createExpensePage.openDatePickerAndCLickButton(9, 13)
        createExpensePage.checkDateVisibleNow("13/9/2025")

        createExpensePage.clickSaveButton()
        createExpensePage.checkNotVisibleNow()

        expenseListPage.checkVisibleNow("7000")
        expenseListPage.checkDateTitleVisible("13 september", 0, "7000")
        expenseListPage.checkItemVisible("Utilities", "5000", 0)
        expenseListPage.checkItemVisible("Groceries", "2000", 0)
    }

    @Test
    fun create_expense_and_delete_expense() {
        create_expense_item()
        val expenseListPage = ExpenseAndIncomePage()
        expenseListPage.setDate(2025, 9)
        expenseListPage.checkVisibleNow("5000")

        expenseListPage.clickItemAt(0, "Utilities")
        expenseListPage.checkNotVisibleNow()

        val expenseEditPage = CreateEditPage("Edit Expense")
        expenseEditPage.checkVisibleNow()
        expenseEditPage.clickDeleteButton()
        expenseListPage.checkVisibleNow("0")
        expenseListPage.checkDateTitleNotVisible(0,"13 september","5000")
        expenseListPage.checkItemNotVisible(0)

        expenseListPage.clickCreateButton()
        expenseListPage.checkNotVisibleNow()

        val createExpensePage = CreateEditPage("Create Expense")
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

        val createExpensePage = CreateEditPage("Create Expense")
        createExpensePage.checkVisibleNow()
        createExpensePage.inputSum("15000")
        createExpensePage.clickOtherButton()
        createExpensePage.openDatePickerAndCLickButton(9, 2)

        createExpensePage.clickSaveButton()
        createExpensePage.checkNotVisibleNow()

        expenseListPage.checkVisibleNow("22000")
        expenseListPage.checkDateTitleVisible("2 september", 0, "7000")
        expenseListPage.checkItemVisible("Utilities", "5000", 0)
        expenseListPage.checkItemVisible("Groceries", "2000", 0)
        expenseListPage.checkDateTitleVisible("13 september", 1, "15000")
        expenseListPage.checkItemVisible("Other", "15000", 1)
    }

    @Test
    fun create_expense_month_ago() {
        val expenseListPage = ExpenseAndIncomePage()
        expenseListPage.setDate(2025, 9)
        expenseListPage.checkVisibleNow("0")

        val createExpensePage = CreateEditPage("create expense")
        createExpensePage.inputSum("1000")
        createExpensePage.clickGroceriesButton()
        createExpensePage.openDatePickerAndCLickButton(8, 25)
        createExpensePage.clickSaveButton()
        createExpensePage.checkNotVisibleNow()

        expenseListPage.checkVisibleNow("0")
        expenseListPage.setDate(2025, 8)
        expenseListPage.clickLeftArrow()
        expenseListPage.checkVisibleNow("1000")
        expenseListPage.checkDateTitleVisible("25 august", 0,"1000")
        expenseListPage.checkItemVisible("Groceries", "1000", 0)
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
        createIncomePage.openDatePickerAndCLickButton(9, 13)
        createIncomePage.checkDateVisibleNow("13/9/2025")
        createIncomePage.clickSaveButton()
        createIncomePage.checkNotVisibleNow()

        incomeListPage.checkVisibleNow("5000")
        incomeListPage.checkDateTitleVisible("13 september", 0,"5000")
        incomeListPage.checkItemVisible("Kaspi Bank", "5000", 0)
    }

    @Test
    fun create_second_income() {
        create_income()
        val incomeListPage = ExpenseAndIncomePage()
        incomeListPage.setDate(2025, 9)
        incomeListPage.checkVisibleNow("5000")
        incomeListPage.clickCreateButton()
        incomeListPage.checkNotVisibleNow()

        val createIncomePage = CreateEditPage("create income")
        createIncomePage.checkVisibleNow()
        createIncomePage.inputSum("2000")
        createIncomePage.selectBccBank()
        createIncomePage.openDatePickerAndCLickButton(9, 13)
        createIncomePage.checkDateVisibleNow("13/9/2025")

        createIncomePage.clickSaveButton()
        createIncomePage.checkNotVisibleNow()

        incomeListPage.checkVisibleNow("7000")
        incomeListPage.checkDateTitleVisible("13 september", 0,"7000")
        incomeListPage.checkItemVisible("Kaspi Bank", "5000", 0)
        incomeListPage.checkItemVisible("BCC", "2000", 0)
    }

    @Test
    fun create_income_month_ago() {
        val incomeListPage = ExpenseAndIncomePage()
        incomeListPage.clickBottomIncomeIcon()
        incomeListPage.setDate(2025, 9)
        incomeListPage.checkVisibleNow("0")

        val createIncomePage = CreateEditPage("create income")
        createIncomePage.inputSum("1000")
        createIncomePage.selectKaspiBank()
        createIncomePage.openDatePickerAndCLickButton(8, 25)
        createIncomePage.clickSaveButton()
        createIncomePage.checkNotVisibleNow()

        incomeListPage.checkVisibleNow("0")
        incomeListPage.setDate(2025, 8)
        incomeListPage.clickLeftArrow()
        incomeListPage.checkVisibleNow("1000")
        incomeListPage.checkDateTitleVisible("25 august", 0,"1000")
        incomeListPage.checkItemVisible("Kaspi Bank", "1000", 0)
    }

    @Test
    fun create_income_item_different_days() {
        create_second_income()
        val incomeListPage = ExpenseAndIncomePage()
        incomeListPage.setDate(2025, 9)
        incomeListPage.checkVisibleNow("7000")

        incomeListPage.clickCreateButton()
        incomeListPage.checkNotVisibleNow()

        val createIncomePage = CreateEditPage("create income")
        createIncomePage.checkVisibleNow()
        createIncomePage.inputSum("15000")
        createIncomePage.selectBccBank()
        createIncomePage.openDatePickerAndCLickButton(9, 25)

        createIncomePage.clickSaveButton()
        createIncomePage.checkNotVisibleNow()

        incomeListPage.checkVisibleNow("22000")
        incomeListPage.checkDateTitleVisible("13 september", 0,"7000")
        incomeListPage.checkItemVisible("Kaspi Bank", "5000", 0)
        incomeListPage.checkItemVisible("BCC", "2000", 0)
        incomeListPage.checkDateTitleVisible("25 september", 1,"15000")
        incomeListPage.checkItemVisible("Kaspi Bank", "15000", 1)
    }

    @Test
    fun create_income_and_delete_income() {
        create_income()
        val incomeListPage = ExpenseAndIncomePage()
        incomeListPage.setDate(2025, 9)
        incomeListPage.checkVisibleNow("5000")

        incomeListPage.clickItemAt(0, "Kaspi Bank")
        incomeListPage.checkNotVisibleNow()
        val incomeEditListPage = CreateEditPage("edit expense")
        incomeEditListPage.checkVisibleNow()
        incomeEditListPage.clickDeleteButton()
        incomeListPage.checkVisibleNow("0")
        incomeListPage.checkDateTitleNotVisible(0,"13 september","5000")
        incomeListPage.checkItemNotVisible( 0)

        incomeListPage.clickCreateButton()
        incomeListPage.checkNotVisibleNow()

        val createIncomePage = CreateEditPage("create income")
        createIncomePage.checkVisibleNow()

        createIncomePage.clickBackButton()
        createIncomePage.checkNotVisibleNow()
        incomeListPage.checkVisibleNow("0")
    }

}