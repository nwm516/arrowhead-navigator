package com.arrowheadnavigator.service;

import com.arrowheadnavigator.model.DeliveryRoute;
import com.arrowheadnavigator.model.RiskFactor;
import com.arrowheadnavigator.model.RouteWaypoint;
import com.arrowheadnavigator.model.WeatherData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * Service for managing delivery routes and calculating risk levels.
 * This implementation includes sample data for development purposes.
 */
@Service
public class RouteService {

    private final WeatherService weatherService;

    // In-memory storage for demo purposes
    private final List<DeliveryRoute> routes = new ArrayList<>();

    @Autowired
    public RouteService(WeatherService weatherService) {
        this.weatherService = weatherService;
        // Initialize with sample data
        initSampleRoutes();
    }

    /**
     * Get all delivery routes.
     */
    public List<DeliveryRoute> getAllRoutes() {
        // Update risk levels for all routes
        routes.forEach(this::updateRouteRisk);
        return routes;
    }

    /**
     * Get a specific route by ID.
     */
    public DeliveryRoute getRouteById(String routeId) {
        DeliveryRoute route = routes.stream()
                .filter(r -> r.getRouteId().equals(routeId))
                .findFirst()
                .orElse(null);

        if (route != null) {
            updateRouteRisk(route);
        }

        return route;
    }

    /**
     * Update the risk assessment for a route based on current weather conditions.
     */
    private void updateRouteRisk(DeliveryRoute route) {
        List<RiskFactor> riskFactors = new ArrayList<>();
        int maxWaypointRisk = 0;

        // Assess risk at each waypoint
        for (RouteWaypoint waypoint : route.getWaypoints()) {
            int pointRisk = assessWaypointRisk(waypoint);
            waypoint.setLocalRiskLevel(pointRisk);

            if (pointRisk > 5) {
                waypoint.setRiskPoint(true);
            }

            maxWaypointRisk = Math.max(maxWaypointRisk, pointRisk);
        }

        // Add weather as a risk factor
        WeatherData weather = weatherService.getCurrentWeather(
                route.getWaypoints().get(0).getLatitude(),
                route.getWaypoints().get(0).getLongitude());

        riskFactors.add(RiskFactor.builder()
                .name("Current Weather")
                .description(weather.getConditions())
                .impactLevel(weather.getFloodRiskLevel())
                .weight(0.4)
                .build());

        // Add forecast as a risk factor
        int forecastRisk = weatherService.calculateFloodRisk(
                route.getWaypoints().get(0).getLatitude(),
                route.getWaypoints().get(0).getLongitude());

        riskFactors.add(RiskFactor.builder()
                .name("Weather Forecast")
                .description("Based on precipitation forecast for next 72 hours")
                .impactLevel(forecastRisk)
                .weight(0.3)
                .build());

        // Add terrain as a risk factor
        riskFactors.add(RiskFactor.builder()
                .name("Route Terrain")
                .description("Based on elevation changes and known flood zones")
                .impactLevel(maxWaypointRisk)
                .weight(0.3)
                .build());

        // Calculate overall risk level as weighted average
        double weightedRiskSum = riskFactors.stream()
                .mapToDouble(rf -> rf.getImpactLevel() * rf.getWeight())
                .sum();

        int overallRisk = (int) Math.round(weightedRiskSum);

        // Update route
        route.setRiskLevel(overallRisk);
        route.setRiskFactors(riskFactors);
        route.setWeatherConditions(weather.getConditions() + ": " +
                weather.getDescription());
    }

    /**
     * Assess the flood risk at a specific waypoint.
     * This is a simplified assessment for development purposes.
     */
    private int assessWaypointRisk(RouteWaypoint waypoint) {
        // Get the base flood risk for this location
        int baseRisk = weatherService.calculateFloodRisk(
                waypoint.getLatitude(),
                waypoint.getLongitude());

        // In a real implementation, we would adjust based on:
        // - Elevation data
        // - Proximity to water bodies
        // - Known flood zones
        // - Historical flood data for this specific point

        return baseRisk;
    }

    /**
     * Initialize with sample routes for development.
     */
    private void initSampleRoutes() {
        // Route 1: Downtown Seattle to Capitol Hill
        List<RouteWaypoint> route1Waypoints = Arrays.asList(
                RouteWaypoint.builder()
                        .latitude(47.6062)
                        .longitude(-122.3321)
                        .name("Downtown Seattle")
                        .description("Starting point")
                        .sequenceNumber(0)
                        .build(),
                RouteWaypoint.builder()
                        .latitude(47.6104)
                        .longitude(-122.3260)
                        .sequenceNumber(1)
                        .build(),
                RouteWaypoint.builder()
                        .latitude(47.6152)
                        .longitude(-122.3214)
                        .sequenceNumber(2)
                        .build(),
                RouteWaypoint.builder()
                        .latitude(47.6195)
                        .longitude(-122.3185)
                        .sequenceNumber(3)
                        .build(),
                RouteWaypoint.builder()
                        .latitude(47.6231)
                        .longitude(-122.3142)
                        .name("Capitol Hill")
                        .description("Destination point")
                        .sequenceNumber(4)
                        .build()
        );

        DeliveryRoute route1 = DeliveryRoute.builder()
                .routeId("route1")
                .name("Downtown to Capitol Hill")
                .description("Urban delivery route through downtown Seattle to Capitol Hill neighborhood")
                .distanceMiles(2.3)
                .estimatedMinutes(25)
                .waypoints(route1Waypoints)
                .supplier("Urban Greens Nursery")
                .affectedProducts(Arrays.asList("Potted herbs", "Decorative plants"))
                .build();

        // Route 2: Ballard to Fremont
        List<RouteWaypoint> route2Waypoints = Arrays.asList(
                RouteWaypoint.builder()
                        .latitude(47.6698)
                        .longitude(-122.3845)
                        .name("Ballard")
                        .description("Starting point")
                        .sequenceNumber(0)
                        .build(),
                RouteWaypoint.builder()
                        .latitude(47.6605)
                        .longitude(-122.3730)
                        .sequenceNumber(1)
                        .build(),
                RouteWaypoint.builder()
                        .latitude(47.6515)
                        .longitude(-122.3590)
                        .sequenceNumber(2)
                        .build(),
                RouteWaypoint.builder()
                        .latitude(47.6470)
                        .longitude(-122.3480)
                        .name("Fremont")
                        .description("Destination point")
                        .sequenceNumber(3)
                        .build()
        );

        DeliveryRoute route2 = DeliveryRoute.builder()
                .routeId("route2")
                .name("Ballard to Fremont")
                .description("Route crossing multiple bridges with potential flooding areas")
                .distanceMiles(2.1)
                .estimatedMinutes(20)
                .waypoints(route2Waypoints)
                .supplier("Northgate Farms")
                .affectedProducts(Arrays.asList("Fresh produce", "Cut flowers"))
                .build();

        // Route 3: South Seattle to Bellevue
        List<RouteWaypoint> route3Waypoints = Arrays.asList(
                RouteWaypoint.builder()
                        .latitude(47.5412)
                        .longitude(-122.2714)
                        .name("South Seattle")
                        .description("Starting point")
                        .sequenceNumber(0)
                        .build(),
                RouteWaypoint.builder()
                        .latitude(47.5494)
                        .longitude(-122.2699)
                        .sequenceNumber(1)
                        .build(),
                RouteWaypoint.builder()
                        .latitude(47.5587)
                        .longitude(-122.2651)
                        .sequenceNumber(2)
                        .build(),
                RouteWaypoint.builder()
                        .latitude(47.5667)
                        .longitude(-122.2532)
                        .sequenceNumber(3)
                        .build(),
                RouteWaypoint.builder()
                        .latitude(47.5750)
                        .longitude(-122.2357)
                        .sequenceNumber(4)
                        .build(),
                RouteWaypoint.builder()
                        .latitude(47.5902)
                        .longitude(-122.2237)
                        .name("Bellevue")
                        .description("Destination point")
                        .sequenceNumber(5)
                        .build()
        );

        DeliveryRoute route3 = DeliveryRoute.builder()
                .routeId("route3")
                .name("South Seattle to Bellevue")
                .description("Long route crossing Lake Washington with high flood risk areas")
                .distanceMiles(8.7)
                .estimatedMinutes(45)
                .waypoints(route3Waypoints)
                .supplier("Eastside Organic Farms")
                .affectedProducts(Arrays.asList("Seasonal vegetables", "Organic fruit"))
                .build();

        routes.add(route1);
        routes.add(route2);
        routes.add(route3);
    }

    /**
     * Add a new route.
     */
    public DeliveryRoute addRoute(DeliveryRoute route) {
        // Generate a unique ID if one isn't provided
        if (route.getRouteId() == null || route.getRouteId().isEmpty()) {
            route.setRouteId(UUID.randomUUID().toString());
        }

        // Calculate risk before saving
        updateRouteRisk(route);

        routes.add(route);
        return route;
    }

    /**
     * Update an existing route.
     */
    public DeliveryRoute updateRoute(String routeId, DeliveryRoute updatedRoute) {
        DeliveryRoute existingRoute = getRouteById(routeId);

        if (existingRoute == null) {
            return null;
        }

        // Update fields
        existingRoute.setName(updatedRoute.getName());
        existingRoute.setDescription(updatedRoute.getDescription());
        existingRoute.setDistanceMiles(updatedRoute.getDistanceMiles());
        existingRoute.setEstimatedMinutes(updatedRoute.getEstimatedMinutes());
        existingRoute.setSupplier(updatedRoute.getSupplier());
        existingRoute.setAffectedProducts(updatedRoute.getAffectedProducts());

        // Only replace waypoints if provided
        if (updatedRoute.getWaypoints() != null && !updatedRoute.getWaypoints().isEmpty()) {
            existingRoute.setWaypoints(updatedRoute.getWaypoints());
        }

        // Recalculate risk
        updateRouteRisk(existingRoute);

        return existingRoute;
    }

    /**
     * Delete a route.
     */
    public boolean deleteRoute(String routeId) {
        return routes.removeIf(route -> route.getRouteId().equals(routeId));
    }
}