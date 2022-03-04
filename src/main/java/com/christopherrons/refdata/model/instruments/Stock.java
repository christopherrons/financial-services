package com.christopherrons.refdata.model.instruments;

import com.christopherrons.refdata.api.Instrument;
import com.christopherrons.refdata.enums.InstrumentType;

public class Stock implements Instrument {

    private final String tradingPair;

    public Stock(String tradingPair) {
        this.tradingPair = tradingPair;
    }

    @Override
    public InstrumentType getInstrumentType() {
        return InstrumentType.STOCK;
    }

    @Override
    public String getTradingPair() {
        return tradingPair;
    }
}
