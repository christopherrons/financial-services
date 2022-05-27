package com.christopherrons.surveillanceengine.api;

import com.christopherrons.marketdata.api.MarketDataOrder;
import com.christopherrons.marketdata.api.MarketDataTrade;
import com.christopherrons.surveillanceengine.alert.model.Alert;

public interface AlertRule {

    Alert investigate(MarketDataOrder order);

    Alert investigate(MarketDataTrade trade);

}
