package com.christopherrons.common.model.refdata;

import com.christopherrons.common.api.refdata.Derivative;
import com.christopherrons.common.enums.refdata.InstrumentTypeEnum;
import com.christopherrons.common.enums.marketdata.TradingPairEnum;

import java.util.Objects;

public record Future(TradingPairEnum tradingPairEnum, long timeToMaturity) implements Derivative {

    private static final InstrumentTypeEnum INSTRUMENT_TYPE_ENUM = InstrumentTypeEnum.FUTURE;

    @Override
    public TradingPairEnum getTradingPairEnum() {
        return tradingPairEnum;
    }

    @Override
    public long getTimeToMaturity() {
        return 0;
    }

    @Override
    public InstrumentTypeEnum getInstrumentType() {
        return InstrumentTypeEnum.FUTURE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Future future)) return false;
        return tradingPairEnum == future.tradingPairEnum && timeToMaturity == future.timeToMaturity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(tradingPairEnum);
    }

    @Override
    public String getInstrumentId() {
        return tradingPairEnum + "-" + INSTRUMENT_TYPE_ENUM + "-" + timeToMaturity;
    }
}
