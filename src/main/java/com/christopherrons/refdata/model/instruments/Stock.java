package com.christopherrons.refdata.model.instruments;

import com.christopherrons.refdata.api.Instrument;
import com.christopherrons.refdata.enums.InstrumentTypeEnum;

public class Stock implements Instrument {

    private final String tradingPair;

    public Stock(String tradingPair) {
        this.tradingPair = tradingPair;
    }

    @Override
    public InstrumentTypeEnum getInstrumentType() {
        return InstrumentTypeEnum.STOCK;
    }

    @Override
    public String getTradingPair() {
        return tradingPair;
    }
}
