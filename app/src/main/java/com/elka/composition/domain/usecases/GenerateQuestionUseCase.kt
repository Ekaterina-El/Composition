package com.elka.composition.domain.usecases

import com.elka.composition.domain.entity.Question
import com.elka.composition.domain.repository.GameRepository

class GenerateQuestionUseCase(private val rep: GameRepository) {
  operator fun invoke(maxSumValue: Int, countOfOptions: Int): Question =
    rep.generateQuestion(maxSumValue, COUNT_OF_OPTIONS)

  companion object {
    private const val COUNT_OF_OPTIONS = 6
  }
}