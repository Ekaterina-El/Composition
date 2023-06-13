package com.elka.composition.presentatino

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.elka.composition.R
import com.elka.composition.data.GameRepositoryImpl
import com.elka.composition.databinding.FragmentGameBinding
import com.elka.composition.domain.entity.GameResult
import com.elka.composition.domain.entity.Level

class GameFragment : Fragment() {
  private lateinit var binding: FragmentGameBinding
  private lateinit var level: Level

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
  ): View {
    binding = FragmentGameBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    parseArgs()
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    binding.tvLeftNumber.setOnClickListener {
      launchGameFinishedFragment()
    }
  }

  private fun parseArgs() {
    requireArguments().getParcelable<Level>(LEVEL_KEY)?.let {
      level = it
    }
  }

  private fun launchGameFinishedFragment() {
    val gameResult = GameResult(
      winner = true,
      countOfRightAnswers = 10,
      countOfQuestions = 12,
      gameSettings = GameRepositoryImpl.getGameSettings(level)
    )

    requireActivity().supportFragmentManager.beginTransaction()
      .replace(R.id.container, GameFinishedFragment.getInstance(gameResult))
      .addToBackStack(null)
      .commit()
  }

  companion object {
    const val NAME = "game_fragment"
    private const val LEVEL_KEY = "level"
    fun getInstance(level: Level): GameFragment {
      return GameFragment().apply {
        arguments = Bundle().apply {
          putParcelable(LEVEL_KEY, level)
        }
      }
    }
  }
}