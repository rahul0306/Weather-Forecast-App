package com.example.weatherapplication.model

data class CurrentWeather(
    val city: String,
    val iconUrl: String,
    val temperature: Double,
    val wind: Double,
    val humidity: Int,
    val condition: Condition
)
