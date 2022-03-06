package com.christopherrons.marketdata.api;

import com.christopherrons.marketdata.common.enums.subscription.ChannelEnum;
import com.christopherrons.marketdata.common.enums.subscription.TradingPairEnum;

import javax.websocket.DeploymentException;
import java.io.IOException;

public interface MarketDataWebsocketClient {

    void subscribe(TradingPairEnum tradingPairEnum, ChannelEnum channelEnum) throws DeploymentException, IOException;

    void unsubscribe(TradingPairEnum tradingPairEnum, ChannelEnum channelEnum);

    boolean isSubscribed(TradingPairEnum tradingPairEnum, ChannelEnum channelEnum);

}
