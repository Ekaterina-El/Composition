package com.elka.composition.presentatino

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.elka.composition.databinding.FragmentGameBinding
import com.elka.composition.databinding.FragmentWelcomeBinding

class GameFragment : Fragment() {
  private lateinit var binding: FragmentGameBinding

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = FragmentGameBinding.inflate(inflater, container, false)
    return binding.root
  }
}