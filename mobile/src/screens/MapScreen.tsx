import React, { useState, useEffect } from 'react';
import { View, StyleSheet, Text, ActivityIndicator, TouchableOpacity } from 'react-native';
import MapView, { Marker, Polyline, PROVIDER_GOOGLE } from 'react-native-maps';
import { useNavigation } from '@react-navigation/native';
import { StackNavigationProp } from '@react-navigation/stack';
import { RootStackParamList } from '../../App';
import * as Location from 'expo-location';

// This would be replaced with actual API calls in the future
import { sampleRoutes } from '../data/sampleData';

type MapScreenNavigationProp = StackNavigationProp<RootStackParamList, 'Map'>;

const MapScreen: React.FC = () => {
    const navigation = useNavigation<MapScreenNavigationProp>();
    const [location, setLocation] = useState<Location.LocationObject | null>(null);
    const [errorMsg, setErrorMsg] = useState<string | null>(null);
    const [loading, setLoading] = useState(true);

    // This would be replaced with actual data from the Java backend
    const routes = sampleRoutes;

    useEffect(() => {
        (async () => {
            let { status } = await Location.requestForegroundPermissionsAsync();
            if (status !== 'granted') {
                setErrorMsg('Permission to access location was denied');
                // Default to Seattle area if permission not granted
                setLocation({
                    coords: {
                        latitude: 47.6062,
                        longitude: -122.3321,
                        altitude: null,
                        accuracy: null,
                        altitudeAccuracy: null,
                        heading: null,
                        speed: null
                    },
                    timestamp: Date.now()
                });
                setLoading(false);
                return;
            }

            try {
                let currentLocation = await Location.getCurrentPositionAsync({});
                setLocation(currentLocation);
            } catch (error) {
                // Default to Seattle area if location fetch fails
                setLocation({
                    coords: {
                        latitude: 47.6062,
                        longitude: -122.3321,
                        altitude: null,
                        accuracy: null,
                        altitudeAccuracy: null,
                        heading: null,
                        speed: null
                    },
                    timestamp: Date.now()
                });
                console.error('Error getting location:', error);
            } finally {
                setLoading(false);
            }
        })();
    }, []);

    // Helper function to determine route color based on risk level
    const getRouteColor = (riskLevel: number) => {
        if (riskLevel >= 7) return 'red';
        if (riskLevel >= 4) return 'orange';
        return 'green';
    };

    if (loading) {
        return (
            <View style={styles.loadingContainer}>
                <ActivityIndicator size="large" color="#2E7D32" />
                <Text style={styles.loadingText}>Loading map...</Text>
            </View>
        );
    }

    if (errorMsg && !location) {
        return (
            <View style={styles.errorContainer}>
                <Text style={styles.errorText}>{errorMsg}</Text>
            </View>
        );
    }

    return (
        <View style={styles.container}>
            {location && (
                <MapView
                    provider={PROVIDER_GOOGLE}
                    style={styles.map}
                    initialRegion={{
                        latitude: location.coords.latitude,
                        longitude: location.coords.longitude,
                        latitudeDelta: 0.0922,
                        longitudeDelta: 0.0421,
                    }}
                >
                    {/* Current location marker */}
                    <Marker
                        coordinate={{
                            latitude: location.coords.latitude,
                            longitude: location.coords.longitude,
                        }}
                        title="Your Location"
                        pinColor="#2E7D32"
                    />

                    {/* Display routes */}
                    {routes.map(route => (
                        <React.Fragment key={route.id}>
                            <Polyline
                                coordinates={route.coordinates}
                                strokeColor={getRouteColor(route.riskLevel)}
                                strokeWidth={4}
                                onPress={() => navigation.navigate('RouteDetails', { routeId: route.id })}
                            />

                            {/* Start point */}
                            <Marker
                                coordinate={route.coordinates[0]}
                                title={`Start: ${route.name}`}
                                pinColor="blue"
                            />

                            {/* End point */}
                            <Marker
                                coordinate={route.coordinates[route.coordinates.length - 1]}
                                title={`End: ${route.name}`}
                                pinColor="red"
                            />
                        </React.Fragment>
                    ))}
                </MapView>
            )}

            {/* Legend */}
            <View style={styles.legend}>
                <Text style={styles.legendTitle}>Risk Level</Text>
                <View style={styles.legendItem}>
                    <View style={[styles.legendColor, { backgroundColor: 'green' }]} />
                    <Text style={styles.legendText}>Low</Text>
                </View>
                <View style={styles.legendItem}>
                    <View style={[styles.legendColor, { backgroundColor: 'orange' }]} />
                    <Text style={styles.legendText}>Medium</Text>
                </View>
                <View style={styles.legendItem}>
                    <View style={[styles.legendColor, { backgroundColor: 'red' }]} />
                    <Text style={styles.legendText}>High</Text>
                </View>
            </View>
        </View>
    );
};

const styles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: '#f5f5f5',
    },
    map: {
        ...StyleSheet.absoluteFillObject,
    },
    loadingContainer: {
        flex: 1,
        justifyContent: 'center',
        alignItems: 'center',
    },
    loadingText: {
        marginTop: 10,
        fontSize: 16,
        color: '#555',
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
    legend: {
        position: 'absolute',
        bottom: 16,
        right: 16,
        backgroundColor: 'white',
        padding: 8,
        borderRadius: 8,
        shadowColor: '#000',
        shadowOffset: {width: 0, height: 2},
        shadowOpacity: 0.25,
        shadowRadius: 3.84,
        elevation: 5,
    },
    legendTitle: {
        fontWeight: 'bold',
        marginBottom: 4,
        fontSize: 14,
    },
    legendItem: {
        flexDirection: 'row',
        alignItems: 'center',
        marginVertical: 2,
    },
    legendColor: {
        width: 16,
        height: 8,
        marginRight: 8,
    },
    legendText: {
        fontSize: 12,
    },
});

export default MapScreen;