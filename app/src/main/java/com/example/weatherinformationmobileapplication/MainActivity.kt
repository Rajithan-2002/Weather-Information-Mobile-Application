package com.example.weatherinformationmobileapplication

import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.weatherinformationmobileapplication.databinding.ActivityMainBinding
import com.example.weatherinformationmobileapplication.state.WeatherUiState

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: WeatherViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Handle window insets for edge-to-edge
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupListeners()
        observeUiState()
    }

    private fun setupListeners() {
        binding.btnSearch.setOnClickListener {
            val cityName = binding.etCitySearch.text.toString().trim()

            if (cityName.isEmpty()) {
                binding.etCitySearch.error = "Please enter a location"
                Toast.makeText(this, "Search query cannot be empty", Toast.LENGTH_SHORT).show()
                binding.etCitySearch.requestFocus()
            } else {
                binding.etCitySearch.error = null
                hideKeyboard()
                viewModel.fetchWeather(cityName)
            }
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

    private fun hideAllStates() {
        binding.layoutInitial.visibility = View.GONE
        binding.layoutLoading.visibility = View.GONE
        binding.cardWeatherResult.visibility = View.GONE
        binding.layoutError.visibility = View.GONE
    }

    private fun showLoading() {
        hideAllStates()
        binding.layoutLoading.visibility = View.VISIBLE
    }

    private fun showSuccess(state: WeatherUiState.Success) {
        hideAllStates()

        val data = state.data
        binding.tvCityName.text = data.cityName
        binding.tvTemperature.text = "${data.temperature}°C"
        binding.tvCondition.text = data.condition
        binding.tvHumidity.text = getString(R.string.humidity_label, "${data.humidity}%")
        binding.tvWindSpeed.text = getString(R.string.wind_speed_label, "${data.windSpeed} m/s")

        binding.cardWeatherResult.visibility = View.VISIBLE
    }

    private fun showError(message: String) {
        hideAllStates()
        binding.tvErrorMessage.text = message
        binding.layoutError.visibility = View.VISIBLE

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}
