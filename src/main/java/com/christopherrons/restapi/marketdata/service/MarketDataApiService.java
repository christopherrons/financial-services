package com.christopherrons.restapi.marketdata.service;

import com.christopherrons.marketdata.MarketDataService;
import com.christopherrons.marketdata.api.MarketDataWebsocketClient;
import com.christopherrons.marketdata.bitstamp.client.BitstampWebsocketClient;
import com.christopherrons.marketdata.common.enums.event.MarketDataFeedEnum;
import com.christopherrons.marketdata.common.enums.subscription.ChannelEnum;
import com.christopherrons.marketdata.common.enums.subscription.SubscriptionOperation;
import com.christopherrons.marketdata.common.enums.subscription.TradingPairEnum;
import com.christopherrons.marketdata.common.model.ApiOrder;
import com.christopherrons.marketdata.common.model.ApiTrade;
import com.christopherrons.restapi.marketdata.dto.ApiAvailableChannelsDto;
import com.christopherrons.restapi.marketdata.dto.ApiAvailableMarketDataFeedDto;
import com.christopherrons.restapi.marketdata.dto.ApiAvailableTradingPairsDto;
import com.christopherrons.restapi.marketdata.dto.ApiSubscriptionDto;
import com.christopherrons.restapi.marketdata.requests.ApiOrderRequest;
import com.christopherrons.restapi.marketdata.requests.ApiSubscriptionRequest;
import com.christopherrons.restapi.marketdata.requests.ApiTradeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.websocket.DeploymentException;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

@Service
public class MarketDataApiService {

    private static final Logger LOGGER = Logger.getLogger(MarketDataApiService.class.getName());

    private final Map<MarketDataFeedEnum, MarketDataWebsocketClient> dataFeedToWebsocketClient = new ConcurrentHashMap<>();

    @Autowired
    private MarketDataService marketDataService;


    @Autowired
    public MarketDataApiService(BitstampWebsocketClient bitstampWebsocketClient) {
        dataFeedToWebsocketClient.putIfAbsent(MarketDataFeedEnum.BITSTAMP, bitstampWebsocketClient);
    }

    public ApiAvailableMarketDataFeedDto getAvailableMarketDataFeed() {
        return new ApiAvailableMarketDataFeedDto(MarketDataFeedEnum.getDataFeedNames());
    }

    public ApiAvailableTradingPairsDto getAvailableTradingPairs(final MarketDataFeedEnum marketDataFeedEnum) {
        return new ApiAvailableTradingPairsDto(TradingPairEnum.getAvailableTradingPairsByDataFeed(marketDataFeedEnum));
    }

    public ApiAvailableChannelsDto getAvailableChannels(final MarketDataFeedEnum marketDataFeedEnum) {
        return new ApiAvailableChannelsDto(ChannelEnum.getAvailableChannelNamesByDataFeed(marketDataFeedEnum));
    }

    public ApiSubscriptionDto subscribeRequest(final ApiSubscriptionRequest subscriptionRequest) throws DeploymentException, IOException, InterruptedException {
        return subscriptionRequest(subscriptionRequest, SubscriptionOperation.SUBSCRIBE);
    }

    public ApiSubscriptionDto unsubscribeRequest(final ApiSubscriptionRequest subscriptionRequest) throws DeploymentException, IOException, InterruptedException {
        return subscriptionRequest(subscriptionRequest, SubscriptionOperation.UNSUBSCRIBE);
    }

    public ApiSubscriptionDto isSubscribedToChannelRequest(final ApiSubscriptionRequest subscriptionRequest) throws DeploymentException, IOException, InterruptedException {
        return subscriptionRequest(subscriptionRequest, SubscriptionOperation.IS_SUBSCRIBED);
    }

    private ApiSubscriptionDto subscriptionRequest(final ApiSubscriptionRequest subscriptionRequest, final SubscriptionOperation subscriptionOperation) throws DeploymentException, IOException, InterruptedException {
        boolean isSubscribed = handleSubscription(dataFeedToWebsocketClient.get(subscriptionRequest.getDataFeedName()),
                subscriptionRequest.getTradingPair(), subscriptionRequest.getChannelName(), subscriptionOperation);
        return new ApiSubscriptionDto(isSubscribed);
    }

    private boolean handleSubscription(final MarketDataWebsocketClient websocketClient, final TradingPairEnum tradingPairEnum,
                                       final ChannelEnum channelEnum, final SubscriptionOperation subscriptionOperation) throws DeploymentException, IOException, InterruptedException {
        switch (subscriptionOperation) {
            case SUBSCRIBE:
                websocketClient.subscribe(tradingPairEnum, channelEnum);

                int maxTime = 30;
                int interval = 2;
                int totalTime = 0;
                while (!websocketClient.isSubscribed(tradingPairEnum, channelEnum) && totalTime < maxTime) {
                    Thread.sleep(interval * 1000);
                    totalTime = totalTime + interval;
                    LOGGER.info(String.format("Waited %ss for subscription to connect.", totalTime));
                }

                return websocketClient.isSubscribed(tradingPairEnum, channelEnum);
            case UNSUBSCRIBE:
                websocketClient.unsubscribe(tradingPairEnum, channelEnum);
                return websocketClient.isSubscribed(tradingPairEnum, channelEnum);
            case IS_SUBSCRIBED:
                return websocketClient.isSubscribed(tradingPairEnum, channelEnum);
            default:
                return false;
        }
    }

    public void pushOrder(final ApiOrderRequest apiOrderRequest) {
        marketDataService.handleEvent(new ApiOrder(apiOrderRequest));
    }

    public void pushTrade(final ApiTradeRequest apiTradeRequest) {
        marketDataService.handleEvent(new ApiTrade(apiTradeRequest));
    }
}
