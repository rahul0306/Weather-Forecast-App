package com.example.weatherapplication.model

data class HourlyForecast(
    val time: String,
    val temp_c: Double,
    val condition: Condition
)
