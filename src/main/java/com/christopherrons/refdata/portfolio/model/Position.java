package com.christopherrons.refdata.portfolio.model;

import com.christopherrons.refdata.instrument.api.Instrument;

public class Position {

    private final Instrument instrument;
    private double volume;

    public Position(Instrument instrument, double volume) {
        this.instrument = instrument;
        this.volume = volume;
    }

    public void increasePosition(final double volume) {
        this.volume = this.volume + volume;
    }

    public void decreasePosition(final double volume) {
        this.volume = this.volume - volume;
    }

    public Instrument getInstrument() {
        return instrument;
    }

    public double getVolume() {
        return volume;
    }
}
