package com.example.finup

import android.widget.Button
import android.widget.DatePicker
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.PickerActions
import androidx.test.espresso.matcher.RootMatchers.isDialog
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withClassName
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import org.hamcrest.Matchers
import org.hamcrest.Matchers.allOf

class CreateAndEditExpensePage(private val title: String) {

    private fun title() = onView(
        allOf(
            isAssignableFrom(TextView::class.java),
            withId(R.id.editCreateExpenseTv),
            withParent(isAssignableFrom(ConstraintLayout::class.java)),
            withParent(withId(R.id.createEditExpenseRl)),
            withText(title)
        )
    )


    private fun amountLabel() = onView(
        allOf(
            withId(R.id.ExpenseAmountLabelTv),
            withText("total:"),
            isAssignableFrom(TextView::class.java),
            withParent(isAssignableFrom(ConstraintLayout::class.java))
        )
    )

    fun checkVisibleNow() {
        title().check(matches(isDisplayed()))
        amountLabel().check(matches(isDisplayed()))
    }

    fun checkNoteVisibleNow() {
        title().check(doesNotExist())
        amountLabel().check(doesNotExist())
    }

    fun clickBackButton() {
        onView(
            allOf(
                isAssignableFrom(ImageButton::class.java),
                withId(R.id.backButton),
                withParent(withId(R.id.createEditExpenseRl)),
                withParent(isAssignableFrom(ConstraintLayout::class.java)),
            )
        ).perform(click())
    }


    fun inputExpenseSum(sum: String) {
        onView(
            allOf(
                isAssignableFrom(TextInputEditText::class.java),
                withId(R.id.expenseInputEditText),
                withParent(isAssignableFrom(ConstraintLayout::class.java)),
                withParent(withId(R.id.createEditExpenseRl))
            )
        ).perform(typeText(sum), closeSoftKeyboard())
    }

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
                hasDescendant(withId(R.id.groceriesImageView))
            )
        ).check(matches(isDisplayed())).perform(click())
    }

    fun openDatePickerAndCLickButton(day: String) {
        onView(
            allOf(
                isAssignableFrom(MaterialButton::class.java),
                withId(R.id.dateMaterialButton),
                withParent(withId(R.id.createEditExpenseRl)),
                withParent(isAssignableFrom(ConstraintLayout::class.java)),
            )
        ).check(matches(isDisplayed())).perform(click())
        onView(withClassName(Matchers.equalTo(DatePicker::class.java.name)))
            .perform(PickerActions.setDate(2025, 9, 13))
        onView(withText("OK"))
            .inRoot(isDialog())
            .perform(click())
    }

    fun checkDateVisibleNow(date: String) {
        onView(
            allOf(
                isAssignableFrom(TextView::class.java),
                withId(R.id.dateTextView),
                withParent(isAssignableFrom(ConstraintLayout::class.java)),
                withText(date),
            )
        ).check(matches(isDisplayed()))
    }

    fun clickSaveButton() {
        onView(
            allOf(
                isAssignableFrom(Button::class.java),
                withId(R.id.saveExpenseButton),
                withText("Save"),
                withParent(isAssignableFrom(ConstraintLayout::class.java)),
                withParent(withId(R.id.createEditExpenseRl))
            )
        ).check(matches(isDisplayed())).perform(click())
    }

    fun clickDeleteButton() {
        onView(
            allOf(
                isAssignableFrom(Button::class.java),
                withText("Delete"),
                withId(R.id.deleteExpenseButton),
                withParent(withId(R.id.createEditExpenseRl)),
                withParent(isAssignableFrom(ConstraintLayout::class.java)),

                )
        ).check(matches(isDisplayed())).perform(click())
    }

}
