package com.example.weatherapplication.model

data class Current(
    val temp_c:Double,
    val wind_kph:Double,
    val humidity:Int,
    val condition:Condition
)
