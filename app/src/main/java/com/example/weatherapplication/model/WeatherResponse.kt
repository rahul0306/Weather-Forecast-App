package com.example.weatherapplication.model

data class WeatherResponse(
    val location: Location,
    val current: Current,
    val forecast: ForecastContainer?
)
