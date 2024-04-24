package com.example.unscramblegame

import android.view.View
import androidx.test.espresso.matcher.BoundedMatcher
import com.google.android.material.textfield.TextInputLayout
import org.hamcrest.Description

class InputHasErrorMatcher(private val expectingHasError: Boolean) :
    BoundedMatcher<View, TextInputLayout>(TextInputLayout::class.java) {

    override fun describeTo(description: Description) {
        description.appendText("has error for input")
    }

    override fun matchesSafely(item: TextInputLayout): Boolean {
        val actualHasError = item.isErrorEnabled
        return actualHasError == expectingHasError
    }
}

class InputErrorMatcher(private val expectedError: String) :
    BoundedMatcher<View, TextInputLayout>(TextInputLayout::class.java) {

    override fun describeTo(description: Description) {
        description.appendText("error for input")
    }

    override fun matchesSafely(item: TextInputLayout): Boolean {
        val actualError = item.error
        return actualError == expectedError
    }
}