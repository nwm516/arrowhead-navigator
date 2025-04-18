package com.arrowheadnavigator.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;

/**
 * Represents current weather conditions at a specific location.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class WeatherData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double latitude;
    private double longitude;

    private String location;
    private String conditions;
    private String description;

    private double temperatureFahrenheit;
    private double humidity;
    private double windSpeedMph;
    private int windDirection;

    private double precipitationInches;
    private double precipitationProbability;

    // Flood risk factors
    private double recentRainfallInches; // Accumulated rainfall in past 24h
    private int floodRiskLevel; // 0-10 scale (10 being highest risk)

    private LocalDateTime observationTime;
    private LocalDateTime retrievalTime;
}