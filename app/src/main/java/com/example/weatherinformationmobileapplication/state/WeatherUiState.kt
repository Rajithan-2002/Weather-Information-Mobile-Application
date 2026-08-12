package com.example.weatherinformationmobileapplication.state

import com.example.weatherinformationmobileapplication.model.WeatherResponse

/**
 * Sealed class representing the different states of the Weather UI.
 */
sealed class WeatherUiState {
    object Loading : WeatherUiState()
    data class Success(val data: WeatherResponse) : WeatherUiState()
    data class Error(val message: String) : WeatherUiState()
}
