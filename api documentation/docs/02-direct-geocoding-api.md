# Direct Geocoding API

## API Name

**OpenWeather Geocoding API — Direct Geocoding (Coordinates by Location Name)**

## Purpose

The Direct Geocoding API converts a **city name** (or location name) into **geographic
coordinates** (latitude and longitude). In this project, it serves as the first step in
the API call chain — translating the user's text input into coordinates that the Current
Weather API requires.

```text
City Name → Direct Geocoding API → Latitude + Longitude
```

## Official Documentation

- **URL:** [https://openweathermap.org/api/geocoding-api](https://openweathermap.org/api/geocoding-api)
- **Section:** Direct geocoding → Coordinates by location name

---

## Endpoint

```
http://api.openweathermap.org/geo/1.0/direct
```

## HTTP Method

```
GET
```

---

## Query Parameters

| Parameter | Required | Description                                                                                                                                                  |
| --------- | -------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------ |
| `q`       | Yes      | City name, state code (only for the US), and country code, separated by commas. Uses [ISO 3166](https://en.wikipedia.org/wiki/ISO_3166-1_alpha-2) country codes. |
| `appid`   | Yes      | Your unique OpenWeather API key.                                                                                                                              |
| `limit`   | No       | Number of locations in the API response (up to 5 results can be returned). Default behavior returns multiple matches if `limit` is not specified.              |

### Parameter Details

#### `q` — Location Query

The `q` parameter accepts the location in the following format:

```text
{city name},{state code},{country code}
```

- **City name** — required (e.g., `Colombo`)
- **State code** — only used for US locations (e.g., `OH` for Ohio)
- **Country code** — optional but recommended, uses ISO 3166-1 alpha-2 codes

Examples:

| Query                  | Meaning                                 |
| ---------------------- | --------------------------------------- |
| `q=Colombo`            | Search for "Colombo" worldwide          |
| `q=Colombo,LK`         | Search for "Colombo" in Sri Lanka       |
| `q=London,GB`           | Search for "London" in Great Britain    |
| `q=Portland,OR,US`      | Search for "Portland" in Oregon, US     |

#### Why `LK` Is Used for Sri Lanka

The country code `LK` is the **ISO 3166-1 alpha-2** code assigned to Sri Lanka.
The OpenWeather API documentation states that the `q` parameter uses ISO 3166 country
codes. Using `LK` ensures the API returns coordinates specifically for Colombo,
Sri Lanka, rather than other cities named "Colombo" elsewhere in the world.

> **Reference:** [ISO 3166-1 alpha-2 — Sri Lanka](https://en.wikipedia.org/wiki/ISO_3166-2:LK)

#### `appid` — API Key

Your unique API key obtained from your OpenWeather account. This key authenticates
your request.

> ⚠️ **Security:** Never expose your API key in public repositories or documentation.
> Use `YOUR_API_KEY` as a placeholder.

#### `limit` — Result Count

Controls how many matching locations are returned. For this project, we use `limit=1`
because we only need the **first (most relevant) match** for the entered city name.

---

## Example Request

### Sri Lanka Example — Colombo

**Request URL:**

```
http://api.openweathermap.org/geo/1.0/direct?q=Colombo,LK&limit=1&appid=YOUR_API_KEY
```

**Breakdown:**

| Component   | Value                                          |
| ----------- | ---------------------------------------------- |
| Base URL    | `http://api.openweathermap.org/geo/1.0/direct` |
| `q`         | `Colombo,LK`                                  |
| `limit`     | `1`                                            |
| `appid`     | `YOUR_API_KEY`                                 |

---

## Response Format

The API returns a **JSON array** (not a JSON object). Each element in the array
represents one matching location.

### Example Response (from Official Documentation)

```json
[
  {
    "name": "Colombo",
    "local_names": {
      "en": "Colombo",
      "si": "කොළඹ",
      "ta": "கொழும்பு"
    },
    "lat": 6.9344,
    "lon": 79.8428,
    "country": "LK"
  }
]
```

> **Note:** The actual field values will vary. The `local_names` object may contain
> different language keys depending on the city. The `state` field may or may not
> be present depending on the location.

---

## Response Fields

According to the official OpenWeather Geocoding API documentation, the response
contains the following fields:

| Field         | Type   | Description                                                                     |
| ------------- | ------ | ------------------------------------------------------------------------------- |
| `name`        | String | Name of the found location                                                      |
| `local_names` | Object | Name of the found location in different languages (keys are language codes)      |
| `lat`         | Number | Geographical coordinate — latitude                                              |
| `lon`         | Number | Geographical coordinate — longitude                                             |
| `country`     | String | Country code (ISO 3166-1 alpha-2)                                               |
| `state`       | String | State or administrative division (where available; not present for all locations)|

### Important Fields for This Project

For this application, only **three fields** from the Geocoding response are needed:

| Field     | Purpose                                      | Example     |
| --------- | -------------------------------------------- | ----------- |
| `name`    | Confirm the resolved city name               | `"Colombo"` |
| `lat`     | Latitude — passed to Current Weather API     | `6.9344`    |
| `lon`     | Longitude — passed to Current Weather API    | `79.8428`   |

### Understanding the Array Response

The Geocoding API returns an **array** `[ ... ]`, not a single object `{ ... }`.

```text
Response:  [ { location1 }, { location2 }, ... ]
                  ↑
            First result = index 0
```

Since we use `limit=1`, the array will contain at most **one element**. To access
the first result, use index `0`:

```text
response[0].lat  →  latitude
response[0].lon  →  longitude
response[0].name →  city name
```

---

## Postman Test Result

> 📸 **Postman screenshot placeholder**
>
> When the actual Postman screenshot is provided, it will be inserted here with the caption:
>
> **Figure 1 — Successful OpenWeather Direct Geocoding request in Postman**
>
> The screenshot should show:
> - HTTP method: GET
> - Request URL with parameters
> - HTTP 200 OK status
> - JSON array response with lat, lon, and name fields
>
> ⚠️ Any visible API key in the screenshot will be redacted in documentation.

---

## Error Handling

| Scenario              | HTTP Status | Response Body                          |
| --------------------- | ----------- | -------------------------------------- |
| Valid city name        | `200 OK`    | JSON array with matching locations     |
| Invalid city name     | `200 OK`    | Empty JSON array: `[]`                 |
| Missing API key       | `401`       | `{"cod":401,"message":"..."}`          |
| Invalid API key       | `401`       | `{"cod":401,"message":"..."}`          |

> **Note:** An invalid city name does **not** return an error status code. It returns
> `200 OK` with an empty array `[]`. The application must check whether the array
> is empty before proceeding to the weather API call.

---

## Summary

| Property          | Value                                                    |
| ----------------- | -------------------------------------------------------- |
| API Name          | OpenWeather Geocoding API — Direct Geocoding             |
| Endpoint          | `http://api.openweathermap.org/geo/1.0/direct`           |
| HTTP Method       | GET                                                      |
| Required Params   | `q` (city query), `appid` (API key)                      |
| Optional Params   | `limit` (max results, up to 5)                           |
| Response Format   | JSON array                                               |
| Key Output Fields | `lat`, `lon`, `name`, `country`                          |
| Role in App       | Translates city name → coordinates for weather API call  |
