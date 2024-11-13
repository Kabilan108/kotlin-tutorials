package com.example.unscramble.ui.test

import com.example.unscramble.data.MAX_NO_OF_WORDS
import com.example.unscramble.data.SCORE_INCREASE
import com.example.unscramble.data.getUnscrambledWord
import com.example.unscramble.ui.GameViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import kotlin.math.exp

class GameViewModelTest {
    private val viewModel = GameViewModel()
    // test methods are executed in isolation.  a new instance of the test class is
    // created each time JUnit runs a test method

    // success path
    @Test
    // thingUnderTest_TriggerOfTest_ResultOfTest
    fun gameViewModel_CorrectWordGuessed_ScoreUpdatedAndErrorFlagUnset() {
        // works for MutableStateFlow
        var currentGameUIState = viewModel.uiState.value
        val correctPlayerWord = getUnscrambledWord(currentGameUIState.currentScrambledWord)

        viewModel.updateUserGuess(correctPlayerWord)
        viewModel.checkUserGuess()

        currentGameUIState = viewModel.uiState.value
        assertFalse(viewModel.isGuessedWordWrong)
        assertEquals(SCORE_AFTER_FIRST_CORRECT_ANSWER, currentGameUIState.score)
    }

    // error path
    @Test
    fun gameViewModel_IncorrectGuess_ErrorFlagSet() {
        val incorrectPlayerWord = "and"

        viewModel.updateUserGuess(incorrectPlayerWord)
        viewModel.checkUserGuess()

        val currentGameUIState = viewModel.uiState.value
        assertEquals(0, currentGameUIState.score)
        assertTrue(viewModel.isGuessedWordWrong)
    }

    // boundary case
    // when GameViewModel is initialized: currentWordCount = 1, score = 0, isGuessedWordWrong = false, isGameOver = false
    @Test
    fun gameViewModel_Initialization_FirstWordLoaded() {
        val gameUIState = viewModel.uiState.value
        val unscrambledWord = getUnscrambledWord(gameUIState.currentScrambledWord)

        // current word is scrambled
        assertNotEquals(unscrambledWord, gameUIState.currentScrambledWord)
        // current word count is set to 1
        assertTrue(gameUIState.currentWordCount == 1)
        // score = 0
        assertTrue(gameUIState.score == 0)
        // wrong word guessed is false
        assertFalse(viewModel.isGuessedWordWrong)
        // game is not over
        assertFalse(gameUIState.isGameOver)
    }

    // boundary case
    // after user has guessed all words, assert that when user guesses correctly:
    // socre is correct
    // currentWordCount is MAX_NO_OF_WORDS
    // isGameOver is true
    @Test
    fun gameViewModel_AllWordsGuessed_UIStateUpdatedCorrectly() {
        var expectedScore = 0
        var currentGameUIState = viewModel.uiState.value
        var correctPlayerWord = getUnscrambledWord(currentGameUIState.currentScrambledWord)

        repeat(MAX_NO_OF_WORDS) {
            expectedScore += SCORE_INCREASE
            viewModel.updateUserGuess(correctPlayerWord)
            viewModel.checkUserGuess()

            currentGameUIState = viewModel.uiState.value
            correctPlayerWord = getUnscrambledWord(currentGameUIState.currentScrambledWord)
            assertEquals(expectedScore, currentGameUIState.score)
        }

        assertEquals(MAX_NO_OF_WORDS, currentGameUIState.currentWordCount)
        assertTrue(currentGameUIState.isGameOver)
    }


    @Test
    fun gameViewModel_WordSkipped_ScoreUnchangedAndWordCountIncreased() {
        var currentState = viewModel.uiState.value
        val correctPlayerWord = getUnscrambledWord(currentState.currentScrambledWord)
        viewModel.updateUserGuess(correctPlayerWord)
        viewModel.checkUserGuess()

        currentState = viewModel.uiState.value
        val lastWordCount = currentState.currentWordCount
        viewModel.skipWord()
        currentState = viewModel.uiState.value

        assertEquals(SCORE_AFTER_FIRST_CORRECT_ANSWER, currentState.score)
        assertEquals(lastWordCount + 1, currentState.currentWordCount)
    }

    companion object {
        private const val SCORE_AFTER_FIRST_CORRECT_ANSWER = SCORE_INCREASE
    }
}