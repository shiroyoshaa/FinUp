package com.example.finup

import android.widget.ImageButton
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import androidx.test.espresso.matcher.ViewMatchers.withText
import org.hamcrest.Matchers.allOf

class CreateAndEditExpensePage {

    private fun checkVisibleNow(title: String) {
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

}
