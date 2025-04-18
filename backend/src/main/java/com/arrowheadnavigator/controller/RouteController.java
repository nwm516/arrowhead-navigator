package com.arrowheadnavigator.controller;

import com.arrowheadnavigator.model.DeliveryRoute;
import com.arrowheadnavigator.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for delivery route management.
 */
@RestController
@RequestMapping("/api/routes")
@CrossOrigin(origins = "*")
public class RouteController {

    private final RouteService routeService;

    @Autowired
    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    /**
     * Get all routes.
     */
    @GetMapping
    public ResponseEntity<List<DeliveryRoute>> getAllRoutes() {
        return ResponseEntity.ok(routeService.getAllRoutes());
    }

    /**
     * Get a specific route by ID.
     */
    @GetMapping("/{routeId}")
    public ResponseEntity<DeliveryRoute> getRouteById(@PathVariable String routeId) {
        DeliveryRoute route = routeService.getRouteById(routeId);

        if (route == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(route);
    }

    /**
     * Create a new route.
     */
    @PostMapping
    public ResponseEntity<DeliveryRoute> createRoute(@RequestBody DeliveryRoute route) {
        DeliveryRoute createdRoute = routeService.addRoute(route);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRoute);
    }

    /**
     * Update an existing route.
     */
    @PutMapping("/{routeId}")
    public ResponseEntity<DeliveryRoute> updateRoute(
            @PathVariable String routeId,
            @RequestBody DeliveryRoute route) {

        DeliveryRoute updatedRoute = routeService.updateRoute(routeId, route);

        if (updatedRoute == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(updatedRoute);
    }

    /**
     * Delete a route.
     */
    @DeleteMapping("/{routeId}")
    public ResponseEntity<Void> deleteRoute(@PathVariable String routeId) {
        boolean deleted = routeService.deleteRoute(routeId);

        if (!deleted) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}