package com.arrowheadnavigator.controller;

import com.arrowheadnavigator.model.WeatherData;
import com.arrowheadnavigator.model.WeatherForecast;
import com.arrowheadnavigator.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for weather-related endpoints.
 * Provides access to current weather conditions and forecasts.
 */
@RestController
@RequestMapping("/api/weather")
@CrossOrigin(origins = "*")
public class WeatherController {

    private final WeatherService weatherService;

    @Autowired
    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    /**
     * Get current weather conditions for a specific location.
     *
     * @param latitude  Location latitude
     * @param longitude Location longitude
     * @return Current weather data
     */
    @GetMapping("/current")
    public ResponseEntity<WeatherData> getCurrentWeather(
            @RequestParam("latitude") double latitude,
            @RequestParam("longitude") double longitude) {

        WeatherData weatherData = weatherService.getCurrentWeather(latitude, longitude);
        return ResponseEntity.ok(weatherData);
    }

    /**
     * Get weather forecast for a specific location.
     *
     * @param latitude  Location latitude
     * @param longitude Location longitude
     * @param days      Number of days to forecast (max 7)
     * @return List of forecast data
     */
    @GetMapping("/forecast")
    public ResponseEntity<List<WeatherForecast>> getWeatherForecast(
            @RequestParam("latitude") double latitude,
            @RequestParam("longitude") double longitude,
            @RequestParam(value = "days", defaultValue = "5") int days) {

        if (days < 1 || days > 7) {
            days = 5; // Default to 5 days if out of range
        }

        List<WeatherForecast> forecast = weatherService.getWeatherForecast(latitude, longitude, days);
        return ResponseEntity.ok(forecast);
    }

    /**
     * Get flood risk assessment for a location.
     * This is a simplified endpoint that would eventually incorporate
     * more sophisticated risk modeling based on terrain, historical data, etc.
     *
     * @param latitude  Location latitude
     * @param longitude Location longitude
     * @return Flood risk score (0-10)
     */
    @GetMapping("/flood-risk")
    public ResponseEntity<Integer> getFloodRisk(
            @RequestParam("latitude") double latitude,
            @RequestParam("longitude") double longitude) {

        int riskScore = weatherService.calculateFloodRisk(latitude, longitude);
        return ResponseEntity.ok(riskScore);
    }
}