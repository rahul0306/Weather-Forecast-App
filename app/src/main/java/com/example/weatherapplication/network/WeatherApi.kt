package com.example.weatherapplication.network

import com.example.weatherapplication.model.ForecastResponse
import com.example.weatherapplication.model.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("forecast.json")
    suspend fun getForecastWeather(
        @Query("q") city: String,
        @Query("days") days: Int,
        @Query("key") apiKey: String
    ): ForecastResponse

    @GET("current.json")
    suspend fun getCurrentWeather(
        @Query("q") city: String,
        @Query("key") apiKey: String
    ): WeatherResponse
}