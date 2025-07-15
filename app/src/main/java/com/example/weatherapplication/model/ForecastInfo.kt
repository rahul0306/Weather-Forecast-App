package com.example.weatherapplication.model

data class ForecastInfo(
    val iconUrl: String,
    val todayHigh: Double,
    val todayLow: Double,
    val wind: Double,
    val humidity: Int,
    val nextDays: List<DailyForecast>
)
