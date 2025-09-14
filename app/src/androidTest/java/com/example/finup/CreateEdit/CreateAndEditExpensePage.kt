package com.example.finup.CreateEdit

import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.example.finup.R
import org.hamcrest.Matchers.allOf

class CreateAndEditExpensePage(title: String): CreateEditAbstractPage(title,ids){

    fun clickUtilitiesButton() {
        onView(
            allOf(
                isAssignableFrom(LinearLayout::class.java),
                withId(R.id.UtilitiesButton),
                withParent(isAssignableFrom(ConstraintLayout::class.java)),
                hasDescendant(withText("Utilities")),
                hasDescendant(withId(R.id.utilitiesImageView))
            )
        ).check(matches(isDisplayed())).perform(click())
    }

    fun clickTransfersButton() {
        onView(
            allOf(
                isAssignableFrom(LinearLayout::class.java),
                withId(R.id.transfersButton),
                withParent(isAssignableFrom(ConstraintLayout::class.java)),
                hasDescendant(withText("Transfers")),
                hasDescendant(withId(R.id.transfersImageView))
            )
        ).check(matches(isDisplayed())).perform(click())
    }

    fun clickGroceriesButton() {
        onView(
            allOf(
                isAssignableFrom(LinearLayout::class.java),
                withId(R.id.groceriesButton),
                withParent(isAssignableFrom(ConstraintLayout::class.java)),
                hasDescendant(withText("Groceries")),
                hasDescendant(withId(R.id.groceriesImageView))
            )
        ).check(matches(isDisplayed())).perform(click())
    }

    fun clickOtherButton() {
        onView(
            allOf(
                isAssignableFrom(LinearLayout::class.java),
                withId(R.id.otherButton),
                withParent(isAssignableFrom(ConstraintLayout::class.java)),
                hasDescendant(withText("Other")),
                hasDescendant(withId(R.id.otherImageView))
            )
        ).check(matches(isDisplayed())).perform(click())
    }
}

private val ids = CreateEditPageIds(
    editCreateTextView = R.id.editCreateExpenseTv,
    editCreateRootLayout = R.id.editCreateExpenseRl,
    amountLabelTextView = R.id.ExpenseAmountLabelTv,
    backButton = R.id.createEdibBackBtn,
    sumInputEditText = R.id.expenseInputEditText,
    openDateButton = R.id.expenseOpenDateBtn,
    dateTextView = R.id.dateExpenseTextView,
    saveButton = R.id.saveExpenseButton,
    deleteButton = R.id.deleteExpenseButton,
)
