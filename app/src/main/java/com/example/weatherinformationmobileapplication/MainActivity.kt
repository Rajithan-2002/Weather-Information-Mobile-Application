package com.example.weatherinformationmobileapplication

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.weatherinformationmobileapplication.databinding.ActivityMainBinding
import com.example.weatherinformationmobileapplication.state.WeatherUiState

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: WeatherViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListeners()
        observeUiState()
    }

    private fun setupListeners() {
        binding.searchButton.setOnClickListener {
            val city = binding.cityEditText.text.toString()
            viewModel.fetchWeather(city)
        }
    }

    private fun observeUiState() {
        viewModel.uiState.observe(this) { state ->
            when (state) {
                is WeatherUiState.Loading -> {
                    showLoading()
                }
                is WeatherUiState.Success -> {
                    showSuccess(state)
                }
                is WeatherUiState.Error -> {
                    showError(state.message)
                }
            }
        }
    }

    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
        binding.weatherInfoContainer.visibility = View.GONE
        binding.errorText.visibility = View.GONE
    }

    private fun showSuccess(state: WeatherUiState.Success) {
        binding.progressBar.visibility = View.GONE
        binding.errorText.visibility = View.GONE
        binding.weatherInfoContainer.visibility = View.VISIBLE

        val data = state.data
        binding.cityNameText.text = data.cityName
        binding.tempText.text = "${data.temperature}°C"
        binding.conditionText.text = data.condition
        binding.humidityText.text = "Humidity: ${data.humidity}%"
        binding.windText.text = "Wind: ${data.windSpeed} m/s"
    }

    private fun showError(message: String) {
        binding.progressBar.visibility = View.GONE
        binding.weatherInfoContainer.visibility = View.GONE
        binding.errorText.visibility = View.VISIBLE
        binding.errorText.text = message
        
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
