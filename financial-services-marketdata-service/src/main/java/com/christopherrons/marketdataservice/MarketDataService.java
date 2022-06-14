package com.christopherrons.marketdataservice;

import com.christopherrons.common.api.marketdata.MarketDataEvent;
import com.christopherrons.marketdataservice.api.MarketDataWebsocketClient;
import com.christopherrons.marketdataservice.bitstamp.client.BitstampWebsocketClient;
import com.christopherrons.marketdataservice.common.EventHandler;
import com.christopherrons.common.enums.marketdata.MarketDataFeedEnum;
import com.christopherrons.marketdataservice.common.enums.ChannelEnum;
import com.christopherrons.common.enums.marketdata.TradingPairEnum;
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
