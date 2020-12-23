package com.example.flooddetector.model;

public class Indicator {
    private String type;
    private String min;
    private String max;

    public Indicator(String type, String min, String max) {
        this.type = type;
        this.min = min;
        this.max = max;
    }

    public String getType() {
        return type;
    }

    public String getMin() {
        return min;
    }

    public String getMax() {
        return max;
    }
}
