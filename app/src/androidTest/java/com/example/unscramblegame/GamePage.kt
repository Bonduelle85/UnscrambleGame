package com.example.unscramblegame

import android.view.View
import android.widget.LinearLayout
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import org.hamcrest.Matcher

class GamePage(
    private val counter: String = "",
    private val word: String = "",
    private val score: String,
) {
    private val rootId: Matcher<View> = withParent(withId(R.id.rootLayout))
    private val rootClass: Matcher<View> = withParent(isAssignableFrom(LinearLayout::class.java))

    private val counterUi = CounterUi(rootId, rootClass)
    private val wordUi = WordUi(rootId, rootClass)
    private val inputUi = InputUi(rootId, rootClass)
    private val submitUi = SubmitUi(rootId, rootClass)
    private val skipUi = SkipUi(rootId, rootClass)
    private val scoreUi = ScoreUi(rootId, rootClass)

    fun checkInputEmpty() {
        inputUi.checkEmpty()
    }

    fun checkQuestionState() {
        counterUi.check(counter)
        wordUi.check(word)
        inputUi.checkQuestionState()
        submitUi.checkQuestionState()
        scoreUi.check(score)
    }

    fun checkMatchState() {
        inputUi.checkMatchState()
        submitUi.checkMatchState()
    }

    fun checkErrorState() {
        inputUi.checkErrorState()
        submitUi.checkErrorState()
    }

    fun inputWord(text: String) {
        inputUi.inputWord(text = text)
    }

    fun clickSubmit() {
        submitUi.click()
    }

    fun clickSkip() {
        skipUi.click()
    }
}