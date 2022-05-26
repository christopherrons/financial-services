package com.christopherrons.refdata.portfolio.model;

public class Position {

    private final String instrumentId;
    private double volume;

    public Position(String instrumentId) {
        this.instrumentId = instrumentId;
    }

    public void increasePosition(final double volume) {
        this.volume = this.volume + volume;
    }

    public void decreasePosition(final double volume) {
        this.volume = this.volume - volume;
    }

    public String getInstrumentId() {
        return instrumentId;
    }

    public double getVolume() {
        return volume;
    }
}
