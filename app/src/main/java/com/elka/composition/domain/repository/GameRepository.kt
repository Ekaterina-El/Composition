package com.elka.composition.domain.repository

import com.elka.composition.domain.entity.GameSettings
import com.elka.composition.domain.entity.Level
import com.elka.composition.domain.entity.Question

interface GameRepository {
  fun getGameSettings(level: Level): GameSettings
  fun generateQuestion(maxSumValue: Int, countOfOptions: Int): Question
}