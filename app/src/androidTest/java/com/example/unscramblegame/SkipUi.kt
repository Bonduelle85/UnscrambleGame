package com.example.unscramblegame

import android.view.View
import android.widget.Button
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf

class SkipUi(rootId: Matcher<View>, rootClass: Matcher<View>) {

    private val interaction = onView(
        allOf(
            withId(R.id.skipButton),
            withText(R.string.skip),
            isAssignableFrom(Button::class.java),
            rootId,
            rootClass,
        )
    )

    fun click() {
        interaction.perform(androidx.test.espresso.action.ViewActions.click())
    }
}