package com.christopherrons.refdata.instrument.api;

import com.christopherrons.marketdata.common.enums.subscription.TradingPairEnum;
import com.christopherrons.refdata.instrument.enums.InstrumentTypeEnum;
import com.christopherrons.refdata.instrument.model.Future;
import com.christopherrons.refdata.instrument.model.InvalidInstrument;
import com.christopherrons.refdata.instrument.model.Option;
import com.christopherrons.refdata.instrument.model.Stock;

public interface Instrument {
    static Instrument createInstrument(final InstrumentTypeEnum instrumentTypeEnum, final TradingPairEnum tradingPairEnum) {
        switch (instrumentTypeEnum) {
            case STOCK:
                return new Stock(tradingPairEnum);
            case FUTURE:
                return new Future(tradingPairEnum);
            case OPTION:
                return new Option(tradingPairEnum);
            default:
                return new InvalidInstrument();
        }
    }

    InstrumentTypeEnum getInstrumentType();

    TradingPairEnum getTradingPairEnum();
}
