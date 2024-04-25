package com.example.unscramblegame

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf

class InputUi(rootId: Matcher<View>, rootClass: Matcher<View>) {

    private val inputLayoutInteraction = onView(
        allOf(
            withId(R.id.inputLayout),
            isAssignableFrom(TextInputLayout::class.java),
            rootId,
            rootClass
        )
    )

    private val editTextInteraction = onView(
        allOf(
            withId(R.id.inputEditText),
            isAssignableFrom(TextInputEditText::class.java),
        )
    )

    fun checkEmpty() {
        editTextInteraction.check(matches(withText("")))
    }

    fun inputWord(text: String) {
        editTextInteraction.perform(replaceText(text), closeSoftKeyboard())
    }

    fun checkQuestionState() {
        inputLayoutInteraction.check(matches(InputHasErrorMatcher(false)))
    }

    fun checkMatchState() {
        inputLayoutInteraction.check(matches(InputHasErrorMatcher(false)))
    }

    fun checkErrorState() {
        inputLayoutInteraction.check(matches(InputErrorMatcher("Not correct")))
        inputLayoutInteraction.check(matches(InputHasErrorMatcher(true)))
    }
}