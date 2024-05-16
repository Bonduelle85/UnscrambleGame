package com.example.unscramblegame

import android.widget.LinearLayout
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import androidx.test.espresso.matcher.ViewMatchers.withParent

class LoadPage {

    private val rootId: Int = R.id.loadLayout
    private val parent = withParent(isAssignableFrom(LinearLayout::class.java))

    private val errorUi = ErrorUi(rootId, parent)
    private val progressUi = ProgressUi(rootId, parent)
    private val retryUi = RetryUi(rootId, parent)

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