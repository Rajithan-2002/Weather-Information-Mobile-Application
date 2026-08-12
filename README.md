# ☁️ Weather Information Mobile Application

A modern Android weather application that provides **real-time weather data** for any city worldwide using the [OpenWeatherMap API](https://openweathermap.org/). Built with a clean MVVM architecture and a polished blue-themed Material Design UI.

> 📱 Developed as part of **In-Class Assessment 2** — Mobile Application Development Module  
> 🎓 **Kelani MIT** — 2nd Year, 2nd Semester

---

## ✨ Features

| Feature | Description |
|---------|-------------|
| 🔍 **City Search** | Search weather by city name with instant results |
| 🌡️ **Live Weather Data** | Real-time temperature, humidity, wind speed & conditions |
| 🎨 **Modern UI** | Blue gradient theme with Material Design cards & components |
| 📱 **Edge-to-Edge** | Immersive full-screen layout with system bar handling |
| ⚡ **Async Networking** | Non-blocking API calls using Kotlin Coroutines |
| 🔄 **State Management** | 4 distinct UI states — Initial, Loading, Success, Error |
| ❌ **Error Handling** | Graceful handling of network errors, invalid cities & timeouts |
| ⌨️ **Smart Keyboard** | Auto-hide keyboard on search with input validation |

---

## 📸 App States

The app displays **4 distinct UI states** for a smooth user experience:

| State | Description |
|-------|-------------|
| 🏠 **Initial** | Welcome screen with weather icon — *"Search a city to see the weather"* |
| ⏳ **Loading** | Spinner with *"Fetching weather..."* message during API call |
| ✅ **Success** | Weather card showing city name, temperature, condition, humidity & wind speed |
| ❌ **Error** | Error icon with descriptive message (city not found, no internet, etc.) |

---

## 🏗️ Architecture

The project follows the **MVVM (Model-View-ViewModel)** architecture pattern:

```
app/src/main/java/com/example/weatherinformationmobileapplication/
│
├── MainActivity.kt              # View layer — UI rendering & user interactions
├── WeatherViewModel.kt          # ViewModel — Business logic & API orchestration
│
├── model/
│   └── WeatherResponse.kt       # Data classes for API JSON response mapping
│
├── network/
│   ├── RetrofitClient.kt        # Singleton Retrofit HTTP client configuration
│   └── WeatherApiService.kt     # API interface with endpoint definitions
│
└── state/
    └── WeatherUiState.kt        # Sealed class for UI state management
```

### Data Flow

```
User Input → MainActivity → WeatherViewModel → RetrofitClient → OpenWeatherMap API
                  ↑                                                      │
                  └──────── LiveData<WeatherUiState> ←──── JSON Response ┘
```

---

## 🛠️ Tech Stack

| Technology | Purpose |
|-----------|---------|
| **Kotlin** | Primary programming language |
| **Android SDK 37** | Target & compile SDK |
| **ViewBinding** | Type-safe view access (no `findViewById`) |
| **ViewModel + LiveData** | Lifecycle-aware state management |
| **Retrofit 2.11** | Type-safe HTTP client for API calls |
| **Gson** | JSON serialization/deserialization |
| **OkHttp** | HTTP client with logging interceptor |
| **Kotlin Coroutines** | Asynchronous programming |
| **Material Design 3** | UI components (MaterialCardView, TextInputLayout) |
| **ConstraintLayout** | Responsive layout design |

---

## 🌐 API Reference

This app uses the **OpenWeatherMap Current Weather API**.

| Parameter | Value |
|-----------|-------|
| **Base URL** | `https://api.openweathermap.org/` |
| **Endpoint** | `GET /data/2.5/weather` |
| **Query Params** | `q` (city), `appid` (API key), `units` (metric) |

### Sample Response

```json
{
  "name": "Colombo",
  "main": {
    "temp": 29.5,
    "humidity": 78
  },
  "weather": [
    { "main": "Clouds" }
  ],
  "wind": {
    "speed": 4.12
  }
}
```

> 🔑 Get your free API key at [openweathermap.org/api](https://openweathermap.org/api)

---

## 🚀 Getting Started

### Prerequisites

- **Android Studio** Arctic Fox or later
- **JDK 11** or higher
- **Android SDK 37** (API Level 37)
- **Internet connection** (for API calls)

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/Rajithan-2002/Weather-Information-Mobile-Application.git
   ```

2. **Open in Android Studio**
   - File → Open → Select the project folder

3. **Configure API Key** (Optional — a default key is included)
   - Open `app/build.gradle`
   - Replace the `WEATHER_API_KEY` value with your own key:
     ```groovy
     buildConfigField "String", "WEATHER_API_KEY", "\"YOUR_API_KEY_HERE\""
     ```

4. **Build & Run**
   - Connect an Android device or start an emulator (min SDK 24 / Android 7.0)
   - Click ▶️ Run

---

## 📁 Project Structure

```
Weather-Information-Mobile-Application/
├── app/
│   ├── build.gradle                    # App-level dependencies & config
│   ├── src/main/
│   │   ├── AndroidManifest.xml         # Permissions & activity declarations
│   │   ├── java/.../
│   │   │   ├── MainActivity.kt         # Main UI activity
│   │   │   ├── WeatherViewModel.kt     # ViewModel with API logic
│   │   │   ├── model/
│   │   │   │   └── WeatherResponse.kt  # API response data models
│   │   │   ├── network/
│   │   │   │   ├── RetrofitClient.kt   # HTTP client setup
│   │   │   │   └── WeatherApiService.kt# API endpoint interface
│   │   │   └── state/
│   │   │       └── WeatherUiState.kt   # UI state sealed class
│   │   └── res/
│   │       ├── drawable/               # Gradients, card backgrounds, icons
│   │       ├── layout/
│   │       │   └── activity_main.xml   # Main activity layout
│   │       └── values/
│   │           ├── colors.xml          # Blue theme color palette
│   │           └── strings.xml         # UI string resources
├── build.gradle                        # Project-level Gradle config
├── settings.gradle                     # Gradle settings
└── README.md                           # This file
```

---

## 📋 Permissions

| Permission | Reason |
|-----------|--------|
| `INTERNET` | Required to make API calls to OpenWeatherMap |
| `ACCESS_NETWORK_STATE` | Check network connectivity before API calls |

---

## 👥 Contributors

| Name | Role |
|------|------|
| **Rajithan** | Project setup, networking & backend logic |
| **Habikugasarma.K** | API integration & documentation |
| **Pasindu Dulsara** | UI design & layout states  |

---

## 📄 License

This project is developed for educational purposes as part of the Mobile Application Development module at Kelani MIT.

---

<p align="center">
  Made with ❤️ by the <strong>Weather App Team</strong> | Kelani MIT 2025/2026
</p>
