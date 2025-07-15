package com.example.weatherapplication.model

data class ForecastDay(
    val date: String,
    val day: Day,
    val hour: List<Hour>
)
