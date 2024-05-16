package com.example.unscramblegame

import android.view.View
import android.widget.TextView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import androidx.test.espresso.matcher.ViewMatchers.withText
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.not
import org.hamcrest.Matcher

class ErrorUi(rootId: Int, parent: Matcher<View>) {

    private val id: Int = R.id.errorTextView

    private val interaction = onView(
        allOf(
            withId(id),
            isAssignableFrom(TextView::class.java),
            withParent(withId(rootId)),
            parent
        )
    )

    fun checkNotVisible() {
        interaction.check(matches(not(isDisplayed())))
    }

    fun waitUntilVisible() {
        onView(isRoot())
            .perform(WaitAction(id, isDisplayed(), 1100))
    }

    fun checkVisible(message: String) {
        interaction.check(matches(withText(message))).check(
            matches(isDisplayed())
        )
    }
}