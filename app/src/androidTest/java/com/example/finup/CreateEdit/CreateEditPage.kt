package com.example.finup.CreateEdit

import android.widget.Button
import android.widget.DatePicker
import android.widget.ImageView
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
import com.example.finup.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import org.hamcrest.Matchers
import org.hamcrest.Matchers.allOf

class CreateEditPage(
    private val title: String,
) {
    private fun title() = onView(
        allOf(
            isAssignableFrom(TextView::class.java),
            withId(mainIds.titleTextView),
            withParent(isAssignableFrom(ConstraintLayout::class.java)),
            withParent(withId(mainIds.editCreateRootLayout)),
            withText(title)
        )
    )

    private fun amountLabel() = onView(
        allOf(
            withId(mainIds.amountLabelTextView),
            withText("total:"),
            isAssignableFrom(TextView::class.java),
            withParent(isAssignableFrom(ConstraintLayout::class.java))
        )
    )

    fun checkVisibleNow() {
        title().check(matches(isDisplayed()))
        amountLabel().check(matches(isDisplayed()))
    }

    fun checkNotVisibleNow() {
        title().check(doesNotExist())
        amountLabel().check(doesNotExist())
    }

    fun clickBackButton() {
        onView(
            allOf(
                isAssignableFrom(ImageView::class.java),
                withId(mainIds.backButton),
                withParent(withId(mainIds.editCreateRootLayout)),
                withParent(isAssignableFrom(ConstraintLayout::class.java)),
            )
        ).perform(click())
    }

    fun inputSum(sum: String) {
        onView(
            allOf(
                isAssignableFrom(TextInputEditText::class.java),
                withId(mainIds.sumInputEditText),
                withParent(isAssignableFrom(ConstraintLayout::class.java)),
                withParent(withId(mainIds.editCreateRootLayout))
            )
        ).perform(typeText(sum), closeSoftKeyboard())
    }

    fun openDatePickerAndCLickButton(month: Int, day: Int) {
        onView(
            allOf(
                isAssignableFrom(MaterialButton::class.java),
                withId(mainIds.openDateButton),
                withParent(withId(mainIds.editCreateRootLayout)),
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
                withId(mainIds.dateTextView),
                withParent(isAssignableFrom(ConstraintLayout::class.java)),
                withText(date),
            )
        ).check(matches(isDisplayed()))
    }

    fun clickSaveButton() {
        onView(
            allOf(
                isAssignableFrom(Button::class.java),
                withId(mainIds.saveButton),
                withText("Save"),
                withParent(isAssignableFrom(ConstraintLayout::class.java)),
                withParent(withId(mainIds.editCreateRootLayout))
            )
        ).check(matches(isDisplayed())).perform(click())
    }

    fun clickDeleteButton() {
        onView(
            allOf(
                isAssignableFrom(Button::class.java),
                withText("Delete"),
                withId(mainIds.deleteButton),
                withParent(withId(mainIds.editCreateRootLayout)),
                withParent(isAssignableFrom(ConstraintLayout::class.java)),

                )
        ).check(matches(isDisplayed())).perform(click())
    }

    private fun selectCategory(categoryText: String, buttonIds: Int, parentId: Int) = onView(
        allOf(
            isAssignableFrom(MaterialButton::class.java),
            withId(buttonIds),
            withParent(isAssignableFrom(ConstraintLayout::class.java)),
            withParent(withId(parentId)),
            withText(categoryText)
        )
    )

    fun clickUtilitiesButton() {
        selectCategory(
            "Utilities",
            expenseCategoryIds.utilitiesButton,
            expenseCategoryIds.gridRootId,
        ).check(matches(isDisplayed())).perform(click())
    }

    fun clickTransfersButton() {
        selectCategory(
            "Transfers",
            expenseCategoryIds.transfersButton,
            expenseCategoryIds.gridRootId,
        ).check(matches(isDisplayed())).perform(click())
    }

    fun clickGroceriesButton() {
        selectCategory(
            "Groceries",
            expenseCategoryIds.groceriesButton,
            expenseCategoryIds.gridRootId,
        ).check(matches(isDisplayed())).perform(click())
    }

    fun clickOtherButton() {
        selectCategory(
            "Other",
            expenseCategoryIds.otherButton,
            expenseCategoryIds.gridRootId,
        ).check(matches(isDisplayed())).perform(click())
    }

    fun selectKaspiBank() {
        selectCategory(
            "Kaspi Bank",
            incomeCategoryIds.kaspiButton,
            incomeCategoryIds.gridRootId,
        ).check(matches(isDisplayed())).perform(click())
    }

    fun selectBccBank() {
        selectCategory(
            "BCC Bank",
            incomeCategoryIds.bccBankButton,
            incomeCategoryIds.gridRootId,
        ).check(matches(isDisplayed())).perform(click())
    }
}

private val mainIds = CreateEditPageIds(
    titleTextView = R.id.titleTextView,
    editCreateRootLayout = R.id.createEditRootLayout,
    amountLabelTextView = R.id.amountLabelTextView,
    backButton = R.id.backButton,
    sumInputEditText = R.id.sumInputEditText,
    openDateButton = R.id.openDateButton,
    dateTextView = R.id.dateTextView,
    saveButton = R.id.saveButton,
    deleteButton = R.id.deleteButton,
)

private val expenseCategoryIds = ExpenseCategoryIds(
    gridRootId = R.id.gridExpensesLayout,
    otherButton = R.id.otherButton,
    transfersButton = R.id.transfersButton,
    groceriesButton = R.id.groceriesButton,
    utilitiesButton = R.id.utilitiesButton,
)
private val incomeCategoryIds = IncomeCategoryIds(
    gridRootId = R.id.gridIncomeLayout,
    kaspiButton = R.id.kaspiButtn,
    bccBankButton = R.id.BccButton,
)
