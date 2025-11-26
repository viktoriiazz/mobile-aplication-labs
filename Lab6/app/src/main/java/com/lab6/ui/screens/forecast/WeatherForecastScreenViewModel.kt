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

    init {
        viewModelScope.launch {
            /**
             * serverModule.getWeatherForecast(...) - call to api to get weather forecast
             * - return WeatherForecastResponse object
             * - must be in coroutine (parallel thread) viewModelScope.launch {...here}
             */
            val weatherForecastResponse = serverModule.getWeatherForecast(
                lat = 50.4851493,
                lon = 30.4721233,
            )
            Log.e("WeatherForecastScreenViewModel", "$weatherForecastResponse")
            _weatherForecastResponseStateFlow.value = weatherForecastResponse
        }
    }
}