package com.lab6.ui.screens.forecast

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lab6.ui.components.WeatherMainCustomView
import org.koin.androidx.compose.koinViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun WeatherForecastScreen(
    viewModel: WeatherForecastScreenViewModel = koinViewModel()
) {
    val weatherForecastResponseState = viewModel.weatherForecastResponseStateFlow.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Weather Forecast Screen", fontSize = 22.sp, modifier = Modifier.fillMaxWidth())
        weatherForecastResponseState.value?.list?.let { weatherForecastList ->
            LazyColumn {
                items(weatherForecastList) { weatherForecast ->
                    Text(
                        "Date: ${
                            SimpleDateFormat(
                                "yyyy-MM-dd HH:mm",
                                Locale.getDefault()
                            ).format(Date(weatherForecast.dt * 1000))
                        }",
                        fontSize = 18.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                    // custom view for WeatherMain object
                    WeatherMainCustomView(weatherMain = weatherForecast.main)
                }
            }
        }
    }
}