package com.christopherrons.refdata.api;

import com.christopherrons.refdata.enums.InstrumentType;
import com.christopherrons.refdata.model.instruments.Future;
import com.christopherrons.refdata.model.instruments.InvalidInstrument;
import com.christopherrons.refdata.model.instruments.Option;
import com.christopherrons.refdata.model.instruments.Stock;

public interface Instrument {
    InstrumentType getInstrumentType();

    String getTradingPair();

    static Instrument createInstrument(final InstrumentType instrumentType, final String tradingPair) {
        switch (instrumentType) {
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
