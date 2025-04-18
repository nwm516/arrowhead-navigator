package com.arrowheadnavigator.service;

import com.arrowheadnavigator.model.WeatherData;
import com.arrowheadnavigator.model.WeatherForecast;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Service for retrieving and processing weather data.
 * This implementation includes simulated data for development purposes.
 * In a production environment, this would integrate with the National Weather Service API.
 */
@Service
public class WeatherService {

    private final RestTemplate restTemplate;
    private final String weatherApiBaseUrl;

    // For demo purposes, we'll seed a random generator to get consistent "random" data
    private final Random random = new Random(42);

    public WeatherService(
            RestTemplate restTemplate,
            @Value("${weather.api.base-url}") String weatherApiBaseUrl) {
        this.restTemplate = restTemplate;
        this.weatherApiBaseUrl = weatherApiBaseUrl;
    }

    /**
     * Get current weather conditions for a location.
     * This is currently mocked for development.
     */
    public WeatherData getCurrentWeather(double latitude, double longitude) {
        // In a real implementation, we'd call the weather API:
        // String url = weatherApiBaseUrl + "/points/" + latitude + "," + longitude;
        // ResponseEntity<NwsPointResponse> response = restTemplate.getForEntity(url, NwsPointResponse.class);

        // For now, return simulated data:
        return createMockWeatherData(latitude, longitude);
    }

    /**
     * Get weather forecast for a location.
     * This is currently mocked for development.
     */
    public List<WeatherForecast> getWeatherForecast(double latitude, double longitude, int days) {
        // In a real implementation, we'd call the weather API:
        // String url = weatherApiBaseUrl + "/points/" + latitude + "," + longitude + "/forecast";

        // For now, return simulated data:
        return createMockForecast(latitude, longitude, days);
    }

    /**
     * Calculate flood risk for a location.
     * This is a simplified algorithm for development.
     * A real implementation would incorporate:
     * - Recent rainfall data
     * - Soil saturation
     * - Elevation and terrain
     * - Proximity to water bodies
     * - Historical flood data
     */
    public int calculateFloodRisk(double latitude, double longitude) {
        // Get current weather and forecast
        WeatherData current = getCurrentWeather(latitude, longitude);
        List<WeatherForecast> forecast = getWeatherForecast(latitude, longitude, 3);

        // Simple algorithm: calculate based on recent rainfall and expected rainfall
        double recentRainfall = current.getRecentRainfallInches();

        // Sum expected rainfall over next 3 days
        double expectedRainfall = forecast.stream()
                .mapToDouble(WeatherForecast::getExpectedRainfallInches)
                .sum();

        // Base score on combined rainfall
        double totalRainfall = recentRainfall + expectedRainfall;

        // Convert to a 0-10 scale
        int riskScore = (int) Math.min(10, Math.round(totalRainfall * 2));

        // In a real implementation, we'd adjust based on terrain, soil conditions, etc.
        // For some locations like Seattle area, we might have known flood-prone areas

        return riskScore;
    }

    /**
     * Create simulated weather data for development purposes.
     */
    private WeatherData createMockWeatherData(double latitude, double longitude) {
        String[] conditions = {"Clear", "Partly Cloudy", "Cloudy", "Light Rain", "Heavy Rain"};

        // For Seattle area, increase chance of rain
        boolean isPNW = latitude > 45 && latitude < 49 && longitude > -125 && longitude < -120;
        int conditionIndex = isPNW ?
                random.nextInt(2) + 3 : // Favor rainy conditions for PNW
                random.nextInt(conditions.length);

        double recentRainfall = isPNW ?
                0.5 + random.nextDouble() * 2.0 : // Higher rainfall for PNW
                random.nextDouble() * 1.0;

        // Create simulated data
        return WeatherData.builder()
                .latitude(latitude)
                .longitude(longitude)
                .location("Location near " + latitude + ", " + longitude)
                .conditions(conditions[conditionIndex])
                .description("Simulated weather data for development")
                .temperatureFahrenheit(45 + random.nextDouble() * 20) // 45-65°F
                .humidity(70 + random.nextDouble() * 30) // 70-100%
                .windSpeedMph(5 + random.nextDouble() * 15) // 5-20 mph
                .windDirection(random.nextInt(360)) // 0-359 degrees
                .precipitationInches(conditionIndex >= 3 ? 0.1 + random.nextDouble() * 0.5 : 0)
                .precipitationProbability(conditionIndex >= 2 ? 50 + random.nextDouble() * 50 : 0)
                .recentRainfallInches(recentRainfall)
                .floodRiskLevel(calculateSimpleFloodRisk(recentRainfall, conditionIndex))
                .observationTime(LocalDateTime.now().minusHours(1))
                .retrievalTime(LocalDateTime.now())
                .build();
    }

    /**
     * Create simulated forecast data for development purposes.
     */
    private List<WeatherForecast> createMockForecast(double latitude, double longitude, int days) {
        String[] conditions = {"Clear", "Partly Cloudy", "Cloudy", "Light Rain", "Heavy Rain"};

        // For Seattle area, increase chance of rain
        boolean isPNW = latitude > 45 && latitude < 49 && longitude > -125 && longitude < -120;

        List<WeatherForecast> forecast = new ArrayList<>();

        for (int i = 0; i < days; i++) {
            // More likely to have rain in forecast for Pacific Northwest
            int conditionIndex = isPNW ?
                    Math.min(4, random.nextInt(3) + i % 3) : // Progressively more rainy for PNW
                    random.nextInt(conditions.length);

            double expectedRainfall = conditionIndex >= 3 ?
                    (conditionIndex == 4 ? 1.0 + random.nextDouble() * 1.5 : 0.1 + random.nextDouble() * 0.7) :
                    0;

            // Adjust soil saturation based on rainfall
            double soilSaturation = Math.min(100, 60 + expectedRainfall * 20);

            // Calculate flood risk
            int floodRisk = calculateSimpleFloodRisk(expectedRainfall, conditionIndex);
            String riskDesc = getRiskDescription(floodRisk);

            forecast.add(WeatherForecast.builder()
                    .latitude(latitude)
                    .longitude(longitude)
                    .forecastDate(LocalDate.now().plusDays(i))
                    .conditions(conditions[conditionIndex])
                    .description("Simulated forecast data for development")
                    .highTemperatureFahrenheit(45 + random.nextDouble() * 20) // 45-65°F
                    .lowTemperatureFahrenheit(35 + random.nextDouble() * 15) // 35-50°F
                    .precipitationProbability(conditionIndex >= 2 ? 50 + random.nextDouble() * 50 : 0)
                    .expectedRainfallInches(expectedRainfall)
                    .humidity(70 + random.nextDouble() * 30) // 70-100%
                    .windSpeedMph(5 + random.nextDouble() * 15) // 5-20 mph
                    .floodRiskLevel(floodRisk)
                    .floodRiskDescription(riskDesc)
                    .soilSaturationPct(soilSaturation)
                    .build());
        }

        return forecast;
    }

    /**
     * Simple formula to calculate flood risk based on rainfall and conditions.
     * This is a simplified algorithm for development purposes.
     */
    private int calculateSimpleFloodRisk(double rainfall, int conditionIndex) {
        // Start with base risk from rainfall amount
        double baseRisk = rainfall * 2.5; // 0.5" rain = risk level 1.25, 2" rain = risk level 5

        // Adjust for weather conditions
        double conditionMultiplier = 1.0;
        if (conditionIndex == 4) { // Heavy rain
            conditionMultiplier = 1.5;
        } else if (conditionIndex == 3) { // Light rain
            conditionMultiplier = 1.2;
        }

        int riskLevel = (int) Math.min(10, Math.round(baseRisk * conditionMultiplier));
        return Math.max(0, riskLevel); // Ensure risk is between 0-10
    }

    /**
     * Get a description of flood risk based on the numeric level.
     */
    private String getRiskDescription(int riskLevel) {
        if (riskLevel >= 8) {
            return "High risk of flooding. Consider alternate routes.";
        } else if (riskLevel >= 5) {
            return "Moderate flood risk. Monitor conditions.";
        } else if (riskLevel >= 2) {
            return "Low flood risk. Exercise normal caution.";
        } else {
            return "Minimal flood risk.";
        }
    }
}