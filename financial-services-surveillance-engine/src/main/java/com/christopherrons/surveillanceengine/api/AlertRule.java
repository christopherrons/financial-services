package com.christopherrons.surveillanceengine.api;

import com.christopherrons.common.api.marketdata.MarketDataOrder;
import com.christopherrons.common.api.marketdata.MarketDataTrade;
import com.christopherrons.common.model.surveillance.Alert;

public interface AlertRule {

    Alert investigate(MarketDataOrder order);

    Alert investigate(MarketDataTrade trade);

}
