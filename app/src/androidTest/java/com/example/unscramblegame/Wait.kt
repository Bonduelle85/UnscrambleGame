package com.example.unscramblegame

import android.view.View
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers
import org.hamcrest.Matcher

class Wait(
    private val time: Long
) : ViewAction {

    override fun getConstraints(): Matcher<View> {
        return ViewMatchers.isRoot()
    }

    override fun getDescription(): String {
        return "wait $time millis."
    }

    override fun perform(uiController: UiController, rootView: View) {
        uiController.loopMainThreadUntilIdle()
        uiController.loopMainThreadForAtLeast(time)
    }
}