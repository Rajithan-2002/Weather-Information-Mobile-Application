package com.example.weatherinformationmobileapplication

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherinformationmobileapplication.network.InvalidCityException
import com.example.weatherinformationmobileapplication.network.RetrofitClient
import com.example.weatherinformationmobileapplication.network.WeatherApiService
import com.example.weatherinformationmobileapplication.state.WeatherUiState
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class WeatherViewModel : ViewModel() {

    init {
        Log.i("WeatherApp", "WeatherViewModel initialized")
    }

    private val _uiState = MutableLiveData<WeatherUiState>()
    val uiState: LiveData<WeatherUiState> = _uiState

    /**
     * Using the real Retrofit-powered API service.
     */
    private val weatherApiService: WeatherApiService = RetrofitClient.weatherApiService

    /**
     * Fetches weather for the given city or zip code and updates the UI state.
     */
    fun fetchWeather(query: String) {
        Log.d("WeatherApp", "fetchWeather called for query: $query")
        if (query.isBlank()) {
            _uiState.postValue(WeatherUiState.Error("Please enter a city name or zip code."))
            return
        }

        _uiState.value = WeatherUiState.Loading

        val isZipCode = query.firstOrNull()?.isDigit() == true

        viewModelScope.launch {
            try {
                Log.d("WeatherApp", "Starting real API call...")
                val response = if (isZipCode) {
                    weatherApiService.getCurrentWeatherByZip(
                        zipCode = query,
                        apiKey = BuildConfig.WEATHER_API_KEY
                    )
                } else {
                    weatherApiService.getCurrentWeather(
                        city = query,
                        apiKey = BuildConfig.WEATHER_API_KEY
                    )
                }
                Log.d("WeatherApp", "API call successful: ${response.cityName}")
                _uiState.postValue(WeatherUiState.Success(response))
            } catch (e: IOException) {
                Log.e("WeatherApp", "IOException: ${e.message}")
                _uiState.postValue(
                    WeatherUiState.Error("No internet connection. Please check your network and try again.")
                )
            } catch (e: InvalidCityException) {
                Log.e("WeatherApp", "InvalidCityException: ${e.message}")
                _uiState.postValue(
                    WeatherUiState.Error("Location not found. Please check the spelling or zip code and try again.")
                )
            } catch (e: HttpException) {
                Log.e("WeatherApp", "HttpException: ${e.code()}")
                if (e.code() == 404) {
                    _uiState.postValue(
                        WeatherUiState.Error("Location not found. Please check the spelling or zip code and try again.")
                    )
                } else {
                    _uiState.postValue(
                        WeatherUiState.Error("Unable to retrieve weather data. Please try again later.")
                    )
                }
            } catch (e: Exception) {
                Log.e("WeatherApp", "General Exception: ${e.message}")
                _uiState.postValue(
                    WeatherUiState.Error("Something went wrong. Please try again.")
                )
            }
        }
    }
}
