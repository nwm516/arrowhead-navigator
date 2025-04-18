package com.arrowheadnavigator.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * Represents a waypoint along a delivery route.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class RouteWaypoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double latitude;
    private double longitude;

    private String name;  // Optional name for this waypoint
    private String description;  // Optional description

    private int sequenceNumber;  // Position in the route

    // Is this waypoint a risk point? (e.g., near a flood-prone area)
    private boolean riskPoint;
    private int localRiskLevel;  // 0-10 risk at this specific point
}