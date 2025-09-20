package com.example.domain;

public final class OrderIds {
    private static long nextId = 1001; // Start from 1001 as shown in sample output
    
    private OrderIds() {
        // Private constructor to prevent instantiation
    }
    
    public static long next() {
        return nextId++;
    }
    
    // Optional: reset method for testing
    public static void reset() {
        nextId = 1001;
    }
}