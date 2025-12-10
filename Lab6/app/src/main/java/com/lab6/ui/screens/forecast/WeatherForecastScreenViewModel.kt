package com.lab6.ui.screens.forecast

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lab6.data.ServerApi
import com.lab6.data.entity.response.WeatherForecastResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WeatherForecastScreenViewModel(
    private val serverModule: ServerApi
) : ViewModel() {

    private val _weatherForecastResponseStateFlow = MutableStateFlow<WeatherForecastResponse?>(null)
    val weatherForecastResponseStateFlow: StateFlow<WeatherForecastResponse?>
        get() = _weatherForecastResponseStateFlow

    val cities = listOf("Львів", "Київ", "Дніпро", "Харків", "Рівне", "Тернопіль")

    fun fetchWeatherForecast(city: String) {
        viewModelScope.launch {
            try {
                val weatherForecastResponse = serverModule.getWeatherForecast(city = city)
                _weatherForecastResponseStateFlow.value = weatherForecastResponse
                Log.d("WeatherForecastVM", "Forecast data for $city loaded successfully")
            } catch (e: Exception) {
                Log.e("WeatherForecastVM", "Failed to load forecast data for $city", e)
            }
        }
    }
}
