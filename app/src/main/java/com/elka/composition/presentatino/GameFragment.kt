package com.elka.composition.presentatino

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
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
    val args = GameFragmentArgs.fromBundle(requireArguments())
    level = args.level
  }

  private fun launchGameFinishedFragment() {
    val gameResult = viewModel.getGameResult()
    val dir = GameFragmentDirections.actionGameFragmentToGameFinishedFragment(gameResult)
    findNavController().navigate(dir)
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
}