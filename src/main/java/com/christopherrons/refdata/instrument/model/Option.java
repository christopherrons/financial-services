package com.christopherrons.refdata.instrument.model;

import com.christopherrons.marketdata.common.enums.subscription.TradingPairEnum;
import com.christopherrons.refdata.instrument.api.Derivative;
import com.christopherrons.refdata.instrument.enums.InstrumentTypeEnum;

import java.util.Objects;

public record Option(TradingPairEnum tradingPairEnum, long strikePrice, long timeToMaturity) implements Derivative {

    private static final InstrumentTypeEnum INSTRUMENT_TYPE_ENUM = InstrumentTypeEnum.OPTION;

    @Override
    public TradingPairEnum getTradingPairEnum() {
        return tradingPairEnum;
    }

    @Override
    public long getTimeToMaturity() {
        return timeToMaturity;
    }

    @Override
    public InstrumentTypeEnum getInstrumentType() {
        return InstrumentTypeEnum.OPTION;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Option)) return false;
        Option option = (Option) o;
        return tradingPairEnum == option.tradingPairEnum &&
                timeToMaturity == option.timeToMaturity &&
                strikePrice == option.strikePrice;
    }

    @Override
    public int hashCode() {
        return Objects.hash(tradingPairEnum);
    }

    @Override
    public String getInstrumentId() {
        return tradingPairEnum + "-" + INSTRUMENT_TYPE_ENUM + "-" + timeToMaturity + "-" + strikePrice;
    }
}
