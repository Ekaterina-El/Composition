package com.elka.composition.presentatino

import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("app:time")
fun printTime(textView: TextView, time: Int) {
  val m = time / 60
  val s = time - m * 60
  textView.text = "${addZero(m)}:${addZero(s)}"
}

fun addZero(n: Int) = if (n < 0) "0$n" else n.toString()