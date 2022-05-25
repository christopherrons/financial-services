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
            case FUTURE -> new Future(tradingPairEnum, 0);
            case OPTION -> new Option(tradingPairEnum, 0, 0);
            default -> new InvalidInstrument();
        };
    }

    InstrumentTypeEnum getInstrumentType();

    TradingPairEnum getTradingPairEnum();

    String getInstrumentId();
}
