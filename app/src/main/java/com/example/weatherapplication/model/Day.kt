package com.example.weatherapplication.model

data class Day(
    val maxtemp_c: Double,
    val mintemp_c: Double,
    val avgtemp_c: Double,
    val maxwind_kph: Double,
    val avghumidity: Double,
    val condition: Condition
)
