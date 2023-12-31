package com.elka.composition.presentatino

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.elka.composition.databinding.FragmentChoseLevelBinding
import com.elka.composition.databinding.FragmentWelcomeBinding

class WelcomeFragment: Fragment() {
  private lateinit var binding: FragmentWelcomeBinding

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = FragmentWelcomeBinding.inflate(inflater, container, false)
    return binding.root
  }
}