package com.christopherrons.restapi.marketdata.service;

import com.christopherrons.common.enums.marketdata.MarketDataFeedEnum;
import com.christopherrons.common.enums.marketdata.SubscriptionOperation;
import com.christopherrons.common.enums.marketdata.TradingPairEnum;
import com.christopherrons.marketdataservice.MarketDataService;
import com.christopherrons.marketdataservice.common.enums.ChannelEnum;
import com.christopherrons.restapi.marketdata.dto.ApiAvailableChannelsDto;
import com.christopherrons.restapi.marketdata.dto.ApiAvailableMarketDataFeedDto;
import com.christopherrons.restapi.marketdata.dto.ApiAvailableTradingPairsDto;
import com.christopherrons.restapi.marketdata.dto.ApiSubscriptionDto;
import com.christopherrons.restapi.marketdata.model.ApiOrder;
import com.christopherrons.restapi.marketdata.model.ApiTrade;
import com.christopherrons.restapi.marketdata.requests.ApiOrderRequest;
import com.christopherrons.restapi.marketdata.requests.ApiSubscriptionRequest;
import com.christopherrons.restapi.marketdata.requests.ApiTradeRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.websocket.DeploymentException;
import java.io.IOException;

@Service
public class MarketDataApiService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MarketDataApiService.class);

    @Autowired
    private MarketDataService marketDataService;


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
        boolean isSubscribed = handleSubscription(subscriptionRequest.getMarketDataFeed(), subscriptionRequest.getTradingPair(),
                subscriptionRequest.getChannelName(), subscriptionOperation);
        return new ApiSubscriptionDto(isSubscribed);
    }

    private boolean handleSubscription(final MarketDataFeedEnum marketDataFeedEnum, final TradingPairEnum tradingPairEnum,
                                       final ChannelEnum channelEnum, final SubscriptionOperation subscriptionOperation) throws DeploymentException, IOException, InterruptedException {
        switch (subscriptionOperation) {
            case SUBSCRIBE:
                marketDataService.subscribe(marketDataFeedEnum, tradingPairEnum, channelEnum);

                int maxTime = 30;
                int interval = 2;
                int totalTime = 0;
                while (!marketDataService.isSubscribed(marketDataFeedEnum, tradingPairEnum, channelEnum) && totalTime < maxTime) {
                    Thread.sleep(interval * 1000);
                    totalTime = totalTime + interval;
                    LOGGER.info(String.format("Waited %ss for subscription to connect.", totalTime));
                }

                return marketDataService.isSubscribed(marketDataFeedEnum, tradingPairEnum, channelEnum);
            case UNSUBSCRIBE:
                marketDataService.unsubscribe(marketDataFeedEnum, tradingPairEnum, channelEnum);
                return marketDataService.isSubscribed(marketDataFeedEnum, tradingPairEnum, channelEnum);
            case IS_SUBSCRIBED:
                return marketDataService.isSubscribed(marketDataFeedEnum, tradingPairEnum, channelEnum);
            default:
                return false;
        }
    }

    public void pushOrder(final ApiOrderRequest apiOrderRequest) {
        marketDataService.pushEvent(new ApiOrder(apiOrderRequest));
    }

    public void pushTrade(final ApiTradeRequest apiTradeRequest) {
        marketDataService.pushEvent(new ApiTrade(apiTradeRequest));
    }
}
