package com.example.weatherinformationmobileapplication

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.weatherinformationmobileapplication.state.WeatherUiState

class MainActivity : AppCompatActivity() {

    private val viewModel: WeatherViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("WeatherApp", "MainActivity onCreate started!")
        setContentView(R.layout.activity_main)

        viewModel.uiState.observe(this) { state ->
            Log.e("WeatherApp", "UI State Changed: $state")
            when (state) {
                is WeatherUiState.Loading -> {
                    Log.d("WeatherApp", "-> LOADING...")
                }
                is WeatherUiState.Success -> {
                    Log.d("WeatherApp", "-> SUCCESS: ${state.data.cityName}")
                }
                is WeatherUiState.Error -> {
                    Log.d("WeatherApp", "-> ERROR: ${state.message}")
                }
            }
        }

        Log.d("WeatherApp", "Triggering initial fetch for London...")
        viewModel.fetchWeather("London")
    }
}
