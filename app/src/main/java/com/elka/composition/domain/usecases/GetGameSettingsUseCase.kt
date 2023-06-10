package com.elka.composition.domain.usecases

import com.elka.composition.domain.entity.GameSettings
import com.elka.composition.domain.entity.Level
import com.elka.composition.domain.repository.GameRepository

class GetGameSettingsUseCase(private val repo: GameRepository) {
  operator fun invoke(level: Level): GameSettings {
    return repo.getGameSettings(level)
  }
}