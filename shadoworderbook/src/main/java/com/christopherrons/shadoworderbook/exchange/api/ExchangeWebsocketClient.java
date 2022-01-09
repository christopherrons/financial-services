package com.christopherrons.shadoworderbook.exchange.api;

import com.christopherrons.shadoworderbook.exchange.common.enums.subscription.ChannelEnum;
import com.christopherrons.shadoworderbook.exchange.common.enums.subscription.TradingPairEnum;

import javax.websocket.DeploymentException;
import java.io.IOException;

public interface ExchangeWebsocketClient {

    void subscribe(TradingPairEnum tradingPairEnum, ChannelEnum channelEnum) throws DeploymentException, IOException;
    void unsubscribe(TradingPairEnum tradingPairEnum, ChannelEnum channelEnum);
    boolean isSubscribed(TradingPairEnum tradingPairEnum, ChannelEnum channelEnum);

}
