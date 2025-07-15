package com.example.weatherapplication.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
fun getDay(date: String): String? {
    val date = LocalDate.parse(date)
    return date.dayOfWeek.getDisplayName(java.time.format.TextStyle.SHORT, Locale.getDefault())
}