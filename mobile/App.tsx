import React from 'react';
import { SafeAreaProvider } from 'react-native-safe-area-context';
import { StatusBar } from 'expo-status-bar';
import { NavigationContainer } from '@react-navigation/native';
import { createStackNavigator } from '@react-navigation/stack';

// Import screens (we'll create these later)
import HomeScreen from './src/screens/HomeScreen';
import MapScreen from './src/screens/MapScreen';
import RouteDetailsScreen from './src/screens/RouteDetailsScreen';

// Define the type for our navigation parameters
export type RootStackParamList = {
    Home: undefined;
    Map: undefined;
    RouteDetails: { routeId: string };
};

const Stack = createStackNavigator<RootStackParamList>();

export default function App() {
    return (
        <SafeAreaProvider>
            <NavigationContainer>
                <Stack.Navigator
                    initialRouteName="Home"
                    screenOptions={{
                        headerStyle: {
                            backgroundColor: '#2E7D32', // Green color theme for Arrowhead
                        },
                        headerTintColor: '#fff',
                        headerTitleStyle: {
                            fontWeight: 'bold',
                        },
                    }}
                >
                    <Stack.Screen
                        name="Home"
                        component={HomeScreen}
                        options={{ title: 'Arrowhead Navigator' }}
                    />
                    <Stack.Screen
                        name="Map"
                        component={MapScreen}
                        options={{ title: 'Weather Map' }}
                    />
                    <Stack.Screen
                        name="RouteDetails"
                        component={RouteDetailsScreen}
                        options={{ title: 'Route Details' }}
                    />
                </Stack.Navigator>
            </NavigationContainer>
            <StatusBar style="auto" />
        </SafeAreaProvider>
    );
}