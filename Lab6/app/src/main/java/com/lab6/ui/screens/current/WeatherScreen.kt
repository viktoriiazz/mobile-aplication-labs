package com.lab6.ui.screens.current

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lab6.ui.components.WeatherMainCustomView
import org.koin.androidx.compose.koinViewModel

@Composable
fun WeatherScreen(
    viewModel: WeatherScreenViewModel = koinViewModel()
) {
    val weatherResponseState = viewModel.weatherResponseStateFlow.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Weather Screen", fontSize = 22.sp, modifier = Modifier.fillMaxWidth())
        Text(
            "Coordinates: lat=${weatherResponseState.value?.coord?.lat} lon=${weatherResponseState.value?.coord?.lon}",
            fontSize = 16.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        )
        weatherResponseState.value?.main?.let { weatherMain ->
            // custom view for WeatherMain object
            WeatherMainCustomView(weatherMain = weatherMain)
        }
    }
}