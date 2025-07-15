package com.example.weatherapplication.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.weatherapplication.BuildConfig
import com.example.weatherapplication.di.AppModule
import com.example.weatherapplication.model.Condition
import com.example.weatherapplication.model.CurrentWeather
import com.example.weatherapplication.model.DailyForecast
import com.example.weatherapplication.model.ForecastInfo
import com.example.weatherapplication.model.HourlyForecast
import com.example.weatherapplication.network.WeatherApi
import kotlinx.coroutines.launch

class WeatherViewModel(
    private val api: WeatherApi
) : ViewModel() {

    private val _currentWeather = mutableStateOf(
        CurrentWeather(
            "--", "", 0.0, 0.0, 0, condition = Condition(
                "", ""
            )
        )
    )
    val currentWeather: State<CurrentWeather> = _currentWeather

    private val _todayForecast = mutableStateOf(emptyList<HourlyForecast>())
    val todayForecast: State<List<HourlyForecast>> = _todayForecast

    private val _forecast = mutableStateOf(ForecastInfo("", 0.0, 0.0, 0.0, 0, emptyList()))
    val forecast: State<ForecastInfo> = _forecast

    private val apiKey = BuildConfig.API_KEY

    fun getCurrentWeather(city: String = "Arlington") {
        viewModelScope.launch {
            val response = api.getForecastWeather(city, 1, apiKey)
            val currentResponse = api.getCurrentWeather(city, apiKey)

            _currentWeather.value = CurrentWeather(
                city = currentResponse.location.name,
                iconUrl = "https:${response.forecast.forecastday.first().day.condition.icon}",
                temperature = response.forecast.forecastday.first().day.avgtemp_c,
                wind = response.forecast.forecastday.first().day.maxwind_kph,
                humidity = response.forecast.forecastday.first().day.avghumidity.toInt(),
                condition = currentResponse.current.condition
            )
            _todayForecast.value = response.forecast.forecastday.firstOrNull()?.hour?.map {
                HourlyForecast(it.time.takeLast(5), it.temp_c, it.condition)
            } ?: emptyList()
        }
    }

    fun getForecastWeather(city: String = "Arlington") {
        viewModelScope.launch {
            val response = api.getForecastWeather(city, 3, apiKey)
            val today = response.forecast.forecastday.first()
            _forecast.value = ForecastInfo(
                iconUrl = "https:${today.day.condition.icon}",
                todayHigh = today.day.maxtemp_c,
                todayLow = today.day.mintemp_c,
                wind = today.day.maxwind_kph,
                humidity = today.day.avghumidity.toInt(),
                nextDays = response.forecast.forecastday.map {
                    DailyForecast(
                        it.date,
                        "https:${it.day.condition.icon}",
                        it.day.condition,
                        it.day.maxtemp_c,
                        it.day.mintemp_c
                    )
                }
            )
        }
    }
}

class WeatherViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return WeatherViewModel(AppModule.api) as T
    }
}