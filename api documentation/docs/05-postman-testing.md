# Postman API Testing

This document records the API testing process performed using **Postman** to verify
that the OpenWeather APIs return the expected data before implementing the Android
application.

Testing in Postman allows verification of:
- API endpoint correctness
- Query parameter formatting
- Response structure and content
- Required fields availability

---

## Test 1 — Direct Geocoding API

### Test Configuration

| Property       | Value                                                                   |
| -------------- | ----------------------------------------------------------------------- |
| Tool           | Postman                                                                 |
| HTTP Method    | **GET**                                                                 |
| Endpoint       | `http://api.openweathermap.org/geo/1.0/direct`                          |
| Test City      | Colombo, Sri Lanka                                                      |

### Request Parameters

| Parameter | Value          | Purpose                                  |
| --------- | -------------- | ---------------------------------------- |
| `q`       | `Colombo,LK`  | City name with Sri Lanka country code    |
| `limit`   | `1`            | Return only the best match               |
| `appid`   | `****************` | API key (redacted for security)      |

### Full Request URL

```
http://api.openweathermap.org/geo/1.0/direct?q=Colombo,LK&limit=1&appid=YOUR_API_KEY
```

### Expected Result

- HTTP Status: `200 OK`
- Response: JSON array containing at least one location object
- Location object should contain: `name`, `lat`, `lon`, `country`
- `name` should be `"Colombo"` or similar
- `country` should be `"LK"`

### Actual Result

> 📸 **Postman screenshot placeholder**
>
> When the actual Postman screenshot is provided, it will be inserted here.
>
> **Figure 1 — Successful OpenWeather Direct Geocoding request in Postman**

> **Actual response placeholder:**
>
> When the actual JSON response from Postman is provided, it will be inserted here:
>
> ```json
> [
>   {
>     "name": "...",
>     "lat": ...,
>     "lon": ...,
>     "country": "..."
>   }
> ]
> ```
>
> The response will be analyzed to confirm all required fields are present.

### Important Returned Fields

| Field     | Expected Value | Actual Value | Purpose                            |
| --------- | -------------- | ------------ | ---------------------------------- |
| `name`    | Colombo        | *Pending*    | Confirm city name resolved         |
| `lat`     | ≈ 6.93         | *Pending*    | Latitude for weather API call      |
| `lon`     | ≈ 79.84        | *Pending*    | Longitude for weather API call     |
| `country` | LK             | *Pending*    | Confirm Sri Lanka                  |

> **Note:** Actual values will be filled in when Postman screenshot/response is provided.

---

## Test 2 — Current Weather Data API

### Test Configuration

| Property       | Value                                                                   |
| -------------- | ----------------------------------------------------------------------- |
| Tool           | Postman                                                                 |
| HTTP Method    | **GET**                                                                 |
| Endpoint       | `https://api.openweathermap.org/data/2.5/weather`                       |
| Test Location  | Colombo, Sri Lanka (using coordinates from Test 1)                      |

### Request Parameters

| Parameter | Value              | Purpose                                          |
| --------- | ------------------ | ------------------------------------------------ |
| `lat`     | *(from Test 1)*    | Latitude obtained from Geocoding API             |
| `lon`     | *(from Test 1)*    | Longitude obtained from Geocoding API            |
| `units`   | `metric`           | Temperature in Celsius, wind speed in m/s        |
| `appid`   | `****************` | API key (redacted for security)                  |

### Full Request URL

```
https://api.openweathermap.org/data/2.5/weather?lat=6.9344&lon=79.8428&units=metric&appid=YOUR_API_KEY
```

> **Note:** The `lat` and `lon` values shown above are examples. The actual values
> should be taken from the Geocoding API response in Test 1.

### Expected Result

- HTTP Status: `200 OK`
- Response: JSON object containing weather data
- Response should contain: `name`, `main.temp`, `main.humidity`, `weather[0].description`, `wind.speed`

### Actual Result

> 📸 **Postman screenshot placeholder**
>
> When the actual Postman screenshot is provided, it will be inserted here.
>
> **Figure 2 — Successful OpenWeather Current Weather request in Postman**

> **Actual response placeholder:**
>
> When the actual JSON response from Postman is provided, it will be inserted here:
>
> ```json
> {
>   "coord": { ... },
>   "weather": [ ... ],
>   "main": { ... },
>   "wind": { ... },
>   "name": "...",
>   "cod": 200
> }
> ```
>
> The response will be analyzed to confirm all required fields are present.

### Required Fields Verification

| Display Field     | JSON Path                  | Expected Present | Actual Value |
| ----------------- | -------------------------- | ---------------- | ------------ |
| City Name         | `name`                     | Yes              | *Pending*    |
| Temperature       | `main.temp`                | Yes              | *Pending*    |
| Weather Condition | `weather[0].description`   | Yes              | *Pending*    |
| Humidity          | `main.humidity`            | Yes              | *Pending*    |
| Wind Speed        | `wind.speed`               | Yes              | *Pending*    |

> **Note:** Actual values will be filled in when Postman screenshot/response is provided.

---

## Verification Checklist

The practical sheet requires verification that:

| Verification Point                          | Test 1 (Geocoding) | Test 2 (Weather) |
| ------------------------------------------- | ------------------ | ----------------- |
| Request was successfully sent               | *Pending*          | *Pending*         |
| Server returned successful response         | *Pending*          | *Pending*         |
| Response is JSON                            | *Pending*          | *Pending*         |
| Required weather information is available   | N/A                | *Pending*         |
| Required coordinates are available          | *Pending*          | N/A               |

> **Note:** Verification results will be confirmed when actual Postman evidence
> is provided. Each point will be marked as **✅ Verified** or **❌ Failed**
> based on the actual screenshots/responses.

---

## Testing Summary

| Test            | API                 | Expected Result           | Actual Result | Status    |
| --------------- | ------------------- | ------------------------- | ------------- | --------- |
| Test 1          | Direct Geocoding    | Coordinates returned      | *Pending*     | *Pending* |
| Test 2          | Current Weather     | Weather JSON returned     | *Pending*     | *Pending* |

> **Note:** Status will be updated to **PASS** or **FAIL** when actual Postman
> test evidence is provided. Do not claim a test passed without evidence.

---

## How to Reproduce These Tests

### Step-by-Step: Test 1 — Geocoding

1. Open Postman
2. Create a new request
3. Set method to **GET**
4. Enter URL: `http://api.openweathermap.org/geo/1.0/direct`
5. Add query parameters:
   - `q` = `Colombo,LK`
   - `limit` = `1`
   - `appid` = *(your API key)*
6. Click **Send**
7. Verify HTTP status is `200 OK`
8. Verify response is a JSON array with `lat`, `lon`, `name` fields
9. **Copy the `lat` and `lon` values** for Test 2

### Step-by-Step: Test 2 — Current Weather

1. In Postman, create a new request
2. Set method to **GET**
3. Enter URL: `https://api.openweathermap.org/data/2.5/weather`
4. Add query parameters:
   - `lat` = *(value from Test 1)*
   - `lon` = *(value from Test 1)*
   - `units` = `metric`
   - `appid` = *(your API key)*
5. Click **Send**
6. Verify HTTP status is `200 OK`
7. Verify response contains: `name`, `main.temp`, `main.humidity`, `weather[0].description`, `wind.speed`

---

## API Key Security Note

> ⚠️ **Important:** API keys are credentials. When taking Postman screenshots:
>
> - The `appid` parameter value should be redacted or obscured
> - Do not share screenshots containing visible API keys in public repositories
> - In this documentation, all API keys are replaced with `YOUR_API_KEY` or `****************`
> - Store your API key securely and do not commit it to version control
