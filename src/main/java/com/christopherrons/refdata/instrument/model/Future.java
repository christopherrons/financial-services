package com.christopherrons.refdata.instrument.model;

import com.christopherrons.marketdata.common.enums.subscription.TradingPairEnum;
import com.christopherrons.refdata.instrument.api.Derivative;
import com.christopherrons.refdata.instrument.enums.InstrumentTypeEnum;

import java.util.Objects;

public class Future implements Derivative {
    private final TradingPairEnum tradingPairEnum;

    public Future(TradingPairEnum tradingPairEnum) {
        this.tradingPairEnum = tradingPairEnum;
    }

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
        if (!(o instanceof Future)) return false;
        Future future = (Future) o;
        return tradingPairEnum == future.tradingPairEnum;
    }

    @Override
    public int hashCode() {
        return Objects.hash(tradingPairEnum);
    }
}
