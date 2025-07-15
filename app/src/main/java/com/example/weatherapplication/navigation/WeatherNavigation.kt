package com.example.weatherapplication.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.weatherapplication.presentation.WeatherViewModel
import com.example.weatherapplication.presentation.WeatherViewModelFactory
import com.example.weatherapplication.presentation.current.CurrentWeatherScreen
import com.example.weatherapplication.presentation.forecast.ForecastWeatherScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WeatherNavigation() {
    val navController = rememberNavController()
    val viewModel: WeatherViewModel = viewModel(factory = WeatherViewModelFactory())
    NavHost(
        navController = navController,
        startDestination = WeatherScreens.CURRENT_WEATHER_SCREEN
    ) {
        composable(route = WeatherScreens.CURRENT_WEATHER_SCREEN) {
            CurrentWeatherScreen(
                navController = navController, viewModel =viewModel
            )
        }
        composable(route = WeatherScreens.FORECAST_WEATHER_SCREEN) {
            ForecastWeatherScreen(navController = navController, viewModel = viewModel)
        }
    }
}