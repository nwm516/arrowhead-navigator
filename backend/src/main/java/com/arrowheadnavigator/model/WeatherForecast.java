package com.arrowheadnavigator.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;

/**
 * Represents a daily weather forecast for a specific location.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class WeatherForecast {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double latitude;
    private double longitude;

    private LocalDate forecastDate;

    private String conditions;
    private String description;

    private double highTemperatureFahrenheit;
    private double lowTemperatureFahrenheit;

    private double precipitationProbability;
    private double expectedRainfallInches;

    private double humidity;
    private double windSpeedMph;

    // Flood risk assessment
    private int floodRiskLevel; // 0-10 scale (10 being highest risk)
    private String floodRiskDescription;

    // Soil saturation prediction (important for flood risk)
    private double soilSaturationPct;
}