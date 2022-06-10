package com.christopherrons.marketdataengine.api;

import com.christopherrons.common.api.marketdata.MarketDataEvent;
import com.christopherrons.marketdataengine.common.enums.ChannelEnum;
import com.christopherrons.common.enums.marketdata.TradingPairEnum;

import javax.websocket.DeploymentException;
import java.io.IOException;
import java.util.function.Consumer;

public interface MarketDataWebsocketClient {

    void subscribe(TradingPairEnum tradingPairEnum, ChannelEnum channelEnum, Consumer<MarketDataEvent> eventHandler) throws DeploymentException, IOException;

    void unsubscribe(TradingPairEnum tradingPairEnum, ChannelEnum channelEnum);

    boolean isSubscribed(TradingPairEnum tradingPairEnum, ChannelEnum channelEnum);

}
