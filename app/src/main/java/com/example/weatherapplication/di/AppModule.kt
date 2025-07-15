package com.example.weatherapplication.di

import com.example.weatherapplication.network.WeatherApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object AppModule {
    private const val BASE_URL = "https://api.weatherapi.com/v1/"

    val api: WeatherApi = Retrofit.Builder()
        .baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
        .build().create(WeatherApi::class.java)
}