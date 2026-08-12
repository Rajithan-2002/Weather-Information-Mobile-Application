# UI Integration Notes - Weather Information Mobile Application

This document outlines how to integrate the UI layer with the backend logic.

## ViewModel Setup

The `WeatherViewModel` is the main controller for fetching weather data. It should be instantiated in your `Activity` or `Fragment` using `ViewModelProvider`.

### 1. Observe UI State
The ViewModel exposes a `LiveData<WeatherUiState>` called `uiState`. Your UI should observe this and react to the different states:

```kotlin
val viewModel = ViewModelProvider(this).get(WeatherViewModel::class.java)

viewModel.uiState.observe(this) { state ->
    when (state) {
        is WeatherUiState.Loading -> {
            // Show loading spinner / progress bar
        }
        is WeatherUiState.Success -> {
            // Hide loading
            // Update UI with weather data:
            val weatherData = state.data
            cityNameTextView.text = weatherData.cityName
            tempTextView.text = "${weatherData.temperature}°C"
            conditionTextView.text = weatherData.condition
            humidityTextView.text = "Humidity: ${weatherData.humidity}%"
            windTextView.text = "Wind: ${weatherData.windSpeed} m/s"
        }
        is WeatherUiState.Error -> {
            // Hide loading
            // Show error message (e.g., Toast or SnackBar)
            errorMessageTextView.text = state.message
        }
    }
}
```

### 2. Trigger Weather Fetch
To start a weather search, simply call `fetchWeather(cityName)`:

```kotlin
searchButton.setOnClickListener {
    val city = cityEditText.text.toString()
    viewModel.fetchWeather(city)
}
```

## Data Model Reference (`WeatherResponse`)

In the `Success` state, the `data` object contains:
- `cityName`: String
- `temperature`: Double (Celsius by default)
- `condition`: String (e.g., "Cloudy")
- `humidity`: Int (Percentage)
- `windSpeed`: Double (m/s)

## Testing without API Key
Currently, the ViewModel is configured to use `FakeWeatherApiService`. 
- Input any city name to see a **Success** state.
- Input **"error"** to simulate a network failure.
- Input **"notfound"** to simulate a 404 City Not Found error.
