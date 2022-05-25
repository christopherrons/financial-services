package com.christopherrons.marketdata;

import com.christopherrons.marketdata.api.MarketDataEvent;
import com.christopherrons.marketdata.api.MarketDataOrder;
import com.christopherrons.marketdata.api.MarketDataWebsocketClient;
import com.christopherrons.marketdata.bitstamp.client.BitstampWebsocketClient;
import com.christopherrons.marketdata.common.EventHandler;
import com.christopherrons.marketdata.common.enums.event.MarketDataFeedEnum;
import com.christopherrons.marketdata.common.enums.subscription.ChannelEnum;
import com.christopherrons.marketdata.common.enums.subscription.TradingPairEnum;
import com.christopherrons.restapi.marketdata.model.ApiOrder;
import com.christopherrons.restapi.marketdata.model.ApiTrade;
import com.christopherrons.restapi.marketdata.requests.ApiOrderRequest;
import com.christopherrons.restapi.marketdata.requests.ApiTradeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.websocket.DeploymentException;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Logger;

@Service
public class MarketDataService {
    private static final Logger LOGGER = Logger.getLogger(MarketDataService.class.getName());
    private final Map<MarketDataFeedEnum, MarketDataWebsocketClient> dataFeedToWebsocketClient = Map.of(MarketDataFeedEnum.BITSTAMP, new BitstampWebsocketClient());

    @Autowired
    private EventHandler eventHandler;

    public void subscribe(final MarketDataFeedEnum marketDataFeedEnum,
                          final TradingPairEnum tradingPairEnum,
                          final ChannelEnum channelEnum) throws DeploymentException, IOException {
        dataFeedToWebsocketClient.get(marketDataFeedEnum)
                .subscribe(tradingPairEnum, channelEnum, eventHandler::handleEvent);
    }

    public void unsubscribe(final MarketDataFeedEnum marketDataFeedEnum,
                            final TradingPairEnum tradingPairEnum,
                            final ChannelEnum channelEnum) {
        dataFeedToWebsocketClient.get(marketDataFeedEnum).unsubscribe(tradingPairEnum, channelEnum);
    }

    public boolean isSubscribed(final MarketDataFeedEnum marketDataFeedEnum,
                                final TradingPairEnum tradingPairEnum,
                                final ChannelEnum channelEnum) {
        return dataFeedToWebsocketClient.get(marketDataFeedEnum).isSubscribed(tradingPairEnum, channelEnum);
    }

    public void pushEvent(final MarketDataEvent event) {
        eventHandler.handleEvent(event);
    }


}
