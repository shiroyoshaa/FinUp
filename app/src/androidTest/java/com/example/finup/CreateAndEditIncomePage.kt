package com.example.finup

import android.R.attr.text
import android.widget.Button
import android.widget.DatePicker
import android.widget.ImageButton
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.PickerActions
import androidx.test.espresso.matcher.RootMatchers.isDialog
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

class CreateAndEditIncomePage(title: String) {

    private fun checkVisibleNow() {
        onView(
            allOf(
                isAssignableFrom(TextView::class.java),
                withId(R.id.editCreateIncomeTv),
                withParent(isAssignableFrom(ConstraintLayout::class.java)),
                withParent(withId(R.id.createEditIncomeRL))
            )
        ).check(matches(isDisplayed()))
        onView(
            allOf(
                withId(R.id.IncomeAmountLabelTv),
                withText("total:"),
                isAssignableFrom(TextView::class.java),
                withParent(isAssignableFrom(ConstraintLayout::class.java))
            )
        ).check(matches(isDisplayed()))
    }

    private fun clickBackButton(title: String) {
        onView(
            allOf(
                isAssignableFrom(ImageButton::class.java),
                withId(R.id.editBackButton),
                withParent(withId(R.id.createEditIncomeRL)),
                withParent(isAssignableFrom(ConstraintLayout::class.java)),
            )
        ).perform(click())
    }

    private fun inputIncomeSum(sum: String) {
        onView(
            allOf(
                isAssignableFrom(TextInputEditText::class.java),
                withId(R.id.IncomeInputEditText),
                withParent(isAssignableFrom(ConstraintLayout::class.java)),
                withParent(withId(R.id.createEditIncomeRL))
            )
        ).perform(typeText(sum), closeSoftKeyboard())
    }

    private fun selectKaspiBank() {
        onView(
            allOf(
                isAssignableFrom(ImageButton::class.java),
                withParent(isAssignableFrom(ConstraintLayout::class.java)),
                withParent(withId(R.id.createEditIncomeRL)),
                withId(R.id.kaspiImageView)
                )
        ).check(matches(isDisplayed())).perform(click())
    }

    private fun selectBccBank() {
        onView(
            allOf(
                isAssignableFrom(ImageButton::class.java),
                withParent(isAssignableFrom(ConstraintLayout::class.java)),
                withParent(withId(R.id.createEditIncomeRL)),
                withId(R.id.bccImageView)
            )
        ).check(matches(isDisplayed())).perform(click())
    }
    private fun openDatePickerAndCLickButton(day: String) {
        onView(
            allOf(
                isAssignableFrom(MaterialButton::class.java),
                withId(R.id.incomeDateMaterialBtn),
                withParent(withId(R.id.createEditIncomeRL)),
                withParent(isAssignableFrom(ConstraintLayout::class.java)),
            )
        ).check(matches(isDisplayed())).perform(click())
        onView(withClassName(Matchers.equalTo(DatePicker::class.java.name)))
            .perform(PickerActions.setDate(2025,9,13))
        onView(withText("OK"))
            .inRoot(isDialog())
            .perform(click())
    }
    private fun clickSaveButton() {
        onView(
            allOf(
                isAssignableFrom(Button::class.java),
                withId(R.id.saveIncomeButton),
                withText("Save"),
                withParent(isAssignableFrom(ConstraintLayout::class.java)),
                withParent(withId(R.id.createEditIncomeRL))
            )
        ).check(matches(isDisplayed())).perform(click())
    }

    private fun clickDeleteButton() {
        onView(
            allOf(
                isAssignableFrom(Button::class.java),
                withText("Delete"),
                withId(R.id.deleteIncomeButton),
                withParent(withId(R.id.createEditIncomeRL)),
                withParent(isAssignableFrom(ConstraintLayout::class.java)),

                )
        ).check(matches(isDisplayed())).perform(click())
    }
}