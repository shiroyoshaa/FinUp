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

    private fun title() = onView(
        allOf(
            isAssignableFrom(TextView::class.java),
            withParent(isAssignableFrom(ConstraintLayout::class.java)),
            withText(mainDateTitle),
            withId(ids.titleMonthTextView),
            withParent(withId(ids.rootId))
        )
    )


    fun checkVisibleNow() {
        title().check(matches(isDisplayed()))
    }

    fun checkNotVisibleNow() {
        title().check(doesNotExist())
    }

    fun leftArrow() = onView(
        allOf(
            isAssignableFrom(ImageButton::class.java),
            withParent(isAssignableFrom(ConstraintLayout::class.java)),
            withId(ids.leftImageButtonId),
            withParent(withId(ids.rootId))
        )
    ).perform(click())

    fun rightArrow() = onView(
        allOf(
            isAssignableFrom(ImageButton::class.java),
            withParent(isAssignableFrom(ConstraintLayout::class.java)),
            withId(ids.rightImageButtonId),
            withParent(withId(ids.rootId))
        )
    ).perform(click())


    private fun dateTitle(position: Int) = onView(
            allOf(
                isAssignableFrom(TextView::class.java),
                recyclerViewMatcher().atPosition(position, ids.itemDateTextView),
                withParent(isAssignableFrom(LinearLayout::class.java)),
                withParent(withId(ids.expenseItemLayout))
            )
        )

    fun checkDateTitleVisible(date: String, position: Int) {
        dateTitle(position).check(matches(withText(date)))
    }

    fun checkDateTitleNoteVisible(date: String, position: Int) {
        dateTitle(position).check(doesNotExist())
    }
    fun clickRecyclerButton(position: Int) {
        onView(
            allOf(
                isAssignableFrom(Button::class.java),
                recyclerViewMatcher().atPosition(position, ids.expenseItemButton),
                withParent(isAssignableFrom(LinearLayout::class.java)),
                withParent(withId(ids.expenseItemLayout)),
            )
        ).check(matches(isDisplayed())).perform(click())
    }

    private fun expenseTitle(position: Int) = onView(
        allOf(
            isAssignableFrom(TextView::class.java),
            withParent(isAssignableFrom(LinearLayout::class.java)),
            recyclerViewMatcher().atPosition(position, ids.expenseNameTextView),
        )
    )

    private fun expenseItem(position: Int) = onView(
        allOf(
            isAssignableFrom(TextView::class.java),
            withParent(withId(ids.expenseListRootLayout)),
            recyclerViewMatcher().atPosition(position, ids.expenseSumTextView),
        )
    )

    fun checkExpenseItemVisible(title: String, sum: String, position: Int) {
        expenseTitle(position).check(matches(withText(title)))
        expenseItem(position).check(matches(withText(sum)))
    }

    fun checkExpenseItemNotVisible(title: String, sum: String, position: Int) {
        expenseTitle(position).check(doesNotExist())
        expenseItem(position).check(doesNotExist())
    }

    fun clickCreateButton() {
        onView(
            allOf(
                isAssignableFrom(FloatingActionButton::class.java),
                withId(ids.floatingButtonId),
                withParent(isAssignableFrom(ConstraintLayout::class.java)),
                withParent(withId(ids.rootId)),
            )
        ).perform(click())
    }

    fun clickExpenseAt(position: Int) {
        onView(recyclerViewMatcher().atPosition(0, ids.expenseNameTextView)).perform(click())
    }
}

