package com.example.unscramblegame

import androidx.lifecycle.Lifecycle
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ScenarioTest {

    @get:Rule
    val scenarioRule = ActivityScenarioRule(MainActivity::class.java)

    private lateinit var gamePage: GamePage

    @Before
    fun setup() {
        gamePage = GamePage(
            counter = "1/2",
            word = "animal".reversed(),
            score = "0"
        )
    }

    /**
     * Number 1 : Success Immediately
     *
     * Input correct word
     * submit button became enabled (state Match)
     * Press Submit
     * counter 1/10, word changed, input empty, submit disabled, score : 20 (state question)
     */
    @Test
    fun caseNumber1() {
        gamePage.checkQuestionState()

        gamePage.inputWord(text = "animal")
        gamePage.checkMatchState()

        gamePage.clickSubmit()

        gamePage = GamePage(
            counter = "2/2",
            word = "auto".reversed(),
            score = "20"
        )
        gamePage.checkQuestionState()
    }

    /**
     * Number 2: Success after error
     *
     * Input incorrect word
     * submit button became enabled (State match)
     * Press Submit
     * Input has error , submit disabled (state Error)
     * Input correct word
     * submit button became enabled and input cleared (state match)
     * Press Submit
     * counter 1/10, word changed, input empty and no error, submit not enabled, score 10
     * (state Question)
     *
     */
    @Test
    fun caseNumber2() {
        gamePage.checkQuestionState()

        gamePage.inputWord(text = "aminal")
        gamePage.checkMatchState()

        gamePage.clickSubmit()
        gamePage.checkErrorState()

        gamePage.inputWord(text = "animal")
        gamePage.checkMatchState()

        gamePage.clickSubmit()

        gamePage = GamePage(
            counter = "2/2",
            word = "auto".reversed(),
            score = "10"
        )
        gamePage.checkQuestionState()
    }

    /**
     * Number 3: Skip
     *
     * Press Skip
     * word changed, counter 1/10, score 0 (State Question)
     */
    @Test
    fun caseNumber3() {
        gamePage.checkQuestionState()

        gamePage.clickSkip()
        gamePage = GamePage(
            counter = "2/2",
            word = "auto".reversed(),
            score = "0"
        )
        gamePage.checkQuestionState()
    }

    /**
     * Number 4: GameOver after submit and play again
     *
     * Run Testcase number 1
     * Run TestCase number 2
     * result: GameOver screen : score 30
     * Press play again
     * Word changed, score 0, counter 0/10, input empty
     */
    @Test
    fun caseNumber4() {
        gamePage.checkQuestionState()

        gamePage.inputWord(text = "animal")
        gamePage.checkMatchState()

        gamePage.clickSubmit()

        gamePage = GamePage(
            counter = "2/2",
            word = "auto".reversed(),
            score = "20"
        )
        gamePage.checkQuestionState()

        gamePage.inputWord(text = "auot")
        gamePage.checkMatchState()

        gamePage.clickSubmit()
        gamePage.checkErrorState()

        gamePage.inputWord(text = "auto")
        gamePage.checkMatchState()

        gamePage.clickSubmit()

        gamePage = GamePage(score = "30")
        gamePage.checkGameOverState()

        gamePage.clickPlayAgain()

        gamePage = GamePage(
            counter = "1/2",
            word = "anecdote".reversed(),
            score = "0"
        )
        gamePage.checkQuestionState()
    }

    /**
     * Number 5
     *
     * Input less than required count of letters
     * nothing changed
     * Input correct count
     * submit enabled (state Match)
     * input more letters
     * submit disabled
     */
    @Test
    fun caseNumber5() {
        gamePage.checkQuestionState()

        gamePage.inputWord(text = "anim")
        gamePage.checkQuestionState()

        gamePage.inputWord(text = "animal")
        gamePage.checkMatchState()

        gamePage.inputWord(text = "animalwr")
        gamePage.checkQuestionState()
    }

    /**
     * Number 6: GameOver after submit and exit
     *
     * Run Testcase number 1
     * Run TestCase number 2
     * result: GameOver screen : score 30
     * Press exit
     * not visible to user anymore
     */
    @Test
    fun caseNumber6() {
        gamePage.checkQuestionState()

        gamePage.inputWord(text = "animal")
        gamePage.checkMatchState()

        gamePage.clickSubmit()

        gamePage = GamePage(
            counter = "2/2",
            word = "auto".reversed(),
            score = "20"
        )
        gamePage.checkQuestionState()

        gamePage.inputWord(text = "auot")
        gamePage.checkMatchState()

        gamePage.clickSubmit()
        gamePage.checkErrorState()

        gamePage.inputWord(text = "auto")
        gamePage.checkMatchState()

        gamePage.clickSubmit()

        gamePage = GamePage(score = "30")
        gamePage.checkGameOverState()

        gamePage.clickExit()
        assertTrue(scenarioRule.scenario.state != Lifecycle.State.RESUMED)
    }
}