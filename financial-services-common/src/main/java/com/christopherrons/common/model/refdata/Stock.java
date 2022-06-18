package com.christopherrons.common.model.refdata;

import com.christopherrons.common.api.refdata.Instrument;
import com.christopherrons.common.enums.marketdata.TradingPairEnum;
import com.christopherrons.common.enums.refdata.InstrumentTypeEnum;

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
