package com.christopherrons.refdata.instrument.model;

import com.christopherrons.marketdata.common.enums.subscription.TradingPairEnum;
import com.christopherrons.refdata.instrument.api.Instrument;
import com.christopherrons.refdata.instrument.enums.InstrumentTypeEnum;

import java.util.Objects;

public record Stock(TradingPairEnum tradingPairEnum) implements Instrument {

    private static final InstrumentTypeEnum INSTRUMENT_TYPE_ENUM = InstrumentTypeEnum.STOCK;

    @Override
    public InstrumentTypeEnum getInstrumentType() {
        return INSTRUMENT_TYPE_ENUM;
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

    @Override
    public String getInstrumentId() {
        return tradingPairEnum + "-" + INSTRUMENT_TYPE_ENUM;
    }
}
