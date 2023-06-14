package com.elka.composition.presentatino

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.ColorFilter
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.BindingAdapter

@SuppressLint("SetTextI18n")
@BindingAdapter("app:time")
fun printTime(textView: TextView, time: Int) {
  val m = time / 60
  val s = time - m * 60
  textView.text = "${addZero(m)}:${addZero(s)}"
}

@BindingAdapter("app:progressColor", "app:minProgress")
fun updateProgressColor(progress: ProgressBar, percentOfRight: Int, minProgress: Int) {
  val colorRes = if (percentOfRight >= minProgress)
    android.R.color.holo_green_light else android.R.color.holo_red_light
  val color = progress.context.getColor(colorRes)

  progress.progressDrawable.setColorFilter(color, android.graphics.PorterDuff.Mode.SRC_IN)
}

fun addZero(n: Int) = if (n < 10) "0$n" else n.toString()