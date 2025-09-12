package com.example.finup

import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.hamcrest.Matchers.allOf

abstract class AbstractPage(
    private val ids: PageIds,
    private val mainDateTitle: String,
) {

    private fun recyclerViewMatcher() = RecyclerViewMatcher(recyclerViewId = ids.recyclerViewId)

    private fun monthView() {
        onView(
            allOf(
                isAssignableFrom(TextView::class.java),
                withParent(isAssignableFrom(ConstraintLayout::class.java)),
                withText(mainDateTitle),
                withId(ids.titleMonthTextView),
                withParent(withId(ids.rootId))
            )
        ).check(matches(isDisplayed()))
    }

    private fun leftArrow() = onView(
        allOf(
            isAssignableFrom(ImageButton::class.java),
            withParent(isAssignableFrom(ConstraintLayout::class.java)),
            withId(ids.leftImageButtonId),
            withParent(withId(ids.rootId))
        )
    ).perform(click())

    private fun rightArrow() = onView(
        allOf(
            isAssignableFrom(ImageButton::class.java),
            withParent(isAssignableFrom(ConstraintLayout::class.java)),
            withId(ids.rightImageButtonId),
            withParent(withId(ids.rootId))
        )
    ).perform(click())


    private fun checkNotVisibleNow() {
        onView(
            allOf(
                isAssignableFrom(TextView::class.java),
                withParent(isAssignableFrom(ConstraintLayout::class.java)),
                withText(mainDateTitle),
                withId(ids.titleMonthTextView),
                withParent(withId(ids.rootId))
            )
        ).check(doesNotExist())
    }


    private fun checkDateTitle(position: Int, date: String) {
        onView(
            allOf(
                isAssignableFrom(TextView::class.java),
                recyclerViewMatcher().atPosition(position, ids.dateTextViewId),
                withParent(isAssignableFrom(LinearLayout::class.java)),
                withParent(withId(ids.dateLayoutId))
            )
        ).check(matches(withText(date)))
    }

    private fun clickRecyclerButton(position: Int) {
        onView(
            allOf(
                isAssignableFrom(Button::class.java),
                recyclerViewMatcher().atPosition(position, ids.dateButtonId),
                withParent(isAssignableFrom(LinearLayout::class.java)),
                withParent(withId(ids.dateLayoutId)),
            )
        ).check(matches(isDisplayed())).perform(click())
    }

    private fun checkExpenseTitle() = onView(
        allOf(
            isAssignableFrom(TextView::class.java),
            withParent(isAssignableFrom(LinearLayout::class.java)),
            withId(ids.expenseNameTextView),
        )
    )

    private fun checkExpenseItem(title: String, sum: String, position: Int) {
        checkExpenseTitle().check(matches(withText(title)))
        onView(
            allOf(
                isAssignableFrom(TextView::class.java),
                withParent(withId(ids.expenseListRootLayout)),
                recyclerViewMatcher().atPosition(position, ids.expenseSumTextView),
            )
        ).check(matches(withText(sum)))
    }

    private fun clickAddButton() {
        onView(
            allOf(
                isAssignableFrom(FloatingActionButton::class.java),
                withId(ids.floatingButtonId),
                withParent(isAssignableFrom(ConstraintLayout::class.java)),
                withParent(withId(ids.rootId)),
            )
        ).perform(click())
    }

}

