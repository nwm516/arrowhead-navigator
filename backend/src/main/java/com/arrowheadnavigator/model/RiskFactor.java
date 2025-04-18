package com.arrowheadnavigator.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a specific factor contributing to the overall risk level.
 * This is used to explain the risk assessment to users.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RiskFactor {

    private String name;  // Name of the risk factor
    private String description;  // Detailed description
    private int impactLevel;  // 0-10 scale
    private double weight;  // Weight of this factor in the overall assessment (0.0-1.0)
}