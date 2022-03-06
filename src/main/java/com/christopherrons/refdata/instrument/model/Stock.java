package com.christopherrons.refdata.instrument.model;

import com.christopherrons.marketdata.common.enums.subscription.TradingPairEnum;
import com.christopherrons.refdata.instrument.api.Instrument;
import com.christopherrons.refdata.instrument.enums.InstrumentTypeEnum;

import java.util.Objects;

public class Stock implements Instrument {

    private final TradingPairEnum tradingPairEnum;

    public Stock(TradingPairEnum tradingPairEnum) {
        this.tradingPairEnum = tradingPairEnum;
    }

    @Override
    public InstrumentTypeEnum getInstrumentType() {
        return InstrumentTypeEnum.STOCK;
    }

    @Override
    public TradingPairEnum getTradingPairEnum() {
        return tradingPairEnum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Stock)) return false;
        Stock stock = (Stock) o;
        return tradingPairEnum == stock.tradingPairEnum;
    }

    @Override
    public int hashCode() {
        return Objects.hash(tradingPairEnum);
    }
}
