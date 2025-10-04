package com.example.finup.main

import android.widget.ImageView
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
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.hamcrest.Matchers.allOf
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.Locale

class ExpenseAndIncomePage(
) {
    private var currentYearMonth = ""

    private fun recyclerViewMatcher() =
        RecyclerViewMatcher(recyclerViewId = pagesIds.recyclerViewId)

    private fun title(title: String) = onView(
        allOf(
            isAssignableFrom(TextView::class.java),
            withParent(isAssignableFrom(ConstraintLayout::class.java)),
            withText(title),
            withId(toolsBar.titleMonthTextView),
            withParent(withId(pagesIds.rootId))
        )
    )

    private fun bottomNav() = onView(
        allOf(
            isAssignableFrom(BottomNavigationView::class.java),
            withId(bottomNav.bottomNavId),
            withParent(isAssignableFrom(ConstraintLayout::class.java)),
            withParent(
                withId(pagesIds.startRootId),
            )
        ))

        fun setDate(year: Int, month: Int) {
            val date = YearMonth.of(year, month)
            currentYearMonth =
                date.format(DateTimeFormatter.ofPattern("MMMM yyyy", Locale.getDefault()))
        }

        fun checkVisibleNow(sum: String) {
            title(currentYearMonth).check(matches(isDisplayed()))
            checkSumVisibleNow(sum).check(matches(isDisplayed()))
            bottomNav().check(matches(isDisplayed()))
        }

                private fun checkSumVisibleNow(sum: String) = onView(
            allOf(
                isAssignableFrom(TextView::class.java),
                withId(toolsBar.titleSumTextView),
                withParent(isAssignableFrom(ConstraintLayout::class.java)),
                withParent(withId(pagesIds.rootId)),
                withText(sum),
            )
        )


        fun checkNotVisibleNow() {
            title(currentYearMonth).check(doesNotExist())
        }

        fun clickLeftArrow() {
            onView(
                allOf(
                    isAssignableFrom(ImageView::class.java),
                    withParent(isAssignableFrom(ConstraintLayout::class.java)),
                    withId(toolsBar.leftImageButtonId),
                    withParent(withId(pagesIds.rootId))
                )
            ).perform(click())
        }

        fun clickRightArrow() {
            onView(
                allOf(
                    isAssignableFrom(ImageView::class.java),
                    withParent(isAssignableFrom(ConstraintLayout::class.java)),
                    withId(toolsBar.rightImageButtonId),
                    withParent(withId(pagesIds.rootId))
                )
            ).perform(click())
        }


                private fun dateTitle(position: Int, title: String) = onView(
            allOf(
                withText(title),
                isAssignableFrom(TextView::class.java),
                recyclerViewMatcher().atPosition(position, header.headerDateTextView),
                withParent(isAssignableFrom(LinearLayout::class.java)),
                withParent(withId(header.headerRootLayout))
            )
        )

                private fun sumByDay(position: Int, sum: String) = onView(
            allOf(
                withText(sum),
                isAssignableFrom(TextView::class.java),
                recyclerViewMatcher().atPosition(position, header.headerTotalSumTextView),
                withParent(
                    isAssignableFrom(LinearLayout::class.java),
                )
            )
        )

        fun checkDateTitleVisible(date: String, position: Int, sum: String) {
            dateTitle(position, date).check(matches(isDisplayed()))
            sumByDay(position, sum).check(matches(isDisplayed()))
        }

        fun checkDateTitleNotVisible(position: Int, title: String, sum: String) {
            dateTitle(position, title).check(doesNotExist())
            sumByDay(position, sum).check(doesNotExist())
        }

                private fun itemTitle(position: Int) = onView(
            allOf(
                isAssignableFrom(TextView::class.java),
                withParent(isAssignableFrom(LinearLayout::class.java)),
                recyclerViewMatcher().atPosition(position, item.itemNameTextView),
            )
        )

                private fun itemSum(position: Int) = onView(
            allOf(
                isAssignableFrom(TextView::class.java),
                withParent(withId(item.itemRootLayout)),
                recyclerViewMatcher().atPosition(position, item.itemSumTextView),
            )
        )

        fun checkItemVisible(title: String, sum: String, position: Int) {
            itemTitle(position).check(matches(withText(title)))
            itemSum(position).check(matches(withText(sum)))
        }

        fun checkItemNotVisible(position: Int) {
            itemTitle(position).check(doesNotExist())
            itemSum(position).check(doesNotExist())
        }

        fun clickCreateButton() {
            onView(
                allOf(
                    isAssignableFrom(FloatingActionButton::class.java),
                    withId(pagesIds.floatingButtonId),
                    withParent(isAssignableFrom(ConstraintLayout::class.java)),
                    withParent(withId(pagesIds.startRootId)),
                )
            ).perform(click())
        }

        fun clickItemAt(position: Int, nameExpense: String) {
            onView(
                allOf(
                    recyclerViewMatcher().atPosition(position, item.itemNameTextView),
                    withText(nameExpense)

                )
            ).perform(click())
        }

        fun clickBottomIncomeIcon() {
            onView(withId(bottomNav.incomeIcon))
                .check(matches(isDisplayed()))
                .perform(click())
        }

        fun clickBottomExpenseIcon() {
            onView(withId(bottomNav.expenseIcon))
                .check(matches(isDisplayed()))
                .perform(click())
        }
}

private val toolsBar = ToolBar(
    titleMonthTextView = R.id.titleMonthTextView,
    titleSumTextView = R.id.titleSumTextView,
    leftImageButtonId = R.id.leftImageViewId,
    rightImageButtonId = R.id.rightImageViewId,
)

private val bottomNav = BottomNav(
    bottomNavId = R.id.bottomNav,
    expenseIcon = R.id.expenseIcon,
    incomeIcon = R.id.incomeIcon,
)

private val header = Header(
    headerRootLayout = R.id.headerRootLayout,
    headerDateTextView = R.id.headerDateTextView,
    headerTotalSumTextView = R.id.headerTotalSumTextView,
)
private val item = Item(
    itemRootLayout = R.id.itemRootLayout,
    itemSumTextView = R.id.itemSumTextView,
    itemNameTextView = R.id.itemNameTextView,
)
private val pagesIds = MainPageIds(
    startRootId = R.id.startRootLayout,
    rootId = R.id.RootLayout,
    floatingButtonId = R.id.addFloatingButton,
    recyclerViewId = R.id.recyclerView,
)