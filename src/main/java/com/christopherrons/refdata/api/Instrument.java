package com.christopherrons.refdata.api;

import com.christopherrons.refdata.enums.InstrumentTypeEnum;
import com.christopherrons.refdata.model.instruments.Future;
import com.christopherrons.refdata.model.instruments.InvalidInstrument;
import com.christopherrons.refdata.model.instruments.Option;
import com.christopherrons.refdata.model.instruments.Stock;

public interface Instrument {
    InstrumentTypeEnum getInstrumentType();

    String getTradingPair();

    static Instrument createInstrument(final InstrumentTypeEnum instrumentTypeEnum, final String tradingPair) {
        switch (instrumentTypeEnum) {
            case STOCK:
                return new Stock(tradingPair);
            case FUTURE:
                return new Future(tradingPair);
            case OPTION:
                return new Option(tradingPair);
            default:
                return new InvalidInstrument();
        }
    }

}
