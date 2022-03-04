package com.christopherrons.refdata.model.instruments;

import com.christopherrons.marketdata.common.enums.subscription.TradingPairEnum;
import com.christopherrons.refdata.api.Instrument;
import com.christopherrons.refdata.enums.InstrumentType;

public class InvalidInstrument implements Instrument {
    @Override
    public InstrumentType getInstrumentType() {
        return InstrumentType.INVALID_INSTRUMENT;
    }

    @Override
    public String getTradingPair() {
        return TradingPairEnum.INVALID_TRADING_PAIR.getName();
    }
}
