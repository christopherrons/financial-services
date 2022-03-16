package com.christopherrons.refdata.instrument.api;

import com.christopherrons.marketdata.common.enums.subscription.TradingPairEnum;
import com.christopherrons.refdata.instrument.enums.InstrumentTypeEnum;
import com.christopherrons.refdata.instrument.model.Future;
import com.christopherrons.refdata.instrument.model.InvalidInstrument;
import com.christopherrons.refdata.instrument.model.Option;
import com.christopherrons.refdata.instrument.model.Stock;

public interface Instrument {
    static Instrument createInstrument(final InstrumentTypeEnum instrumentTypeEnum, final TradingPairEnum tradingPairEnum) {
        return switch (instrumentTypeEnum) {
            case STOCK -> new Stock(tradingPairEnum);
            case FUTURE -> new Future(tradingPairEnum);
            case OPTION -> new Option(tradingPairEnum);
            default -> new InvalidInstrument();
        };
    }

    InstrumentTypeEnum getInstrumentType();

    TradingPairEnum getTradingPairEnum();
}
