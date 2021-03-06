package com.christopherrons.common.broadcasts;

import com.christopherrons.common.api.marketdata.MarketDataTrade;
import org.springframework.context.ApplicationEvent;

import java.util.List;

public class TradeEventBroadcast extends ApplicationEvent {

    //TODO: Are the trades in the correct order timewise, might need to be sorted
    private final transient List<MarketDataTrade> trades;

    public TradeEventBroadcast(Object source, List<MarketDataTrade> trades) {
        super(source);
        this.trades = trades;
    }

    public List<MarketDataTrade> getTrades() {
        return trades;
    }
}
