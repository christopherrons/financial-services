package com.christopherrons.refdata.instrument.model;

import com.christopherrons.marketdata.common.enums.subscription.TradingPairEnum;
import com.christopherrons.refdata.instrument.api.Instrument;
import com.christopherrons.refdata.instrument.enums.InstrumentTypeEnum;

public class InvalidInstrument implements Instrument {
    @Override
    public InstrumentTypeEnum getInstrumentType() {
        return InstrumentTypeEnum.INVALID_INSTRUMENT;
    }

    @Override
    public TradingPairEnum getTradingPairEnum() {
        return TradingPairEnum.INVALID_TRADING_PAIR;
    }
}
