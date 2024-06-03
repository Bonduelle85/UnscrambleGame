package com.example.unscramblegame.ui

import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.example.unscramblegame.R
import org.hamcrest.Matchers.allOf

class GameOverPage(
    private val score: String,
    private val correct: String,
    private val incorrect: String,
    private val skips: String,
) {

    private val rootId: Int = R.id.gameOverLayout

    private val titleInteraction = onView(
        allOf(
            withId(R.id.gameOverTitleTextView),
            withText(R.string.congratulations),
            isAssignableFrom(TextView::class.java),
            withParent(withId(rootId)),
            withParent(isAssignableFrom(ConstraintLayout::class.java)),
        )
    )

    private val scoreInteraction = onView(
        allOf(
            withId(R.id.gameOverScoreTextView),
            isAssignableFrom(TextView::class.java),
            withParent(withId(rootId)),
            withParent(isAssignableFrom(ConstraintLayout::class.java)),
        )
    )

    fun check() {
        val text = "Score: $score\nCorrect: $correct\nIncorrect: $incorrect\nSkips: $skips"
        titleInteraction.check(matches(isDisplayed()))
        scoreInteraction.check(matches(withText(text)))
    }

    fun clickPlayAgain() {
        onView(
            allOf(
                withId(R.id.playAgainButton),
                withText(R.string.play_again),
                isAssignableFrom(Button::class.java),
                withParent(withId(rootId)),
                withParent(isAssignableFrom(ConstraintLayout::class.java)),
            )
        ).perform(click())
    }

    fun clickExit() {
        onView(
            allOf(
                withId(R.id.exitButton),
                withText(R.string.exit),
                isAssignableFrom(Button::class.java),
                withParent(withId(rootId)),
                withParent(isAssignableFrom(ConstraintLayout::class.java)),
            )
        ).perform(click())
    }
}