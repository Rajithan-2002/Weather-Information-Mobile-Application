package com.example.weatherinformationmobileapplication

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherinformationmobileapplication.network.FakeWeatherApiService
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
     * Currently configured API service.
     * Defaults to FakeWeatherApiService for development without a key.
     * TODO: Swap to RetrofitClient.weatherApiService once the real API is ready.
     */
    private val weatherApiService: WeatherApiService = FakeWeatherApiService()

    /**
     * Fetches weather for the given city and updates the UI state.
     */
    fun fetchWeather(city: String) {
        Log.d("WeatherApp", "fetchWeather called for city: $city")
        if (city.isBlank()) {
            _uiState.postValue(WeatherUiState.Error("Please enter a city name."))
            return
        }

        _uiState.value = WeatherUiState.Loading

        viewModelScope.launch {
            try {
                Log.d("WeatherApp", "Starting API call...")
                // TODO: Replace "PLACEHOLDER_API_KEY" with a real key later
                val response = weatherApiService.getCurrentWeather(
                    city = city,
                    apiKey = "PLACEHOLDER_API_KEY"
                )
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
                    WeatherUiState.Error("City not found. Please check the spelling and try again.")
                )
            } catch (e: HttpException) {
                Log.e("WeatherApp", "HttpException: ${e.code()}")
                if (e.code() == 404) {
                    _uiState.postValue(
                        WeatherUiState.Error("City not found. Please check the spelling and try again.")
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
