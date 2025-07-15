package com.example.weatherapplication.model

data class DailyForecast(
    val date:String,
    val iconUrl:String,
    val condition:Condition,
    val high: Double,
    val low: Double
)
