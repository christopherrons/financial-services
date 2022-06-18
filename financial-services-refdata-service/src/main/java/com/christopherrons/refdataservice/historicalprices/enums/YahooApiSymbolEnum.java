package com.christopherrons.refdataservice.historicalprices.enums;

import com.christopherrons.common.api.refdata.Instrument;
import com.christopherrons.common.enums.marketdata.TradingPairEnum;
import com.christopherrons.common.enums.refdata.InstrumentTypeEnum;

import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toMap;

public enum YahooApiSymbolEnum {
    BTC_USD("BTC-USD", Instrument.createInstrument(InstrumentTypeEnum.STOCK, TradingPairEnum.BTC_USD).getInstrumentId()),
    XRP_USD("XRP-USD", Instrument.createInstrument(InstrumentTypeEnum.STOCK, TradingPairEnum.XRP_USD).getInstrumentId());

    private final String apiSymbolName;
    private final String instrumentId;

    private static final Map<String, String> VALUES_BY_IDENTIFIER =
            stream(YahooApiSymbolEnum.values()).collect(toMap(YahooApiSymbolEnum::getName, YahooApiSymbolEnum::getInstrumentId));

    public static final String API_SYMBOLS =
            stream(YahooApiSymbolEnum.values()).map(YahooApiSymbolEnum::getName).collect(Collectors.joining(","));

    YahooApiSymbolEnum(String apiSymbolName, String instrumentId) {
        this.apiSymbolName = apiSymbolName;
        this.instrumentId = instrumentId;
    }

    public String getName() {
        return apiSymbolName;
    }

    public String getInstrumentId() {
        return instrumentId;
    }

    public static String apiSymbolToInstrumendId(String symbol) {
        return VALUES_BY_IDENTIFIER.get(symbol);
    }
}