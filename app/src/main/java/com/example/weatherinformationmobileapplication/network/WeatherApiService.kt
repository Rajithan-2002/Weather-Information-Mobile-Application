package com.example.weatherinformationmobileapplication.network

import com.example.weatherinformationmobileapplication.model.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Weather API Service interface for OpenWeatherMap.
 */
interface WeatherApiService {

    /**
     * Fetches current weather for a specific city.
     */
    @GET("data/2.5/weather")
    suspend fun getCurrentWeather(
        @Query("q") city: String,
        @Query("appid") apiKey: String,
        @Query("units") units: String = "metric"
    ): WeatherResponse
}

/**
 * Custom exception for 404 (City Not Found) errors.
 */
class InvalidCityException(message: String) : Exception(message)
