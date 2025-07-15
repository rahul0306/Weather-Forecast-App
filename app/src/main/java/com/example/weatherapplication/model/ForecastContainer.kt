package com.example.weatherapplication.model

data class ForecastContainer(
    val location: Location,
    val forecastday: List<ForecastDay>
)
