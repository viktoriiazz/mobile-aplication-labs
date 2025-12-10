package com.lab6.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.lab6.ui.screens.current.WeatherScreen
import com.lab6.ui.screens.forecast.WeatherForecastScreen
import com.lab6.ui.screens.manu.MenuScreen


const val SCREEN_MENU_SCREEN = "menuScreen"
const val SCREEN_WEATHER_SCREEN = "weatherScreen"
const val SCREEN_WEATHER_FORECAST_SCREEN = "weatherForecastScreen"

@Composable
fun NavigationGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = SCREEN_MENU_SCREEN
    ) {

        composable(
            route = SCREEN_MENU_SCREEN
        ) {
            MenuScreen(
                onWeather = { navController.navigate(SCREEN_WEATHER_SCREEN) },
                onWeatherForecast = { navController.navigate(SCREEN_WEATHER_FORECAST_SCREEN) },
            )
        }
        composable(
            route = SCREEN_WEATHER_SCREEN
        ) {
            WeatherScreen()
        }
        composable(
            route = SCREEN_WEATHER_FORECAST_SCREEN
        ) {
            WeatherForecastScreen()
        }
    }
}