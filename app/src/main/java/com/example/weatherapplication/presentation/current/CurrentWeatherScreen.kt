package com.example.weatherapplication.presentation.current

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.rememberAsyncImagePainter
import com.example.weatherapplication.navigation.WeatherScreens
import com.example.weatherapplication.presentation.WeatherViewModel

@Composable
fun CurrentWeatherScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: WeatherViewModel
) {
    val scrollState = rememberScrollState()
    val weather = viewModel.currentWeather.value
    val forecast = viewModel.todayForecast.value
    val search = remember { mutableStateOf(false) }
    val newCity = remember { mutableStateOf("") }
    LaunchedEffect(Unit) {
        viewModel.getCurrentWeather()
    }
    Box(
        modifier = modifier
            .fillMaxSize()
            .windowInsetsPadding(insets = WindowInsets.systemBars)
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(15.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    Icons.Default.LocationOn,
                    contentDescription = "Location",
                    colorFilter = ColorFilter.tint(
                        color = MaterialTheme.colorScheme.onBackground
                    )
                )
                Spacer(
                    modifier = Modifier.width(5.dp)
                )
                TextButton(onClick = {
                    search.value = true
                    newCity.value = weather.city
                }) {
                    Text(
                        text = weather.city,
                        fontSize = 25.sp,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
            Image(
                painter = rememberAsyncImagePainter(weather.iconUrl),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .aspectRatio(1f),
                contentScale = ContentScale.Fit
            )
            Text(
                text = "${weather.temperature.toInt()}°",
                fontSize = 100.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = "${weather.condition.text}°",
                fontSize = 30.sp,
                fontWeight = FontWeight.W300,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(15.dp))
            Card(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "${weather.wind.toInt()} km/h", fontSize = 20.sp)
                        Spacer(modifier = Modifier.height(5.dp))
                        Text(text = "Wind", fontSize = 20.sp)
                    }
                    VerticalDivider(modifier = Modifier.fillMaxHeight(0.1f))
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "${weather.humidity}%", fontSize = 20.sp)
                        Spacer(modifier = Modifier.height(5.dp))
                        Text(text = "Humidity", fontSize = 20.sp)

                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Today",
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
                TextButton(onClick = { navController.navigate(route = WeatherScreens.FORECAST_WEATHER_SCREEN) }) {
                    Text(
                        text = "3 Days ＞",
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
            LazyRow {
                items(forecast) { hour ->
                    Card(
                        modifier = Modifier
                            .wrapContentHeight()
                            .padding(7.dp),
                        shape = RoundedCornerShape(20.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(20.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(text = "${hour.temp_c.toInt()}")
                            Image(
                                rememberAsyncImagePainter("https:${hour.condition.icon}"),
                                contentDescription = "Condition Image",
                                contentScale = ContentScale.Fit,
                                modifier = Modifier.size(50.dp)
                            )
                            Text(text = hour.time)
                        }
                    }
                }
            }
        }
    }
    if (search.value) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background.copy(alpha = 0.5f)),
            contentAlignment = Alignment.Center
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.onBackground
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextField(
                        value = newCity.value,
                        onValueChange = { newCity.value = it },
                        label = {
                            Text(
                                text = "Enter location",
                                color = MaterialTheme.colorScheme.background,
                                fontSize = 15.sp
                            )
                        },
                        singleLine = true,
                        modifier = Modifier.weight(1f),
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Color.Transparent,
                            focusedContainerColor = Color.Transparent,
                            unfocusedTextColor = MaterialTheme.colorScheme.background,
                            focusedTextColor = MaterialTheme.colorScheme.background
                        ),
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    TextButton(onClick = {
                        if (newCity.value.isNotBlank()) {
                            viewModel.getCurrentWeather(city = newCity.value)
                            search.value = false
                        }
                    }) {
                        Text(text = "Update", color = MaterialTheme.colorScheme.background)
                    }
                }
            }
        }
    }
}