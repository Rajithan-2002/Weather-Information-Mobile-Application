package com.example.weatherinformationmobileapplication.model

import com.google.gson.annotations.SerializedName

/**
 * Data class representing a parsed weather API JSON response.
 * // TODO: Confirm these field names match the chosen API's actual JSON response before final integration.
 */
data class WeatherResponse(
    @SerializedName("name")
    val cityName: String,

    @SerializedName("main")
    val main: MainInfo,

    @SerializedName("weather")
    val weather: List<WeatherCondition>,

    @SerializedName("wind")
    val wind: WindInfo
) {
    // Derived properties for easier access by the UI
    val temperature: Double get() = main.temp
    val humidity: Int get() = main.humidity
    val condition: String get() = weather.firstOrNull()?.main ?: "Unknown"
    val windSpeed: Double get() = wind.speed
}

data class MainInfo(
    @SerializedName("temp")
    val temp: Double,
    @SerializedName("humidity")
    val humidity: Int
)

data class WeatherCondition(
    @SerializedName("main")
    val main: String
)

data class WindInfo(
    @SerializedName("speed")
    val speed: Double
)
