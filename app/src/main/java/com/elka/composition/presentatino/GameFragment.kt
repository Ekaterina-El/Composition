package com.elka.composition.presentatino

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.elka.composition.R
import com.elka.composition.databinding.FragmentGameBinding
import com.elka.composition.domain.entity.Level

class GameFragment : Fragment() {
  private lateinit var level: Level

  private val gameViewModelFactory by lazy {
    GameViewModelFactory(requireActivity().application, level)
  }
  private val viewModel by lazy {
    ViewModelProvider(this, gameViewModelFactory)[GameViewModel::class.java]
  }
  private lateinit var binding: FragmentGameBinding

  private val handler by lazy { Handler(Looper.getMainLooper()) }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
  ): View {
    binding = FragmentGameBinding.inflate(inflater, container, false)

    binding.apply {
      lifecycleOwner = viewLifecycleOwner
      viewModel = this@GameFragment.viewModel
    }
    return binding.root
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    parseArgs()
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    viewModel.time.observe(viewLifecycleOwner) {
      if (it != 0) return@observe
      launchGameFinishedFragment()
    }
  }

  override fun onResume() {
    super.onResume()
    startTimer()
  }

  private fun parseArgs() {
    requireArguments().getParcelable<Level>(LEVEL_KEY)?.let {
      level = it
    }
  }

  private fun launchGameFinishedFragment() {
    val gameResult = viewModel.getGameResult()

    requireActivity().supportFragmentManager.beginTransaction()
      .replace(R.id.container, GameFinishedFragment.getInstance(gameResult)).addToBackStack(null)
      .commit()
  }

  override fun onDestroyView() {
    super.onDestroyView()
    stopTimer()
  }

  private var timerIsEnabled = false
  private fun startTimer() {
    timerIsEnabled = true
    updateTimer()
  }

  private fun stopTimer() {
    timerIsEnabled = false
  }

  private fun updateTimer() {
    if (!timerIsEnabled) return

    viewModel.updateTime()

    handler.postDelayed({ updateTimer() }, 1000)
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