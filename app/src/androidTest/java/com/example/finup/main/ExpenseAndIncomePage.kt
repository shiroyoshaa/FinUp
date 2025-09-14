package com.example.finup.main

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
import com.example.finup.R
import com.example.finup.RecyclerViewMatcher
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.hamcrest.Matchers.allOf
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.Locale

class ExpenseAndIncomePage(

    private val ids: MainPageIds = pageIds
) {
    private var currentYearMonth = ""

    private fun recyclerViewMatcher() = RecyclerViewMatcher(recyclerViewId = ids.recyclerViewId)
    private fun title(title: String) = onView(
        allOf(
            isAssignableFrom(TextView::class.java),
            withParent(isAssignableFrom(ConstraintLayout::class.java)),
            withText(title),
            withId(ids.titleMonthTextView),
            withParent(withId(ids.rootId))
        )
    )

    fun setDate(year: Int, month: Int) {
        val date = YearMonth.of(year, month)
        currentYearMonth =
            date.format(DateTimeFormatter.ofPattern("MMMM yyyy", Locale.getDefault()))
    }

    fun checkVisibleNow() {
        title(currentYearMonth).check(matches(isDisplayed()))
    }

    fun checkNotVisibleNow() {
        title(currentYearMonth).check(doesNotExist())
    }

    fun clickLeftArrow() {
        onView(
            allOf(
                isAssignableFrom(ImageButton::class.java),
                withParent(isAssignableFrom(ConstraintLayout::class.java)),
                withId(ids.leftImageButtonId),
                withParent(withId(ids.rootId))
            )
        ).perform(click())
    }

    fun clickRightArrow() {
        onView(
            allOf(
                isAssignableFrom(ImageButton::class.java),
                withParent(isAssignableFrom(ConstraintLayout::class.java)),
                withId(ids.rightImageButtonId),
                withParent(withId(ids.rootId))
            )
        ).perform(click())
    }

    private fun dateTitle(position: Int) = onView(
        allOf(
            isAssignableFrom(TextView::class.java),
            recyclerViewMatcher().atPosition(position, ids.itemDateTextView),
            withParent(isAssignableFrom(LinearLayout::class.java)),
            withParent(withId(ids.itemLayout))
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
                recyclerViewMatcher().atPosition(position, ids.itemButton),
                withParent(isAssignableFrom(LinearLayout::class.java)),
                withParent(withId(ids.itemLayout)),
            )
        ).check(matches(isDisplayed())).perform(click())
    }

    private fun itemTitle(position: Int) = onView(
        allOf(
            isAssignableFrom(TextView::class.java),
            withParent(isAssignableFrom(LinearLayout::class.java)),
            recyclerViewMatcher().atPosition(position, ids.itemNameTextView),
        )
    )

    private fun itemSum(position: Int) = onView(
        allOf(
            isAssignableFrom(TextView::class.java),
            withParent(withId(ids.itemRootLayout)),
            recyclerViewMatcher().atPosition(position, ids.itemSumTextView),
        )
    )

    fun checkItemVisible(title: String, sum: String, position: Int) {
        itemTitle(position).check(matches(withText(title)))
        itemSum(position).check(matches(withText(sum)))
    }

    fun checkItemNotVisible(title: String, sum: String, position: Int) {
        itemTitle(position).check(doesNotExist())
        itemSum(position).check(doesNotExist())
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

    fun clickItemAt(position: Int, nameExpense: String) {
        onView(
            allOf(
                recyclerViewMatcher().atPosition(0, ids.itemNameTextView),
                withText(nameExpense)

            )
        ).perform(click())
    }

}

private val pageIds = MainPageIds(
    rootId = R.id.expenseRootLayout,
    titleMonthTextView = R.id.titleMonthTextView,
    floatingButtonId = R.id.FloatingBtn,
    leftImageButtonId = R.id.LeftImageBtn,
    rightImageButtonId = R.id.RightImageBtn,
    recyclerViewId = R.id.itemRecyclerView,
    itemLayout = R.id.itemDateLayout,
    itemDateTextView = R.id.itemDateTextView,
    itemButton = R.id.dateButtonId,
    itemRootLayout = R.id.itemListRootLayout,
    itemSumTextView = R.id.itemSumTextView,
    itemNameTextView = R.id.itemNameTextView,
)
