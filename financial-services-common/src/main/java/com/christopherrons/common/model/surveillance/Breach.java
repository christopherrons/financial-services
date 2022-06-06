package com.christopherrons.common.model.surveillance;

public record Breach(String name, String breachValue, String value) {

    @Override
    public String toString() {
        return "Breach{" +
                "name='" + name + '\'' +
                ", breachValue='" + breachValue + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
