package com.elka.composition.presentatino

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.elka.composition.databinding.FragmentGameFinishedBinding
import com.elka.composition.domain.entity.GameResult

class GameFinishedFragment : Fragment() {
  private lateinit var binding: FragmentGameFinishedBinding
  private lateinit var gameResult: GameResult

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
  ): View {
    binding = FragmentGameFinishedBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    parseArgs()
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
      override fun handleOnBackPressed() { retry() }
    })
    binding.buttonRetry.setOnClickListener { retry() }
  }

  private fun retry() {
    requireActivity().supportFragmentManager.popBackStack(GameFragment.NAME, FragmentManager.POP_BACK_STACK_INCLUSIVE)
  }

  private fun parseArgs() {
    gameResult = requireArguments().getSerializable(GAME_RESULT_KEY) as GameResult
  }

  companion object {
    private const val GAME_RESULT_KEY = "GAME_RESULT_KEY"

    fun getInstance(gameResult: GameResult): GameFinishedFragment {
      return GameFinishedFragment().apply {
        arguments = Bundle().apply {
          putSerializable(GAME_RESULT_KEY, gameResult)
        }
      }
    }
  }
}