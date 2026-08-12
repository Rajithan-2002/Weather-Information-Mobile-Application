package com.example.weatherinformationmobileapplication.network

import com.example.weatherinformationmobileapplication.model.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Weather API Service interface, designed to be swappable.
 */
interface WeatherApiService {

    /**
     * Fetches current weather for a specific city.
     * @GET("TODO_ENDPOINT_PATH") - placeholder for real endpoint
     */
    @GET("TODO_ENDPOINT_PATH")
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
