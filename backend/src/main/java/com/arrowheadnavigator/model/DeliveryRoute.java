package com.arrowheadnavigator.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.util.List;

/**
 * Represents a delivery route with waypoints and risk assessment.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class DeliveryRoute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String routeId;  // External/public ID
    private String name;
    private String description;

    // Route properties
    private double distanceMiles;
    private int estimatedMinutes;

    // Risk assessment
    private int riskLevel;  // 0-10 scale
    private String weatherConditions;

    // Business impact
    private String supplier;

    @ElementCollection
    private List<String> affectedProducts;

    // Route waypoints (start, end, and intermediate points)
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RouteWaypoint> waypoints;

    // Risk factors that contribute to overall risk level
    @Transient  // Not stored in DB but calculated at runtime
    private List<RiskFactor> riskFactors;
}