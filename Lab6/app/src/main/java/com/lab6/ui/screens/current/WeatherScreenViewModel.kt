package com.lab6.ui.screens.current

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lab6.data.ServerApi
import com.lab6.data.entity.response.WeatherResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WeatherScreenViewModel(
    private val serverModule: ServerApi
) : ViewModel() {

    val cities = listOf("Львів", "Київ", "Дніпро", "Харків", "Рівне", "Тернопіль")

    private val _weatherResponseStateFlow = MutableStateFlow<WeatherResponse?>(null)
    val weatherResponseStateFlow: StateFlow<WeatherResponse?>
        get() = _weatherResponseStateFlow

    init {
        fetchWeather("Львів")
    }

    fun fetchWeather(city: String) {
        viewModelScope.launch {
            val weatherResponse = serverModule.getCurrentWeather(city = city)
            Log.e("WeatherScreenViewModel", "$weatherResponse")
            _weatherResponseStateFlow.value = weatherResponse
        }
    }
}