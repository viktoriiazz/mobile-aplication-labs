package com.lab6.data

import com.lab6.data.entity.response.WeatherForecastResponse
import com.lab6.data.entity.response.WeatherResponse
import org.intellij.lang.annotations.Language
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * ServerApi - interface with functions-mappers for API requests
 * - will be used by Retrofit library to generate convenient API class of communication with API
 */
interface ServerApi {
    /**
     * @GET - annotation that marks functions as GET Request
        - contains "/data/2.5/weather" - request to get current data of api
        [described here: https://openweathermap.org/current]
        > investigate mode: @GET @POST
    */
    @GET("/data/2.5/weather")
    suspend fun getCurrentWeather(
        @Query("q") city: String,
        @Query("lang") language: String = "uk",
        @Query("appid") apiId: String = "3cdf910d13f60749aa2cb1a407142405",
        @Query("units") units: String = "metric"
    ): WeatherResponse
    // return data class of response

    /**
     * @GET  - annotation that marks functions as GET Request
        - contains "/data/2.5/forecast" - request to get forecast data of api
        [described here: https://openweathermap.org/forecast5]
     */
    @GET("/data/2.5/forecast")
    suspend fun getWeatherForecast(
        @Query("q") city: String,
        @Query("lang") language: String = "ua",
        @Query("appid") apiId: String = "3cdf910d13f60749aa2cb1a407142405",
        @Query("units") units: String = "metric"

    ): WeatherForecastResponse // return data class of response
}