package com.lab6.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lab6.data.entity.WeatherMain

/**
 * WeatherMainCustomView() - custom and reusable compose view for weather data
 * [weatherMain]: WeatherMain - accepts WeatherMain object as parameter
 * - is used on WeatherScreen and WeatherForecastScreen
 */
@Composable
fun WeatherMainCustomView(weatherMain: WeatherMain, modifier: Modifier = Modifier) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            WeatherTextItem(
                label = "Температура",
                value = "${weatherMain.temp} °C"
            )
            WeatherTextItem(
                label = "Відчувається як",
                value = "${weatherMain.feels_like} °C"
            )
            WeatherTextItem(
                label = "Вологість",
                value = "${weatherMain.humidity} %"
            )
            WeatherTextItem(
                label = "Тиск",
                value = "${weatherMain.pressure} мм"
            )
        }
    }
}

@Composable
fun WeatherTextItem(label: String, value: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "$label:",
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = value,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
        )
    }
}

@Preview
@Composable
private fun WeatherMainCustomViewPreview() {
    WeatherMainCustomView(
        weatherMain = WeatherMain(
            temp = 22.0,
            feels_like = 20.0,
            pressure = 1012,
            humidity = 55
        )
    )
}
