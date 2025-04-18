import axios from 'axios';
import { DeliveryRoute } from '../data/sampleData';

// Configuration
// In a real app, you would use environment variables for different environments
const API_BASE_URL = 'http://10.0.2.2:8080/api'; // Android emulator localhost
// const API_BASE_URL = 'http://localhost:8080/api'; // For iOS simulator

// Create axios instance with default config
const apiClient = axios.create({
    baseURL: API_BASE_URL,
    headers: {
        'Content-Type': 'application/json',
    },
    timeout: 10000, // 10 seconds
});

// API functions for routes
export const routeApi = {
    /**
     * Get all delivery routes with current risk assessments
     */
    getAllRoutes: async (): Promise<DeliveryRoute[]> => {
        try {
            // For development, use sample data if API is not ready
            if (process.env.NODE_ENV === 'development' && process.env.USE_SAMPLE_DATA === 'true') {
                // Import directly from sampleData to avoid circular dependency
                const { sampleRoutes } = require('../data/sampleData');
                return Promise.resolve(sampleRoutes);
            }

            const response = await apiClient.get('/routes');
            return response.data;
        } catch (error) {
            console.error('Error fetching routes:', error);
            // Fall back to sample data if API fails
            const { sampleRoutes } = require('../data/sampleData');
            return sampleRoutes;
        }
    },

    /**
     * Get a specific route by ID
     */
    getRouteById: async (routeId: string): Promise<DeliveryRoute | null> => {
        try {
            // For development, use sample data if API is not ready
            if (process.env.NODE_ENV === 'development' && process.env.USE_SAMPLE_DATA === 'true') {
                const { sampleRoutes } = require('../data/sampleData');
                const route = sampleRoutes.find(r => r.id === routeId);
                return Promise.resolve(route || null);
            }

            const response = await apiClient.get(`/routes/${routeId}`);
            return response.data;
        } catch (error) {
            console.error(`Error fetching route ${routeId}:`, error);
            // Fall back to sample data if API fails
            const { sampleRoutes } = require('../data/sampleData');
            return sampleRoutes.find(r => r.id === routeId) || null;
        }
    },
};

// API functions for weather
export const weatherApi = {
    /**
     * Get current weather for a location
     */
    getCurrentWeather: async (latitude: number, longitude: number) => {
        try {
            const response = await apiClient.get('/weather/current', {
                params: { latitude, longitude }
            });
            return response.data;
        } catch (error) {
            console.error('Error fetching current weather:', error);
            throw error;
        }
    },

    /**
     * Get weather forecast for a location
     */
    getWeatherForecast: async (latitude: number, longitude: number, days = 5) => {
        try {
            const response = await apiClient.get('/weather/forecast', {
                params: { latitude, longitude, days }
            });
            return response.data;
        } catch (error) {
            console.error('Error fetching weather forecast:', error);
            throw error;
        }
    },

    /**
     * Get flood risk for a location
     */
    getFloodRisk: async (latitude: number, longitude: number): Promise<number> => {
        try {
            const response = await apiClient.get('/weather/flood-risk', {
                params: { latitude, longitude }
            });
            return response.data;
        } catch (error) {
            console.error('Error fetching flood risk:', error);
            // Return a default moderate risk if API fails
            return 5;
        }
    },
};

export default {
    routes: routeApi,
    weather: weatherApi,
};