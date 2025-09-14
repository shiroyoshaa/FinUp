package com.example.finup.CreateEdit

import android.widget.ImageButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import com.example.finup.R
import org.hamcrest.Matchers.allOf

class CreateAndEditIncomePage(title: String): CreateEditAbstractPage(title,ids) {

    fun selectKaspiBank() {
        onView(
            allOf(
                isAssignableFrom(ImageButton::class.java),
                withParent(isAssignableFrom(ConstraintLayout::class.java)),
                withParent(withId(R.id.createEditIncomeRL)),
                withId(R.id.kaspiImageView)
            )
        ).check(matches(isDisplayed())).perform(click())
    }

    fun selectBccBank() {
        onView(
            allOf(
                isAssignableFrom(ImageButton::class.java),
                withParent(isAssignableFrom(ConstraintLayout::class.java)),
                withParent(withId(R.id.createEditIncomeRL)),
                withId(R.id.bccImageView)
            )
        ).check(matches(isDisplayed())).perform(click())
    }
}

private val ids = CreateEditPageIds(
    editCreateTextView = R.id.editCreateIncomeTv,
    editCreateRootLayout = R.id.editCreateIncomeRl,
    amountLabelTextView = R.id.incomeAmountLabelTv,
    backButton = R.id.incomeBackButton,
    sumInputEditText = R.id.incomeInputEditText,
    openDateButton = R.id.incomeOpenDateBtn,
    dateTextView = R.id.dateIncomeTextView,
    saveButton = R.id.saveIncomeButton,
    deleteButton = R.id.deleteIncomeButton,
)

