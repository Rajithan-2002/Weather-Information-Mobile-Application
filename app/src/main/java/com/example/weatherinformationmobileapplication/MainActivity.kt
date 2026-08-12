package com.example.weatherinformationmobileapplication

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {

    private lateinit var etCitySearch: TextInputEditText
    private lateinit var btnSearch: Button
    
    // UI state containers
    private lateinit var layoutInitial: View
    private lateinit var layoutLoading: View
    private lateinit var cardWeatherResult: MaterialCardView
    private lateinit var layoutError: View
    
    // Result views
    private lateinit var tvCityName: TextView
    private lateinit var tvTemperature: TextView
    private lateinit var tvCondition: TextView
    private lateinit var tvHumidity: TextView
    private lateinit var tvWindSpeed: TextView
    private lateinit var tvErrorMessage: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Handle window insets for edge-to-edge
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initializeViews()

        btnSearch.setOnClickListener {
            handleSearchClick()
        }
    }

    private fun initializeViews() {
        etCitySearch = findViewById(R.id.etCitySearch)
        btnSearch = findViewById(R.id.btnSearch)
        
        layoutInitial = findViewById(R.id.layoutInitial)
        layoutLoading = findViewById(R.id.layoutLoading)
        cardWeatherResult = findViewById(R.id.cardWeatherResult)
        layoutError = findViewById(R.id.layoutError)
        
        tvCityName = findViewById(R.id.tvCityName)
        tvTemperature = findViewById(R.id.tvTemperature)
        tvCondition = findViewById(R.id.tvCondition)
        tvHumidity = findViewById(R.id.tvHumidity)
        tvWindSpeed = findViewById(R.id.tvWindSpeed)
        tvErrorMessage = findViewById(R.id.tvErrorMessage)
    }

    private fun handleSearchClick() {
        val cityName = etCitySearch.text.toString().trim()

        if (cityName.isEmpty()) {
            etCitySearch.error = "Please enter a city name"
            Toast.makeText(this, "City name cannot be empty", Toast.LENGTH_SHORT).show()
            etCitySearch.requestFocus()
        } else {
            etCitySearch.error = null
            hideKeyboard()
            
            // TASK 05: The backend developer will trigger the API request here.
            showLoading()
            
            // Example of how the backend developer might call the UI update:
            // fetchWeather(cityName) 
        }
    }

    /**
     * UI helper to show the loading state (Task 05)
     */
    fun showLoading() {
        hideAllStates()
        layoutLoading.visibility = View.VISIBLE
    }

    /**
     * UI helper to display the weather data once parsed from JSON (Task 07)
     * Backend developer: Call this after parsing the JSON response.
     */
    fun displayWeather(city: String, temp: String, condition: String, humidity: String, wind: String) {
        hideAllStates()
        
        tvCityName.text = city
        tvTemperature.text = temp
        tvCondition.text = condition
        tvHumidity.text = getString(R.string.humidity_label, humidity)
        tvWindSpeed.text = getString(R.string.wind_speed_label, wind)
        
        cardWeatherResult.visibility = View.VISIBLE
    }

    /**
     * UI helper to show an error message (Task 05/06)
     */
    fun showError(message: String) {
        hideAllStates()
        tvErrorMessage.text = message
        layoutError.visibility = View.VISIBLE
    }

    private fun hideAllStates() {
        layoutInitial.visibility = View.GONE
        layoutLoading.visibility = View.GONE
        cardWeatherResult.visibility = View.GONE
        layoutError.visibility = View.GONE
    }

    private fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}