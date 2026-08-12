# Current Weather Data API

## API Name

**OpenWeather Current Weather Data API**

## Purpose

The Current Weather Data API returns **real-time weather information** for a specific
geographic location. It accepts latitude and longitude as input and returns a JSON
object containing temperature, humidity, wind speed, weather conditions, and other
meteorological data.

In this project, it is the **second API call** — it receives the coordinates obtained
from the Direct Geocoding API and returns the actual weather data to display.

```text
Latitude + Longitude → Current Weather API → Weather JSON
```

## Official Documentation

- **URL:** [https://openweathermap.org/current](https://openweathermap.org/current)
- **Section:** Current weather data → Call current weather data

---

## Endpoint

```
https://api.openweathermap.org/data/2.5/weather
```

## HTTP Method

```
GET
```

---

## Query Parameters

| Parameter | Required | Description                                                                                              |
| --------- | -------- | -------------------------------------------------------------------------------------------------------- |
| `lat`     | Yes      | Geographical coordinate — latitude                                                                       |
| `lon`     | Yes      | Geographical coordinate — longitude                                                                      |
| `appid`   | Yes      | Your unique OpenWeather API key                                                                          |
| `units`   | No       | Unit system for temperature and wind speed: `standard`, `metric`, or `imperial`. Default is `standard`.  |
| `mode`    | No       | Response format: `json` (default), `xml`, or `html`                                                      |
| `lang`    | No       | Language for weather description text (e.g., `en`, `si`, `ta`)                                           |

### Parameter Details

#### `lat` and `lon` — Geographic Coordinates

These are the latitude and longitude values obtained from the **Direct Geocoding API**
response. They specify the exact location for which weather data is requested.

```text
Colombo, Sri Lanka:
  lat = 6.9344
  lon = 79.8428
```

#### `appid` — API Key

Same API key used for the Geocoding API. One OpenWeather API key works across all
OpenWeather API endpoints.

> ⚠️ **Security:** Never expose your API key. Use `YOUR_API_KEY` as a placeholder.

#### `units` — Unit System

| Value      | Temperature | Wind Speed  |
| ---------- | ----------- | ----------- |
| `standard` | Kelvin      | meter/sec   |
| `metric`   | Celsius     | meter/sec   |
| `imperial` | Fahrenheit  | miles/hour  |

**Why `units=metric` is used in this project:**

- Celsius is the standard temperature unit in Sri Lanka and most countries
- Metric units are familiar and intuitive for the target users
- Without `units=metric`, the API returns temperature in **Kelvin** by default,
  which is not practical for a user-facing weather application

---

## Architecture — Two-Step API Flow

The latitude and longitude used in the Current Weather API request **must come from
the actual Direct Geocoding API response**. The application does not use hardcoded
coordinates.

```text
Colombo
   ↓
Geocoding API
   q=Colombo,LK&limit=1&appid=YOUR_API_KEY
   ↓
Response: lat=6.9344, lon=79.8428
   ↓
Current Weather API
   lat=6.9344&lon=79.8428&units=metric&appid=YOUR_API_KEY
   ↓
Weather JSON Response
```

---

## Example Request

### Using Coordinates from Geocoding API (Colombo, Sri Lanka)

**Request URL:**

```
https://api.openweathermap.org/data/2.5/weather?lat=6.9344&lon=79.8428&units=metric&appid=YOUR_API_KEY
```

**Breakdown:**

| Component   | Value                                                     |
| ----------- | --------------------------------------------------------- |
| Base URL    | `https://api.openweathermap.org/data/2.5/weather`         |
| `lat`       | `6.9344` (from Geocoding API response)                    |
| `lon`       | `79.8428` (from Geocoding API response)                   |
| `units`     | `metric` (Celsius + meter/sec)                            |
| `appid`     | `YOUR_API_KEY`                                            |

---

## Response Format

The API returns a **JSON object** (not an array, unlike the Geocoding API).

### Example Response (from Official Documentation)

```json
{
  "coord": {
    "lon": 79.8428,
    "lat": 6.9344
  },
  "weather": [
    {
      "id": 802,
      "main": "Clouds",
      "description": "scattered clouds",
      "icon": "03d"
    }
  ],
  "base": "stations",
  "main": {
    "temp": 30.5,
    "feels_like": 35.2,
    "temp_min": 29.8,
    "temp_max": 31.0,
    "pressure": 1008,
    "humidity": 74,
    "sea_level": 1008,
    "grnd_level": 1007
  },
  "visibility": 10000,
  "wind": {
    "speed": 4.12,
    "deg": 250,
    "gust": 6.5
  },
  "clouds": {
    "all": 40
  },
  "dt": 1723459200,
  "sys": {
    "type": 2,
    "id": 2010,
    "country": "LK",
    "sunrise": 1723420800,
    "sunset": 1723464000
  },
  "timezone": 19800,
  "id": 1248991,
  "name": "Colombo",
  "cod": 200
}
```

> **Note:** This is a representative example based on the official documentation's response
> structure. Actual values will vary depending on real-time weather conditions. The actual
> Postman response for this project is documented in [`05-postman-testing.md`](./05-postman-testing.md).

---

## Response Fields

### Complete Field Reference

According to the official OpenWeather Current Weather Data API documentation:

| Field                   | Type    | Description                                                      |
| ----------------------- | ------- | ---------------------------------------------------------------- |
| `coord.lon`             | Number  | City longitude                                                   |
| `coord.lat`             | Number  | City latitude                                                    |
| `weather`               | Array   | Array of weather condition objects (can contain multiple entries) |
| `weather[0].id`         | Number  | Weather condition ID                                             |
| `weather[0].main`       | String  | Group of weather parameters (e.g., Rain, Snow, Clouds)           |
| `weather[0].description`| String  | Weather condition description (e.g., "scattered clouds")         |
| `weather[0].icon`       | String  | Weather icon ID                                                  |
| `base`                  | String  | Internal parameter                                               |
| `main.temp`             | Number  | Temperature (units depend on `units` parameter)                  |
| `main.feels_like`       | Number  | Human-perceived temperature                                      |
| `main.temp_min`         | Number  | Minimum currently observed temperature                           |
| `main.temp_max`         | Number  | Maximum currently observed temperature                           |
| `main.pressure`         | Number  | Atmospheric pressure (hPa)                                       |
| `main.humidity`         | Number  | Humidity (%)                                                     |
| `main.sea_level`        | Number  | Atmospheric pressure on sea level (hPa)                          |
| `main.grnd_level`       | Number  | Atmospheric pressure on ground level (hPa)                       |
| `visibility`            | Number  | Visibility distance (meters, max 10 km)                          |
| `wind.speed`            | Number  | Wind speed (units depend on `units` parameter)                   |
| `wind.deg`              | Number  | Wind direction (meteorological degrees)                          |
| `wind.gust`             | Number  | Wind gust speed (units depend on `units` parameter)              |
| `clouds.all`            | Number  | Cloudiness (%)                                                   |
| `rain.1h`               | Number  | Rain volume for last 1 hour, mm (optional, may be absent)        |
| `rain.3h`               | Number  | Rain volume for last 3 hours, mm (optional, may be absent)       |
| `snow.1h`               | Number  | Snow volume for last 1 hour, mm (optional, may be absent)        |
| `snow.3h`               | Number  | Snow volume for last 3 hours, mm (optional, may be absent)       |
| `dt`                    | Number  | Time of data calculation, Unix timestamp, UTC                    |
| `sys.type`              | Number  | Internal parameter                                               |
| `sys.id`                | Number  | Internal parameter                                               |
| `sys.country`           | String  | Country code (e.g., LK, GB)                                     |
| `sys.sunrise`           | Number  | Sunrise time, Unix timestamp, UTC                                |
| `sys.sunset`            | Number  | Sunset time, Unix timestamp, UTC                                 |
| `timezone`              | Number  | Shift in seconds from UTC                                        |
| `id`                    | Number  | City ID                                                          |
| `name`                  | String  | City name                                                        |
| `cod`                   | Number  | Internal parameter (HTTP status code)                            |

### Fields Required by This Application

| Display Field     | JSON Path                  | Unit (metric) |
| ----------------- | -------------------------- | ------------- |
| City Name         | `name`                     | —             |
| Temperature       | `main.temp`                | °C            |
| Weather Condition | `weather[0].description`   | —             |
| Humidity          | `main.humidity`            | %             |
| Wind Speed        | `wind.speed`               | meter/sec     |

---

## Units of Measurement — Detailed Reference

| Parameter        | Standard   | Metric     | Imperial    |
| ---------------- | ---------- | ---------- | ----------- |
| Temperature      | Kelvin     | Celsius    | Fahrenheit  |
| Wind Speed       | meter/sec  | meter/sec  | miles/hour  |
| Precipitation    | mm         | mm         | mm          |
| Pressure         | hPa        | hPa        | hPa         |
| Visibility       | meters     | meters     | meters      |

> This project uses `units=metric`, so temperature is in **Celsius** and wind speed
> is in **meters per second**.

---

## Error Handling

| Scenario              | HTTP Status | Response Body                                  |
| --------------------- | ----------- | ---------------------------------------------- |
| Valid coordinates      | `200 OK`    | JSON object with weather data                  |
| Missing API key       | `401`       | `{"cod":401,"message":"..."}`                  |
| Invalid API key       | `401`       | `{"cod":401,"message":"..."}`                  |
| Invalid lat/lon       | `400`       | `{"cod":"400","message":"..."}`                |

---

## Summary

| Property          | Value                                                     |
| ----------------- | --------------------------------------------------------- |
| API Name          | OpenWeather Current Weather Data API                      |
| Endpoint          | `https://api.openweathermap.org/data/2.5/weather`         |
| HTTP Method       | GET                                                       |
| Required Params   | `lat`, `lon`, `appid`                                     |
| Optional Params   | `units`, `mode`, `lang`                                   |
| Response Format   | JSON object                                               |
| Key Output Fields | `name`, `main.temp`, `main.humidity`, `weather[0].description`, `wind.speed` |
| Role in App       | Provides the actual weather data displayed to the user    |
