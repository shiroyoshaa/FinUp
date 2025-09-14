package com.example.finup.CreateEdit

import android.widget.Button
import android.widget.DatePicker
import android.widget.ImageButton
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

abstract class CreateEditAbstractPage(
    private val title: String,
    private val ids: CreateEditPageIds,
) {
    private fun title() = onView(
        allOf(
            isAssignableFrom(TextView::class.java),
            withId(ids.editCreateTextView),
            withParent(isAssignableFrom(ConstraintLayout::class.java)),
            withParent(withId(ids.editCreateRootLayout)),
            withText(title)
        )
    )

    private fun amountLabel() = onView(
        allOf(
            withId(ids.amountLabelTextView),
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
                withId(ids.backButton),
                withParent(withId(ids.editCreateRootLayout)),
                withParent(isAssignableFrom(ConstraintLayout::class.java)),
            )
        ).perform(click())
    }

    fun inputSum(sum: String) {
        onView(
            allOf(
                isAssignableFrom(TextInputEditText::class.java),
                withId(ids.sumInputEditText),
                withParent(isAssignableFrom(ConstraintLayout::class.java)),
                withParent(withId(ids.editCreateRootLayout))
            )
        ).perform(typeText(sum), closeSoftKeyboard())
    }

    fun openDatePickerAndCLickButton(month: Int, day: Int) {
        onView(
            allOf(
                isAssignableFrom(MaterialButton::class.java),
                withId(ids.openDateButton),
                withParent(withId(ids.editCreateRootLayout)),
                withParent(isAssignableFrom(ConstraintLayout::class.java)),
            )
        ).check(matches(isDisplayed())).perform(click())
        onView(withClassName(Matchers.equalTo(DatePicker::class.java.name)))
            .perform(PickerActions.setDate(2025, month, day))
        onView(withText("OK"))
            .inRoot(isDialog())
            .perform(click())
    }

    fun checkDateVisibleNow(date: String) {
        onView(
            allOf(
                isAssignableFrom(TextView::class.java),
                withId(ids.dateTextView),
                withParent(isAssignableFrom(ConstraintLayout::class.java)),
                withText(date),
            )
        ).check(matches(isDisplayed()))
    }

    fun clickSaveButton() {
        onView(
            allOf(
                isAssignableFrom(Button::class.java),
                withId(ids.saveButton),
                withText("Save"),
                withParent(isAssignableFrom(ConstraintLayout::class.java)),
                withParent(withId(ids.editCreateRootLayout))
            )
        ).check(matches(isDisplayed())).perform(click())
    }

    fun clickDeleteButton() {
        onView(
            allOf(
                isAssignableFrom(Button::class.java),
                withText("Delete"),
                withId(ids.deleteButton),
                withParent(withId(ids.editCreateRootLayout)),
                withParent(isAssignableFrom(ConstraintLayout::class.java)),

                )
        ).check(matches(isDisplayed())).perform(click())
    }
}
