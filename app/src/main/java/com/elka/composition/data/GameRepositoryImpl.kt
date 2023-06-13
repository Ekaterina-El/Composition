package com.elka.composition.data

import com.elka.composition.domain.entity.GameSettings
import com.elka.composition.domain.entity.Level
import com.elka.composition.domain.entity.Level.*
import com.elka.composition.domain.entity.Question
import com.elka.composition.domain.repository.GameRepository
import java.lang.Integer.max
import java.lang.Integer.min
import kotlin.random.Random

object GameRepositoryImpl: GameRepository {
  private const val MIN_SUM_VALUE = 2
  private const val MIN_ANSWER_VALUE = 1

  override fun generateQuestion(maxSumValue: Int, countOfOptions: Int): Question {
    val sum = Random.nextInt(MIN_SUM_VALUE, maxSumValue + 1)
    val visibleNumber = Random.nextInt(MIN_ANSWER_VALUE, sum)
    val rightAnswer = sum - visibleNumber

    val options = HashSet<Int>()
    options.add(rightAnswer)

    val from = max(rightAnswer  - countOfOptions, MIN_ANSWER_VALUE)
    val to = min(maxSumValue, rightAnswer + countOfOptions)

    while (options.size < countOfOptions) {
      options.add(Random.nextInt(from, to))
    }

    return Question(sum, visibleNumber, options.toList())
  }


  override fun getGameSettings(level: Level): GameSettings {
    return when(level) {
      TEST -> GameSettings(
        10,
        3,
        50,
        8
      )
      EASY -> GameSettings(
        10,
        10,
        70,
        60
      )
      NORMAL -> GameSettings(
        20,
        10,
        80,
        40
      )
      HARD -> GameSettings(
        30,
        30,
        90,
        40
      )
    }
  }

}