// Sample data to use before connecting to the Java backend API
// This represents delivery routes in the Seattle area with risk levels

// Types
export interface RouteCoordinate {
    latitude: number;
    longitude: number;
}

export interface DeliveryRoute {
    id: string;
    name: string;
    description: string;
    riskLevel: number; // 0-10 scale where 10 is highest risk
    weatherConditions: string;
    coordinates: RouteCoordinate[];
    estimatedDeliveryTime: number; // in minutes
    distance: number; // in miles
    supplier: string;
    affectedProducts: string[];
}

// Sample Seattle area coordinates for testing
export const sampleRoutes: DeliveryRoute[] = [
    {
        id: "route1",
        name: "Downtown to Capitol Hill",
        description: "Urban delivery route through downtown Seattle to Capitol Hill neighborhood",
        riskLevel: 2, // Low risk
        weatherConditions: "Light rain, good visibility",
        coordinates: [
            { latitude: 47.6062, longitude: -122.3321 }, // Downtown Seattle
            { latitude: 47.6104, longitude: -122.3260 },
            { latitude: 47.6152, longitude: -122.3214 },
            { latitude: 47.6195, longitude: -122.3185 },
            { latitude: 47.6231, longitude: -122.3142 }, // Capitol Hill
        ],
        estimatedDeliveryTime: 25,
        distance: 2.3,
        supplier: "Urban Greens Nursery",
        affectedProducts: ["Potted herbs", "Decorative plants"]
    },
    {
        id: "route2",
        name: "Ballard to Fremont",
        description: "Route crossing multiple bridges with potential flooding areas",
        riskLevel: 6, // Medium risk
        weatherConditions: "Moderate rain, known drainage issues",
        coordinates: [
            { latitude: 47.6698, longitude: -122.3845 }, // Ballard
            { latitude: 47.6605, longitude: -122.3730 },
            { latitude: 47.6515, longitude: -122.3590 },
            { latitude: 47.6470, longitude: -122.3480 }, // Fremont
        ],
        estimatedDeliveryTime: 20,
        distance: 2.1,
        supplier: "Northgate Farms",
        affectedProducts: ["Fresh produce", "Cut flowers"]
    },
    {
        id: "route3",
        name: "South Seattle to Bellevue",
        description: "Long route crossing Lake Washington with high flood risk areas",
        riskLevel: 8, // High risk
        weatherConditions: "Heavy rainfall, possible flooding",
        coordinates: [
            { latitude: 47.5412, longitude: -122.2714 }, // South Seattle
            { latitude: 47.5494, longitude: -122.2699 },
            { latitude: 47.5587, longitude: -122.2651 },
            { latitude: 47.5667, longitude: -122.2532 },
            { latitude: 47.5750, longitude: -122.2357 },
            { latitude: 47.5902, longitude: -122.2237 }, // Bellevue
        ],
        estimatedDeliveryTime: 45,
        distance: 8.7,
        supplier: "Eastside Organic Farms",
        affectedProducts: ["Seasonal vegetables", "Organic fruit"]
    }
];

// Weather forecast sample data
export interface WeatherForecast {
    date: string;
    conditions: string;
    temperature: number;
    precipitation: number;
    floodRisk: number; // 0-10 scale
}

export const sampleWeatherForecast: WeatherForecast[] = [
    {
        date: '2025-04-17',
        conditions: 'Light Rain',
        temperature: 52,
        precipitation: 0.25,
        floodRisk: 2
    },
    {
        date: '2025-04-18',
        conditions: 'Moderate Rain',
        temperature: 48,
        precipitation: 0.75,
        floodRisk: 5
    },
    {
        date: '2025-04-19',
        conditions: 'Heavy Rain',
        temperature: 45,
        precipitation: 2.1,
        floodRisk: 8
    },
    {
        date: '2025-04-20',
        conditions: 'Moderate Rain',
        temperature: 47,
        precipitation: 0.9,
        floodRisk: 6
    },
    {
        date: '2025-04-21',
        conditions: 'Light Rain',
        temperature: 50,
        precipitation: 0.3,
        floodRisk: 3
    }
];