package com.example.finup

import android.R.attr.button
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import org.hamcrest.Matchers.allOf
import kotlin.jvm.java

class CreateAndEditExpensePage(private val title: String) {

    private fun checkVisibleNow() {
        onView(
            allOf(
                isAssignableFrom(TextView::class.java),
                withId(R.id.CreatetitleTextView),
                withParent(isAssignableFrom(ConstraintLayout::class.java)),
                withParent(withId(R.id.CreateAndEditRootlayout)),
                withText(title)
            )
        ).check(matches(isDisplayed()))
    }

    private fun clickBackButton(title: String) {
        onView(
            allOf(
                isAssignableFrom(ImageButton::class.java),
                withId(R.id.backButton),
                withParent(withId(R.id.CreateAndEditRootlayout)),
                withParent(isAssignableFrom(ConstraintLayout::class.java)),
            )
        ).perform(click())
    }


    private fun inputExpenseName(text: String) {
        onView(
            allOf(
                isAssignableFrom(TextInputEditText::class.java),
                withId(R.id.expenseInputEditText),
                withParent(isAssignableFrom(ConstraintLayout::class.java)),
                withParent(withId(R.id.CreateAndEditRootlayout))
            )
        ).perform(typeText("text"), closeSoftKeyboard())
    }

    private fun clickUtilitiesButton() {
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

    private fun clickTransfersButton() {
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

    private fun clickGroceriesButton() {
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

    private fun clickOtherButton() {
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

    private fun openDatePickerAndCLickButton(day: String){
        onView(
            allOf(
                isAssignableFrom(MaterialButton::class.java),
                withId(R.id.dateMaterialButton),
                withParent(withId(R.id.CreateAndEditRootlayout)),
                withParent(isAssignableFrom(ConstraintLayout::class.java)),
            )
        ).check(matches(isDisplayed()))
        onView(withText(day)).perform(click())
        onView(withText("OK"))
    }

    private fun checkDateVisibleNow(date: String) {
        onView(
            allOf(
                isAssignableFrom(TextView::class.java),
                withId(R.id.dateTextView),
                withParent(isAssignableFrom(ConstraintLayout::class.java)),
                withText(date),
            )
        ).check(matches(isDisplayed()))
    }

    private fun clickSaveButton() {
        onView(
            allOf(
                isAssignableFrom(Button::class.java),
                withId(R.id.saveExpenseButton),
                withText("Save"),
                withParent(isAssignableFrom(ConstraintLayout::class.java)),
                withParent(withId(R.id.CreateAndEditRootlayout))
            )
        ).check(matches(isDisplayed())).perform(click())
    }

    private fun deleteButton() {
        onView(
            allOf(
                isAssignableFrom(Button::class.java),
                withText("Delete"),
                withId(R.id.deleteExpenseButton),
                withParent(withId(R.id.CreateAndEditRootlayout)),
                withParent(isAssignableFrom(ConstraintLayout::class.java)),

                )
        ).check(matches(isDisplayed())).perform(click())
    }
}
