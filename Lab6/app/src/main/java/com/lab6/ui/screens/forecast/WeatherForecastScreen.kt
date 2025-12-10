package com.lab6.ui.screens.forecast

import androidx.compose.ui.platform.LocalContext
import android.app.DatePickerDialog
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lab6.ui.components.WeatherMainCustomView
import org.koin.androidx.compose.getViewModel
import java.text.SimpleDateFormat
import java.util.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
@Composable
fun WeatherForecastScreen(
    viewModel: WeatherForecastScreenViewModel = getViewModel()
) {
    val context = LocalContext.current
    val weatherForecastResponseState = viewModel.weatherForecastResponseStateFlow.collectAsState()
    var expanded by remember { mutableStateOf(false) }
    var selectedCity by remember { mutableStateOf(viewModel.cities.first()) }
    var selectedDate by remember { mutableStateOf<Date?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(13.dp)
    ) {
        Text(
            text = "Прогноз",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(16.dp))


        Box(modifier = Modifier.fillMaxWidth()) {
            Button(
                onClick = { expanded = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(100.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text(
                    text = "Місто - $selectedCity",
                    color = Color.White,
                    style = MaterialTheme.typography.titleMedium
                )
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                viewModel.cities.forEach { city ->
                    DropdownMenuItem(
                        onClick = {
                            selectedCity = city
                            expanded = false
                            viewModel.fetchWeatherForecast(city)
                        },
                        text = { Text(city, fontSize = 18.sp) }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                val calendar = Calendar.getInstance()
                DatePickerDialog(
                    context,
                    { _, year, month, day ->
                        selectedDate = Calendar.getInstance().apply {
                            set(year, month, day)
                        }.time
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
                ).show()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(100.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Text(
                text = " ${
                    selectedDate?.let {
                        SimpleDateFormat(
                            "yyyy-MM-dd",
                            Locale.getDefault()
                        ).format(it)
                    } ?: "Виберіть дату"
                }",
                color = Color.White,
                style = MaterialTheme.typography.titleMedium
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        weatherForecastResponseState.value?.list?.let { weatherForecastList ->
            val filteredForecasts = selectedDate?.let { date ->
                val selectedDateString =
                    SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(date)
                weatherForecastList.filter {
                    SimpleDateFormat(
                        "yyyy-MM-dd",
                        Locale.getDefault()
                    ).format(Date(it.dt * 1000)) == selectedDateString
                }
            } ?: weatherForecastList

            LazyColumn {
                items(filteredForecasts) { weatherForecast ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 2.dp, horizontal = 16.dp)
                    ) {
                        Text(
                            text = "Дата: ${
                                SimpleDateFormat(
                                    "yyyy-MM-dd",
                                    Locale.getDefault()
                                ).format(Date(weatherForecast.dt * 1000))
                            }",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 4.dp)
                        )

                        Text(
                            text = "Час: ${
                                SimpleDateFormat("HH:mm", Locale.getDefault()).format(
                                    Date(weatherForecast.dt * 1000)
                                )
                            }",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    WeatherMainCustomView(weatherMain = weatherForecast.main)
                }
            }
        }
    }
}