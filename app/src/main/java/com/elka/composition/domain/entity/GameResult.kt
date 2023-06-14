package com.elka.composition.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GameResult(
  val countOfRightAnswers: Int,
  val countOfQuestions: Int,
  val gameSettings: GameSettings,
  val percentOfRightAnswer: Int
) : Parcelable {
  val winner: Boolean
    get() = (countOfRightAnswers >= gameSettings.minCountOfRightAnswers)
          && (percentOfRightAnswer >= gameSettings.minPercentOfRightAnswers)
}