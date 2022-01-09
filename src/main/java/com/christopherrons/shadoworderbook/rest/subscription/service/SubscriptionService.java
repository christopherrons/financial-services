package com.christopherrons.shadoworderbook.rest.subscription.service;

import com.christopherrons.shadoworderbook.exchange.api.ExchangeWebsocketClient;
import com.christopherrons.shadoworderbook.exchange.bitstamp.client.BitstampWebsocketClient;
import com.christopherrons.shadoworderbook.exchange.common.enums.subscription.ChannelEnum;
import com.christopherrons.shadoworderbook.exchange.common.enums.event.ExchangeEnum;
import com.christopherrons.shadoworderbook.exchange.common.enums.subscription.SubscriptionOperation;
import com.christopherrons.shadoworderbook.exchange.common.enums.subscription.TradingPairEnum;
import com.christopherrons.shadoworderbook.rest.subscription.request.ApiSubscriptionRequest;
import com.christopherrons.shadoworderbook.rest.subscription.request.utils.RequestValidatorUtil;
import com.christopherrons.shadoworderbook.rest.subscription.response.ApiAvailableChannelsResponse;
import com.christopherrons.shadoworderbook.rest.subscription.response.ApiAvailableExchangeResponse;
import com.christopherrons.shadoworderbook.rest.subscription.response.ApiAvailableTradingPairsResponse;
import com.christopherrons.shadoworderbook.rest.subscription.response.ApiSubscriptionResponse;
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

    private final Map<ExchangeEnum, ExchangeWebsocketClient> exchangeToWebsocketClient = new ConcurrentHashMap<>();

    @Autowired
    public SubscriptionService(BitstampWebsocketClient bitstampWebsocketClient) {
        exchangeToWebsocketClient.putIfAbsent(ExchangeEnum.BITSTAMP, bitstampWebsocketClient);
    }

    public ApiAvailableExchangeResponse getAvailableExchanges() {
        return new ApiAvailableExchangeResponse(ExchangeEnum.getExchangeNames());
    }

    public ApiAvailableTradingPairsResponse getAvailableTradingPairs(final String exchangeName) {
        ExchangeEnum exchangeEnum = RequestValidatorUtil.extractAndValidateExchangeName(exchangeName);
        return new ApiAvailableTradingPairsResponse(TradingPairEnum.getAvailableExchangeTradingPairs(exchangeEnum));
    }

    public ApiAvailableChannelsResponse getAvailableChannels(final String exchangeName) {
        ExchangeEnum exchangeEnum = RequestValidatorUtil.extractAndValidateExchangeName(exchangeName);
        return new ApiAvailableChannelsResponse(ChannelEnum.getAvailableExchangeChannelNames(exchangeEnum));
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
        ExchangeEnum exchangeEnum = RequestValidatorUtil.extractAndValidateExchangeName(subscriptionRequest.getExchangeName());
        TradingPairEnum tradingPairEnum = RequestValidatorUtil.extractAndValidateTradingPair(subscriptionRequest.getTradingPair(), exchangeEnum);
        ChannelEnum channelEnum = RequestValidatorUtil.extractAndValidateChannelName(subscriptionRequest.getChannelName(), exchangeEnum);
        boolean isSubscribed = handleSubscription(exchangeToWebsocketClient.get(exchangeEnum), tradingPairEnum, channelEnum, subscriptionOperation);
        return new ApiSubscriptionResponse(isSubscribed);
    }

    private boolean handleSubscription(final ExchangeWebsocketClient websocketClient, final TradingPairEnum tradingPairEnum,
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
