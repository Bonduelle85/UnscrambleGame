package com.example.unscramblegame

import android.view.View
import android.widget.ProgressBar
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.not
import org.hamcrest.Matcher


class ProgressUi(rootId: Int, parent: Matcher<View>?) {

    private val id: Int = R.id.progressBar

    private val interaction = onView(
        allOf(
            withId(id),
            isAssignableFrom(ProgressBar::class.java),
            withParent(withId(rootId)),
            parent
        )
    )

    fun checkVisible() {
        interaction.check(matches(isDisplayed()))
    }

    fun checkNotVisible() {
        interaction.check(matches(not(isDisplayed())))
    }
}