package com.elka.composition.presentatino

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.elka.composition.R
import com.elka.composition.databinding.FragmentChoseLevelBinding
import com.elka.composition.domain.entity.Level

class ChoseLevelFragment : Fragment() {
  private lateinit var binding: FragmentChoseLevelBinding

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
  ): View {
    binding = FragmentChoseLevelBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    with(binding) {
      buttonLevelEasy.setOnClickListener { launchGameFragment(Level.EASY) }
      buttonLevelNormal.setOnClickListener { launchGameFragment(Level.NORMAL) }
      buttonLevelHard.setOnClickListener { launchGameFragment(Level.HARD) }
      buttonLevelTest.setOnClickListener { launchGameFragment(Level.TEST) }
    }
  }

  private fun launchGameFragment(level: Level) {
    val dir = ChoseLevelFragmentDirections.actionChoseLevelFragmentToGameFragment(level)
    findNavController().navigate(dir)
  }
}