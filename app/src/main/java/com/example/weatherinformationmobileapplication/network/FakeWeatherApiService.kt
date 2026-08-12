package com.example.weatherinformationmobileapplication.network

import com.example.weatherinformationmobileapplication.model.*
import kotlinx.coroutines.delay
import java.io.IOException

/**
 * Fake implementation of WeatherApiService for testing without a real API key.
 */
class FakeWeatherApiService : WeatherApiService {

    override suspend fun getCurrentWeather(
        city: String,
        apiKey: String,
        units: String
    ): WeatherResponse {
        // Simulate network delay
        delay(1000)

        return when (city.lowercase()) {
            "error" -> throw IOException("Simulated network error")
            "notfound" -> throw InvalidCityException("City not found")
            else -> {
                // Success case with dummy data
                WeatherResponse(
                    cityName = city,
                    main = MainInfo(temp = 29.0, humidity = 78),
                    weather = listOf(WeatherCondition(main = "Cloudy")),
                    wind = WindInfo(speed = 12.0)
                )
            }
        }
    }
}
