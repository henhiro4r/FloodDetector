package com.example.flooddetector.model;

public class Indicator {
    private String type;
    private String description;

    public Indicator(String type, String description) {
        this.type = type;
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }
}
