package com.elka.composition.presentatino

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.elka.composition.data.GameRepositoryImpl
import com.elka.composition.domain.entity.GameResult
import com.elka.composition.domain.entity.GameSettings
import com.elka.composition.domain.entity.Level
import com.elka.composition.domain.entity.Question
import com.elka.composition.domain.usecases.GenerateQuestionUseCase
import com.elka.composition.domain.usecases.GetGameSettingsUseCase

class GameViewModel : ViewModel() {
  private val rep = GameRepositoryImpl
  private val generateQuestionUseCase = GenerateQuestionUseCase(rep)
  private val getGameSettings = GetGameSettingsUseCase(rep)

  private val _gameSettings = MutableLiveData<GameSettings?>(null)
  val gameSettings: LiveData<GameSettings?> get() = _gameSettings

  private val _time = MutableLiveData(0)
  val time: LiveData<Int> get() = _time

  private val _question = MutableLiveData<Question?>(null)
  val question: LiveData<Question?> get() = _question

  private val _countOfQuestions = MutableLiveData(0)
  val countOfQuestions: LiveData<Int> get() = _countOfQuestions

  private val _percentOfRight = MutableLiveData(0)
  val percentOfRight: LiveData<Int> get() = _percentOfRight

  private val _countOfRightAnswers = MutableLiveData(0)
  val countOfRightAnswers: LiveData<Int> get() = _countOfRightAnswers

  fun setupLevel(level: Level) {
    _gameSettings.value = getGameSettings(level)
    _time.value = _gameSettings.value!!.gameTimeInSeconds
    generateNewQuestion()
  }

  private fun generateNewQuestion() {
    _gameSettings.value?.let {
      _question.value = generateQuestionUseCase(it.maxSumValue)
      _countOfQuestions.value = _countOfQuestions.value!! + 1
    }
  }

  fun sendAnswer(userAnswer: Int) {
    _question.value?.let {
      val answer = it.sum - it.visibleNumber
      if (answer == userAnswer) onRightAnswers()
      _percentOfRight.value = (_countOfRightAnswers.value!!.toDouble() / _countOfQuestions.value!!.toDouble() * 100.0).toInt()
      generateNewQuestion()
    }
  }

  private fun onRightAnswers() {
    _countOfRightAnswers.value = _countOfRightAnswers.value!! + 1
  }

  fun updateTime() {
    with(time.value!!) {
      if (this <= 0) return
      _time.value = this - 1
    }
  }

  fun getGameResult() = GameResult(
    countOfQuestions = _countOfQuestions.value!!,
    countOfRightAnswers = _countOfRightAnswers.value!!,
    gameSettings = _gameSettings.value!!,
    winner = (_countOfRightAnswers.value!! >= _gameSettings.value!!.minCountOfRightAnswers) &&
        (_percentOfRight.value!! >= _gameSettings.value!!.minPercentOfRightAnswers)
  )
}