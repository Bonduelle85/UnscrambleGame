package com.example.unscramblegame.ui

import android.view.View
import android.widget.LinearLayout
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import androidx.test.espresso.matcher.ViewMatchers.withParent
import com.example.unscramblegame.R
import org.hamcrest.Matcher

class LoadPage {

    private val rootId: Matcher<View> = withParent(ViewMatchers.withId(R.id.gameLayout))
    private val parentClass: Matcher<View> = withParent(isAssignableFrom(LinearLayout::class.java))

    private val errorUi = ErrorUi(rootId, parentClass)
    private val progressUi = ProgressUi(rootId, parentClass)
    private val retryUi = RetryUi(rootId, parentClass)

    fun checkProgressState() {
        progressUi.checkVisible()
        errorUi.checkNotVisible()
        retryUi.checkNotVisible()
    }

    fun waitUntilError() {
        errorUi.waitUntilVisible()
    }

    fun checkErrorState(message: String) {
        progressUi.checkNotVisible()
        retryUi.checkVisible()
        errorUi.checkVisible(message = message)
    }

    fun clickRetry() {
        retryUi.click()
    }

    fun waitUntilDisappear() {
        onView(isRoot()).perform(Wait(1100))
    }
}