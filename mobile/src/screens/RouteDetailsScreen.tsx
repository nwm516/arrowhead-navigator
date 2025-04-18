import React from 'react';
import { View, Text, StyleSheet, ScrollView } from 'react-native';
import { RouteProp } from '@react-navigation/native';
import { RootStackParamList } from '../../App';

// Sample data (would be replaced by API calls)
import { sampleRoutes, sampleWeatherForecast } from '../data/sampleData';

type RouteDetailsScreenRouteProp = RouteProp<RootStackParamList, 'RouteDetails'>;

interface RouteDetailsScreenProps {
    route: RouteDetailsScreenRouteProp;
}

const RouteDetailsScreen: React.FC<RouteDetailsScreenProps> = ({ route }) => {
    const { routeId } = route.params;

    // Find the selected route from our sample data
    const selectedRoute = sampleRoutes.find(r => r.id === routeId);

    if (!selectedRoute) {
        return (
            <View style={styles.errorContainer}>
                <Text style={styles.errorText}>Route not found</Text>
            </View>
        );
    }

    // Helper function to get a text description of risk level
    const getRiskDescription = (riskLevel: number) => {
        if (riskLevel >= 7) return 'High';
        if (riskLevel >= 4) return 'Medium';
        return 'Low';
    };

    // Helper function to get a color based on risk level
    const getRiskColor = (riskLevel: number) => {
        if (riskLevel >= 7) return '#F44336'; // Red
        if (riskLevel >= 4) return '#FF9800'; // Orange
        return '#4CAF50'; // Green
    };

    return (
        <ScrollView style={styles.container}>
            <View style={styles.header}>
                <Text style={styles.title}>{selectedRoute.name}</Text>
                <View style={[
                    styles.riskBadge,
                    { backgroundColor: getRiskColor(selectedRoute.riskLevel) }
                ]}>
                    <Text style={styles.riskText}>
                        {getRiskDescription(selectedRoute.riskLevel)} Risk
                    </Text>
                </View>
            </View>

            <View style={styles.section}>
                <Text style={styles.sectionTitle}>Route Information</Text>
                <Text style={styles.description}>{selectedRoute.description}</Text>

                <View style={styles.detailRow}>
                    <Text style={styles.detailLabel}>Distance:</Text>
                    <Text style={styles.detailValue}>{selectedRoute.distance} miles</Text>
                </View>

                <View style={styles.detailRow}>
                    <Text style={styles.detailLabel}>Estimated Time:</Text>
                    <Text style={styles.detailValue}>{selectedRoute.estimatedDeliveryTime} minutes</Text>
                </View>
            </View>

            <View style={styles.section}>
                <Text style={styles.sectionTitle}>Weather Conditions</Text>
                <Text style={styles.weatherConditions}>{selectedRoute.weatherConditions}</Text>

                <Text style={styles.forecastTitle}>5-Day Forecast</Text>
                {sampleWeatherForecast.map((day, index) => (
                    <View key={index} style={styles.forecastItem}>
                        <Text style={styles.forecastDate}>{day.date}</Text>
                        <Text style={styles.forecastCondition}>{day.conditions}</Text>
                        <Text style={styles.forecastTemp}>{day.temperature}°F</Text>
                        <View style={[
                            styles.riskIndicator,
                            { backgroundColor: getRiskColor(day.floodRisk) }
                        ]} />
                    </View>
                ))}
            </View>

            <View style={styles.section}>
                <Text style={styles.sectionTitle}>Supply Impact</Text>
                <View style={styles.detailRow}>
                    <Text style={styles.detailLabel}>Supplier:</Text>
                    <Text style={styles.detailValue}>{selectedRoute.supplier}</Text>
                </View>

                <Text style={styles.affectedTitle}>Affected Products:</Text>
                {selectedRoute.affectedProducts.map((product, index) => (
                    <Text key={index} style={styles.affectedProduct}>• {product}</Text>
                ))}

                <View style={styles.recommendationBox}>
                    <Text style={styles.recommendationTitle}>Recommendation:</Text>
                    <Text style={styles.recommendationText}>
                        {selectedRoute.riskLevel >= 7
                            ? 'Consider increasing inventory orders by 25% to account for potential delivery delays.'
                            : selectedRoute.riskLevel >= 4
                                ? 'Monitor weather conditions closely. Consider a backup delivery route.'
                                : 'No action needed. Route appears stable.'
                        }
                    </Text>
                </View>
            </View>
        </ScrollView>
    );
};

const styles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: '#f5f5f5',
    },
    errorContainer: {
        flex: 1,
        justifyContent: 'center',
        alignItems: 'center',
        padding: 20,
    },
    errorText: {
        fontSize: 16,
        color: 'red',
        textAlign: 'center',
    },
    header: {
        padding: 16,
        backgroundColor: '#2E7D32',
        flexDirection: 'row',
        justifyContent: 'space-between',
        alignItems: 'center',
    },
    title: {
        fontSize: 20,
        fontWeight: 'bold',
        color: 'white',
        flex: 1,
    },
    riskBadge: {
        paddingHorizontal: 12,
        paddingVertical: 6,
        borderRadius: 16,
    },
    riskText: {
        color: 'white',
        fontWeight: 'bold',
        fontSize: 14,
    },
    section: {
        backgroundColor: 'white',
        margin: 12,
        padding: 16,
        borderRadius: 8,
        shadowColor: '#000',
        shadowOffset: { width: 0, height: 1 },
        shadowOpacity: 0.2,
        shadowRadius: 1.41,
        elevation: 2,
    },
    sectionTitle: {
        fontSize: 18,
        fontWeight: 'bold',
        marginBottom: 12,
        color: '#2E7D32',
    },
    description: {
        fontSize: 15,
        lineHeight: 22,
        marginBottom: 16,
        color: '#333',
    },
    detailRow: {
        flexDirection: 'row',
        justifyContent: 'space-between',
        paddingVertical: 8,
        borderBottomWidth: 1,
        borderBottomColor: '#eee',
    },
    detailLabel: {
        fontSize: 15,
        color: '#666',
    },
    detailValue: {
        fontSize: 15,
        fontWeight: '500',
        color: '#333',
    },
    weatherConditions: {
        fontSize: 16,
        marginBottom: 16,
    },
    forecastTitle: {
        fontSize: 16,
        fontWeight: '500',
        marginBottom: 8,
        marginTop: 8,
    },
    forecastItem: {
        flexDirection: 'row',
        justifyContent: 'space-between',
        alignItems: 'center',
        paddingVertical: 10,
        borderBottomWidth: 1,
        borderBottomColor: '#eee',
    },
    forecastDate: {
        fontSize: 14,
        width: '30%',
    },
    forecastCondition: {
        fontSize: 14,
        width: '30%',
    },
    forecastTemp: {
        fontSize: 14,
        width: '20%',
        textAlign: 'right',
    },
    riskIndicator: {
        width: 16,
        height: 16,
        borderRadius: 8,
    },
    affectedTitle: {
        fontSize: 16,
        fontWeight: '500',
        marginTop: 16,
        marginBottom: 8,
    },
    affectedProduct: {
        fontSize: 15,
        paddingVertical: 4,
        paddingLeft: 8,
    },
    recommendationBox: {
        backgroundColor: '#f8f9fa',
        padding: 12,
        borderRadius: 6,
        marginTop: 16,
        borderLeftWidth: 4,
        borderLeftColor: '#2E7D32',
    },
    recommendationTitle: {
        fontSize: 16,
        fontWeight: 'bold',
        marginBottom: 6,
    },
    recommendationText: {
        fontSize: 15,
        lineHeight: 22,
    },
});

export default RouteDetailsScreen;