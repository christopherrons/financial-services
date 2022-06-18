package com.christopherrons.common.model.refdata;

import com.christopherrons.common.api.refdata.Instrument;
import com.christopherrons.common.enums.marketdata.TradingPairEnum;
import com.christopherrons.common.enums.refdata.InstrumentTypeEnum;

public record InvalidInstrument() implements Instrument {
    @Override
    public InstrumentTypeEnum getInstrumentType() {
        return InstrumentTypeEnum.INVALID_INSTRUMENT;
    }

    @Override
    public TradingPairEnum getTradingPairEnum() {
        return TradingPairEnum.INVALID_TRADING_PAIR;
    }

    @Override
    public String getInstrumentId() {
        return getTradingPairEnum().toString() + "-" + getInstrumentType().toString();
    }
}
