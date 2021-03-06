package com.christopherrons.common.model.refdata;

import com.christopherrons.common.api.refdata.Derivative;
import com.christopherrons.common.enums.marketdata.TradingPairEnum;
import com.christopherrons.common.enums.refdata.InstrumentTypeEnum;

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
