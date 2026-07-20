import React from 'react';
import { SafeAreaView, StyleSheet, Text, View } from 'react-native';

export default function App() {
  return (
    <SafeAreaView style={styles.container}>
      <View style={styles.card}>
        <Text style={styles.title}>DASP Digital Mobile MVP</Text>
        <Text style={styles.subtitle}>React Native Offline Sync Framework</Text>
      </View>
    </SafeAreaView>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#0f172a',
    justifyContent: 'center',
    alignItems: 'center',
  },
  card: {
    padding: 24,
    backgroundColor: '#1e293b',
    borderRadius: 12,
    alignItems: 'center',
  },
  title: {
    color: '#38bdf8',
    fontSize: 20,
    fontWeight: 'bold',
  },
  subtitle: {
    color: '#94a3b8',
    fontSize: 14,
    marginTop: 8,
  },
});
