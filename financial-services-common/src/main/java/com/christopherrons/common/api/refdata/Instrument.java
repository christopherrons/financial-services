package com.christopherrons.common.api.refdata;


import com.christopherrons.common.enums.marketdata.TradingPairEnum;
import com.christopherrons.common.enums.refdata.InstrumentTypeEnum;
import com.christopherrons.common.model.refdata.Future;
import com.christopherrons.common.model.refdata.InvalidInstrument;
import com.christopherrons.common.model.refdata.Option;
import com.christopherrons.common.model.refdata.Stock;

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
