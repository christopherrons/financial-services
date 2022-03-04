package com.christopherrons.restapi.subscription.service;

import com.christopherrons.marketdata.api.MarketDataWebsocketClient;
import com.christopherrons.marketdata.bitstamp.client.BitstampWebsocketClient;
import com.christopherrons.marketdata.common.enums.subscription.ChannelEnum;
import com.christopherrons.marketdata.common.enums.event.MargetDataEnum;
import com.christopherrons.marketdata.common.enums.subscription.SubscriptionOperation;
import com.christopherrons.marketdata.common.enums.subscription.TradingPairEnum;
import com.christopherrons.restapi.subscription.request.ApiSubscriptionRequest;
import com.christopherrons.restapi.subscription.request.utils.RequestValidatorUtil;
import com.christopherrons.restapi.subscription.response.ApiAvailableChannelsResponse;
import com.christopherrons.restapi.subscription.response.ApiAvailableExchangeResponse;
import com.christopherrons.restapi.subscription.response.ApiAvailableTradingPairsResponse;
import com.christopherrons.restapi.subscription.response.ApiSubscriptionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.websocket.DeploymentException;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

@Service
public class SubscriptionService {

    private static final Logger LOGGER = Logger.getLogger(SubscriptionService.class.getName());

    private final Map<MargetDataEnum, MarketDataWebsocketClient> exchangeToWebsocketClient = new ConcurrentHashMap<>();

    @Autowired
    public SubscriptionService(BitstampWebsocketClient bitstampWebsocketClient) {
        exchangeToWebsocketClient.putIfAbsent(MargetDataEnum.BITSTAMP, bitstampWebsocketClient);
    }

    public ApiAvailableExchangeResponse getAvailableExchanges() {
        return new ApiAvailableExchangeResponse(MargetDataEnum.getExchangeNames());
    }

    public ApiAvailableTradingPairsResponse getAvailableTradingPairs(final String exchangeName) {
        MargetDataEnum margetDataEnum = RequestValidatorUtil.extractAndValidateExchangeName(exchangeName);
        return new ApiAvailableTradingPairsResponse(TradingPairEnum.getAvailableExchangeTradingPairs(margetDataEnum));
    }

    public ApiAvailableChannelsResponse getAvailableChannels(final String exchangeName) {
        MargetDataEnum margetDataEnum = RequestValidatorUtil.extractAndValidateExchangeName(exchangeName);
        return new ApiAvailableChannelsResponse(ChannelEnum.getAvailableExchangeChannelNames(margetDataEnum));
    }

    public ApiSubscriptionResponse subscribeRequest(final ApiSubscriptionRequest subscriptionRequest) throws DeploymentException, IOException, InterruptedException {
        return subscriptionRequest(subscriptionRequest, SubscriptionOperation.SUBSCRIBE);
    }

    public ApiSubscriptionResponse unsubscribeRequest(final ApiSubscriptionRequest subscriptionRequest) throws DeploymentException, IOException, InterruptedException {
        return subscriptionRequest(subscriptionRequest, SubscriptionOperation.UNSUBSCRIBE);
    }

    public ApiSubscriptionResponse isSubscribedToChannelRequest(final ApiSubscriptionRequest subscriptionRequest) throws DeploymentException, IOException, InterruptedException {
        return subscriptionRequest(subscriptionRequest, SubscriptionOperation.IS_SUBSCRIBED);
    }

    private ApiSubscriptionResponse subscriptionRequest(final ApiSubscriptionRequest subscriptionRequest, final SubscriptionOperation subscriptionOperation) throws DeploymentException, IOException, InterruptedException {
        MargetDataEnum margetDataEnum = RequestValidatorUtil.extractAndValidateExchangeName(subscriptionRequest.getExchangeName());
        TradingPairEnum tradingPairEnum = RequestValidatorUtil.extractAndValidateTradingPair(subscriptionRequest.getTradingPair(), margetDataEnum);
        ChannelEnum channelEnum = RequestValidatorUtil.extractAndValidateChannelName(subscriptionRequest.getChannelName(), margetDataEnum);
        boolean isSubscribed = handleSubscription(exchangeToWebsocketClient.get(margetDataEnum), tradingPairEnum, channelEnum, subscriptionOperation);
        return new ApiSubscriptionResponse(isSubscribed);
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
}
