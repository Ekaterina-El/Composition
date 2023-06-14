package com.elka.composition.presentatino

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.elka.composition.domain.entity.Level

class GameViewModelFactory(
  private val application: Application,
  private val level: Level,
): ViewModelProvider.Factory {

  override fun <T : ViewModel> create(modelClass: Class<T>): T {
    if (!modelClass.isAssignableFrom(GameViewModel::class.java)) throw RuntimeException("Unknown view model class $modelClass")
    return GameViewModel(application, level) as T
  }
}