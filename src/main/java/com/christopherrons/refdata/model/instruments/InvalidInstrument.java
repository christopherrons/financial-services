package com.christopherrons.refdata.model.instruments;

import com.christopherrons.marketdata.common.enums.subscription.TradingPairEnum;
import com.christopherrons.refdata.api.Instrument;
import com.christopherrons.refdata.enums.InstrumentTypeEnum;

public class InvalidInstrument implements Instrument {
    @Override
    public InstrumentTypeEnum getInstrumentType() {
        return InstrumentTypeEnum.INVALID_INSTRUMENT;
    }

    @Override
    public String getTradingPair() {
        return TradingPairEnum.INVALID_TRADING_PAIR.getName();
    }
}
