package com.christopherrons.common.broadcasts;

import com.christopherrons.marketdata.api.MarketDataTrade;
import org.springframework.context.ApplicationEvent;

import java.util.List;

public class TradeEventBroadcast extends ApplicationEvent {

    private List<MarketDataTrade> trades;

    public TradeEventBroadcast(Object source, List<MarketDataTrade> trades) {
        super(source);
        this.trades = trades;
    }

    public List<MarketDataTrade> getTrades() {
        return trades;
    }
}
