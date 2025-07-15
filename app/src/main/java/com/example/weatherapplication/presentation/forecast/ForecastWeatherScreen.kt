package com.example.weatherapplication.presentation.forecast

import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Card
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.rememberAsyncImagePainter
import com.example.weatherapplication.presentation.WeatherViewModel
import com.example.weatherapplication.utils.getDay

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ForecastWeatherScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: WeatherViewModel
) {
    val forecastInfo = viewModel.forecast.value
    LaunchedEffect(Unit) {
        viewModel.getForecastWeather()
    }
    if (forecastInfo.nextDays.size >= 2) {
        val tomorrow = forecastInfo.nextDays[1]
        Box(
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(insets = WindowInsets.systemBars)
                .background(
                    color = MaterialTheme.colorScheme.background
                )
                .padding(15.dp)
        ) {
            Box(modifier = Modifier.align(alignment = Alignment.TopStart)) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Image(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back Button",
                        colorFilter = ColorFilter.tint(
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    )
                }
            }
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(top = 10.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Image(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "Calender Icon",
                        colorFilter = ColorFilter.tint(
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = "3 days",
                        fontSize = 27.sp,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
                Spacer(modifier = Modifier.height(50.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(forecastInfo.iconUrl),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth(0.4f)
                            .aspectRatio(1f)
                    )
                    Spacer(modifier = Modifier.width(25.dp))
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = "Tomorrow",
                            color = MaterialTheme.colorScheme.onBackground,
                            fontSize = 22.sp
                        )
                        Spacer(modifier = Modifier.height(15.dp))
                        Text(text = buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    fontSize = 50.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                            ) {
                                append("${tomorrow.high.toInt()}/")
                            }
                            withStyle(
                                style = SpanStyle(
                                    fontWeight = FontWeight.W400,
                                    fontSize = 30.sp,
                                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
                                )
                            ) {
                                append("${tomorrow.low.toInt()}°")
                            }
                        })
                        Spacer(modifier = Modifier.height(7.dp))
                        Text(
                            text = tomorrow.condition.text,
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.4f),
                            fontSize = 17.sp
                        )
                    }
                }
                Spacer(modifier = Modifier.height(50.dp))
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
                            Text(text = "${forecastInfo.wind} km/h", fontSize = 20.sp)
                            Spacer(modifier = Modifier.height(5.dp))
                            Text(text = "Wind", fontSize = 20.sp)
                        }
                        VerticalDivider(modifier = Modifier.fillMaxHeight(0.1f))
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(text = "${forecastInfo.humidity}%", fontSize = 20.sp)
                            Spacer(modifier = Modifier.height(5.dp))
                            Text(text = "Humidity", fontSize = 20.sp)

                        }
                    }
                }
                Spacer(modifier = Modifier.height(50.dp))
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(forecastInfo.nextDays) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = getDay(it.date).toString(),
                                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
                                modifier = Modifier.fillMaxWidth(0.1f)
                            )
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Image(
                                    painter = rememberAsyncImagePainter(it.iconUrl),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .fillMaxWidth(0.15f)
                                        .aspectRatio(1f)
                                )
                                Spacer(modifier = Modifier.width(5.dp))
                                Text(
                                    text = it.condition.text,
                                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
                                )
                            }
                            Text(text = buildAnnotatedString {
                                withStyle(
                                    style = SpanStyle(
                                        color = MaterialTheme.colorScheme.onBackground
                                    )
                                ) {
                                    append("+${it.high.toInt()}° ")
                                }
                                withStyle(
                                    style = SpanStyle(
                                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
                                    )
                                ) {
                                    append("+${it.low.toInt()}°")
                                }
                            })
                        }
                    }
                }
            }
        }
    }
}