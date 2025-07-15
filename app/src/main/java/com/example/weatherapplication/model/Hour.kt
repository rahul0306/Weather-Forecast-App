package com.example.weatherapplication.model

data class Hour(
    val time: String,
    val temp_c: Double,
    val condition: Condition
)
