import React from 'react';
import { View, Text, StyleSheet, TouchableOpacity, Image } from 'react-native';
import { StackNavigationProp } from '@react-navigation/stack';
import { useNavigation } from '@react-navigation/native';
import { RootStackParamList } from '../../App';

type HomeScreenNavigationProp = StackNavigationProp<RootStackParamList, 'Home'>;

const HomeScreen: React.FC = () => {
    const navigation = useNavigation<HomeScreenNavigationProp>();

    return (
        <View style={styles.container}>
            <View style={styles.header}>
                <Text style={styles.title}>Arrowhead Navigator</Text>
                <Text style={styles.subtitle}>Weather-Based Delivery Planning</Text>
            </View>

            <View style={styles.content}>
                <Text style={styles.description}>
                    Monitor delivery routes in the Pacific Northwest for potential weather disruptions,
                    particularly flooding that may impact your supply chain.
                </Text>

                <TouchableOpacity
                    style={styles.button}
                    onPress={() => navigation.navigate('Map')}
                >
                    <Text style={styles.buttonText}>View Weather Map</Text>
                </TouchableOpacity>

                {/* Placeholder for a future feature */}
                <TouchableOpacity
                    style={[styles.button, styles.secondaryButton]}
                >
                    <Text style={styles.secondaryButtonText}>Saved Routes</Text>
                </TouchableOpacity>
            </View>

            <View style={styles.footer}>
                <Text style={styles.footerText}>v1.0.0 - Capstone Project</Text>
            </View>
        </View>
    );
};

const styles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: '#f5f5f5',
    },
    header: {
        padding: 24,
        backgroundColor: '#2E7D32',
        alignItems: 'center',
    },
    title: {
        fontSize: 28,
        fontWeight: 'bold',
        color: 'white',
        marginBottom: 8,
    },
    subtitle: {
        fontSize: 16,
        color: 'rgba(255, 255, 255, 0.8)',
    },
    content: {
        flex: 1,
        padding: 20,
        justifyContent: 'center',
        alignItems: 'center',
    },
    description: {
        fontSize: 16,
        textAlign: 'center',
        marginBottom: 30,
        color: '#333',
        lineHeight: 24,
    },
    button: {
        backgroundColor: '#2E7D32',
        paddingVertical: 12,
        paddingHorizontal: 30,
        borderRadius: 8,
        marginVertical: 10,
        width: '80%',
        alignItems: 'center',
    },
    buttonText: {
        color: 'white',
        fontSize: 16,
        fontWeight: 'bold',
    },
    secondaryButton: {
        backgroundColor: 'transparent',
        borderWidth: 2,
        borderColor: '#2E7D32',
    },
    secondaryButtonText: {
        color: '#2E7D32',
        fontSize: 16,
        fontWeight: 'bold',
    },
    footer: {
        padding: 20,
        alignItems: 'center',
    },
    footerText: {
        color: '#666',
        fontSize: 12,
    },
});

export default HomeScreen;