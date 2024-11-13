package com.example.unscramble.ui

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.unscramble.data.MAX_NO_OF_WORDS
import com.example.unscramble.data.SCORE_INCREASE
import com.example.unscramble.data.allWords
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class GameUIState(
    val currentScrambledWord: String = "",
    val userGuess: String = "",
    val score: Int = 0,
    val currentWordCount: Int = 1,
    val isGameOver: Boolean = false,
)

const val TAG = "GameViewModel"

// StateFlow -> data holder observable flow that emits the current and new state updates
// `.value` reflects current state value
// to update state, assign a new value to `MutableStateFlow.value`
// expose StateFlow from GameUIState so composables can listen for UI state updates
// and make the state survive config changes


// backing property -> return something from a getter that is not the exact object
// define a private prop (only accessible within class)
// define a public prop and set its getter to return the private prop
// this way -> state variables are not changed outsize the state

// data in viewmodel is not destroyed on configuration changes
// state is automatically retained
class GameViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(GameUIState())
    val uiState: StateFlow<GameUIState> = _uiState.asStateFlow()

    private lateinit var currentWord: String
    private var usedWords: MutableSet<String> = mutableSetOf()

    // public prop -> needs to be observable
//    var userGuess by mutableStateOf("")
//        private set

    var isGuessedWordWrong by mutableStateOf(false)
        private set

    init {
        resetGame()
    }

    private fun pickRandomWordAndShuffle(): String {
        // continue picking a random word until you get one that hasn't been used
        currentWord = allWords.random()
        if (usedWords.contains(currentWord)) {
            return pickRandomWordAndShuffle()
        } else {
            usedWords.add(currentWord)
//            Log.d(TAG, "correct word: $currentWord")
            return shuffleCurrentWord(currentWord)
        }
    }

    private fun shuffleCurrentWord(word: String): String {
        val tempWord = word.toCharArray()
        tempWord.shuffle()
        while (String(tempWord) == word) {
            tempWord.shuffle()
        }
        return String(tempWord)
    }

    private fun updateGameState(updatedScore: Int) {
        if (usedWords.size == MAX_NO_OF_WORDS) {
            _uiState.update { currentState ->
                currentState.copy(
                    userGuess = "",
                    score = updatedScore,
                    isGameOver = true
                )
            }
        } else {
            _uiState.update { currentState ->
                currentState.copy(
                    userGuess = "",
                    currentScrambledWord = pickRandomWordAndShuffle(),
                    score = updatedScore,
                    currentWordCount = currentState.currentWordCount.inc()
                )
            }
        }
    }

    // public API
    fun resetGame() {
        usedWords.clear()
        _uiState.value = GameUIState(currentScrambledWord = pickRandomWordAndShuffle())
    }

    fun updateUserGuess(guessedWord: String) {
        // using a mutableState prop
//        userGuess = guessedWord

        // using the uiState
        _uiState.update { currentState ->
            currentState.copy(userGuess = guessedWord)
        }
    }

    fun checkUserGuess() {
        if (_uiState.value.userGuess.equals(currentWord, ignoreCase = true)) {
//            Log.d(TAG, "guess is correct")
            isGuessedWordWrong = false
            val updatedScore = _uiState.value.score.plus(SCORE_INCREASE)
            updateGameState(updatedScore)
        } else {
//            Log.d(TAG, "guess is wrong")
            isGuessedWordWrong = true
            updateUserGuess("")
        }
    }

    fun skipWord() {
        isGuessedWordWrong = false
        updateGameState(_uiState.value.score)
    }
}