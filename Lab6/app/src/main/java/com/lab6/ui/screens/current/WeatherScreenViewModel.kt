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
    private val serverModule: ServerApi // Server module - communication interface with API
) : ViewModel() {

    private val _weatherResponseStateFlow = MutableStateFlow<WeatherResponse?>(null)
    val weatherResponseStateFlow: StateFlow<WeatherResponse?>
        get() = _weatherResponseStateFlow

    init {
        viewModelScope.launch {
            /**
             * serverModule.getCurrentWeather(...) - call to api to get current weather
             * - return WeatherResponse object
             * - must be in coroutine (parallel thread) viewModelScope.launch {...here}
             */
            val weatherResponse = serverModule.getCurrentWeather(
                lat = 50.4851493,
                lon = 30.4721233,
            )
            Log.e("WeatherScreenViewModel", "$weatherResponse")
            _weatherResponseStateFlow.value = weatherResponse
        }
    }
}