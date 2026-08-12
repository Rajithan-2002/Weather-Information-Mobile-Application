# JSON Response Structure

This document analyzes the JSON response structures from both OpenWeather APIs used
in this project and maps them to the application's display requirements.

---

## Required Display Fields

The practical sheet requires the application to display the following information:

| # | Display Field     | Description                            |
|---|-------------------|----------------------------------------|
| 1 | City Name         | Name of the searched city              |
| 2 | Temperature       | Current temperature                    |
| 3 | Weather Condition | Description of current weather         |
| 4 | Humidity          | Current humidity percentage            |
| 5 | Wind Speed        | Current wind speed                     |

---

## Field Mapping Table

Based on the official OpenWeather Current Weather Data API documentation:

| Application Requirement | JSON Path                  | Data Type | Description                                     | Example Value       |
| ----------------------- | -------------------------- | --------- | ----------------------------------------------- | ------------------- |
| City Name               | `name`                     | String    | Name of the city                                | `"Colombo"`         |
| Temperature             | `main.temp`                | Number    | Temperature in °C (with `units=metric`)         | `30.5`              |
| Weather Condition       | `weather[0].description`   | String    | Textual description of weather                  | `"scattered clouds"`|
| Humidity                | `main.humidity`            | Number    | Humidity percentage                             | `74`                |
| Wind Speed              | `wind.speed`               | Number    | Wind speed in m/s (with `units=metric`)         | `4.12`              |

> **Note:** These JSON paths are based on the official OpenWeather API documentation
> response structure. When actual Postman test responses are provided, this table
> will be verified against the real response data.

---

## Understanding the JSON Structure

### The Full Response — Visual Breakdown

```json
{                                    ← Root object
  "coord": { ... },                  ← Coordinates (not needed for display)
  "weather": [ { ... } ],           ← Weather conditions (ARRAY)
  "base": "stations",               ← Internal (not needed)
  "main": {                          ← Main weather data (OBJECT)
    "temp": 30.5,                    ← ★ Temperature
    "feels_like": 35.2,
    "temp_min": 29.8,
    "temp_max": 31.0,
    "pressure": 1008,
    "humidity": 74                   ← ★ Humidity
  },
  "visibility": 10000,
  "wind": {                          ← Wind data (OBJECT)
    "speed": 4.12,                   ← ★ Wind Speed
    "deg": 250
  },
  "clouds": { ... },
  "dt": 1723459200,
  "sys": { ... },
  "timezone": 19800,
  "id": 1248991,
  "name": "Colombo",                 ← ★ City Name
  "cod": 200
}
```

Fields marked with ★ are the five fields required by the practical sheet.

---

## Nested JSON — Explained for Beginners

### What Is Nesting?

JSON responses often contain **objects inside objects** (nesting). To access a nested
value, you use **dot notation** — separating each level with a period.

### Example 1 — `main.temp` (Object Nesting)

```text
main.temp
│    │
│    └── "temp" key inside the "main" object
└── "main" key at the root level
```

In the JSON:

```json
{
  "main": {
    "temp": 30.5,
    "humidity": 74
  }
}
```

- `main` is an **object** containing multiple keys
- `main.temp` means: go into the `main` object, then get the value of `temp`
- `main.humidity` means: go into the `main` object, then get the value of `humidity`

### Example 2 — `wind.speed` (Object Nesting)

```text
wind.speed
│    │
│    └── "speed" key inside the "wind" object
└── "wind" key at the root level
```

In the JSON:

```json
{
  "wind": {
    "speed": 4.12,
    "deg": 250
  }
}
```

- `wind.speed` = `4.12`
- `wind.deg` = `250`

### Example 3 — `weather[0].description` (Array + Object Nesting)

```text
weather[0].description
│       │   │
│       │   └── "description" key inside the object
│       └── First element (index 0) of the array
└── "weather" key at the root level
```

In the JSON:

```json
{
  "weather": [
    {
      "id": 802,
      "main": "Clouds",
      "description": "scattered clouds",
      "icon": "03d"
    }
  ]
}
```

**Key concept:** `weather` is an **array** (enclosed in `[ ]`), not a plain object.
It can contain multiple weather condition objects. We access the **first element**
using index `[0]`.

```text
weather        → the array itself      → [ {...} ]
weather[0]     → the first object      → { "id": 802, "main": "Clouds", ... }
weather[0].description → the value     → "scattered clouds"
weather[0].main → not the same as root "main" → "Clouds"
```

> **Important:** `weather[0].main` (value: `"Clouds"`) and `main.temp` (value: `30.5`)
> refer to **completely different parts** of the JSON. The word "main" appears in two
> different contexts:
> - `weather[0].main` — the weather group name (a string like "Clouds", "Rain")
> - `main` — the root-level object containing temperature, humidity, and pressure

### Example 4 — `name` (Root Level)

```text
name
│
└── "name" key at the root level (no nesting)
```

The `name` field is directly at the root of the JSON object — no nesting required:

```json
{
  "name": "Colombo"
}
```

---

## Visual Field Map

```text
JSON Response
├── name ─────────────────────────────── City Name       ★
├── main
│   ├── temp ─────────────────────────── Temperature     ★
│   ├── feels_like
│   ├── temp_min
│   ├── temp_max
│   ├── pressure
│   ├── humidity ─────────────────────── Humidity         ★
│   ├── sea_level
│   └── grnd_level
├── weather[ ]
│   └── [0]
│       ├── id
│       ├── main
│       ├── description ──────────────── Weather Condition ★
│       └── icon
├── wind
│   ├── speed ────────────────────────── Wind Speed        ★
│   ├── deg
│   └── gust
├── coord
│   ├── lat
│   └── lon
├── clouds
│   └── all
├── sys
│   ├── country
│   ├── sunrise
│   └── sunset
├── visibility
├── dt
├── timezone
├── id
└── cod
```

---

## Geocoding API Response Structure

The Direct Geocoding API response has a simpler structure:

```json
[
  {
    "name": "Colombo",
    "local_names": { "en": "Colombo" },
    "lat": 6.9344,
    "lon": 79.8428,
    "country": "LK"
  }
]
```

### Key Difference: Array vs Object

| API                 | Response Type | Access Pattern               |
| ------------------- | ------------- | ---------------------------- |
| Direct Geocoding    | JSON **array** | `response[0].lat`           |
| Current Weather     | JSON **object**| `response.main.temp`        |

The Geocoding API wraps the result in an array `[ ]`, so the first step is always
to access element `[0]`. The Current Weather API returns a single object `{ }` directly.

---

## Complete Data Flow — JSON Paths

```text
Step 1: Geocoding API Response
──────────────────────────────
response[0].lat  → 6.9344    (passed to Step 2)
response[0].lon  → 79.8428   (passed to Step 2)

Step 2: Current Weather API Response
─────────────────────────────────────
response.name                    → "Colombo"           → City Name
response.main.temp               → 30.5                → Temperature
response.weather[0].description  → "scattered clouds"  → Weather Condition
response.main.humidity           → 74                  → Humidity
response.wind.speed              → 4.12                → Wind Speed
```

---

## Actual Postman Response Analysis

> 📸 **Placeholder for actual Postman response**
>
> When the actual JSON response from Postman testing is provided, it will be
> analyzed here. The field mapping table above will be verified against the
> real response to confirm that all expected fields are present and the
> JSON paths are correct.
>
> If the actual response differs from the documented structure, the differences
> will be noted explicitly.

---

## Summary

| Requirement       | JSON Path                  | Nested? | Container Type |
| ----------------- | -------------------------- | ------- | -------------- |
| City Name         | `name`                     | No      | Root           |
| Temperature       | `main.temp`                | Yes     | Object         |
| Weather Condition | `weather[0].description`   | Yes     | Array + Object |
| Humidity          | `main.humidity`            | Yes     | Object         |
| Wind Speed        | `wind.speed`               | Yes     | Object         |
