package com.example.unscramblegame.ui

import android.view.View
import android.widget.Button
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isEnabled
import androidx.test.espresso.matcher.ViewMatchers.isNotEnabled
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.example.unscramblegame.R
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf

class SubmitUi(rootId: Matcher<View>, rootClass: Matcher<View>) {

    private val interaction = onView(
        allOf(
            withId(R.id.submitButton),
            withText(R.string.submit),
            isAssignableFrom(Button::class.java),
            rootId,
            rootClass,
        )
    )

    fun checkQuestionState() {
        interaction.check(matches(isNotEnabled()))
    }

    fun checkInsufficientState() {
        interaction.check(matches(isNotEnabled()))
    }

    fun checkMatchState() {
        interaction.check(matches(isEnabled()))
    }

    fun click() {
        interaction.perform(androidx.test.espresso.action.ViewActions.click())
    }

    fun checkErrorState() {
        interaction.check(matches(isNotEnabled()))
    }
}