package com.christopherrons.restapi.marketdata.controller;

import com.christopherrons.marketdata.common.enums.event.EventDescriptionEnum;
import com.christopherrons.marketdata.common.enums.event.MarketDataFeedEnum;
import com.christopherrons.marketdata.common.enums.event.OrderOperationEnum;
import com.christopherrons.marketdata.common.enums.event.OrderTypeEnum;
import com.christopherrons.marketdata.common.enums.subscription.ChannelEnum;
import com.christopherrons.marketdata.common.enums.subscription.TradingPairEnum;
import com.christopherrons.refdata.instrument.enums.InstrumentTypeEnum;
import com.christopherrons.restapi.marketdata.dto.ApiAvailableChannelsDto;
import com.christopherrons.restapi.marketdata.dto.ApiAvailableMarketDataFeedDto;
import com.christopherrons.restapi.marketdata.dto.ApiAvailableTradingPairsDto;
import com.christopherrons.restapi.marketdata.dto.ApiSubscriptionDto;
import com.christopherrons.restapi.marketdata.requests.*;
import com.christopherrons.restapi.marketdata.service.MarketDataApiService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.DeploymentException;
import java.io.IOException;
import java.util.logging.Logger;

@RestController()
@CrossOrigin("*")
@RequestMapping("/marketData")
public class MarketDataApiController {

    private static final Logger LOGGER = Logger.getLogger(MarketDataApiController.class.getName());

    @Autowired
    private MarketDataApiService marketDataApiService;

    @GetMapping("/availableDataFeeds")
    @ApiOperation(value = "Request available data feeds.",
            notes = "This method returns all available data fees.")
    public ApiAvailableMarketDataFeedDto getAvailableDataFeeds() {
        LOGGER.info("Get request available data feeds received.");
        return marketDataApiService.getAvailableMarketDataFeed();
    }


    @PostMapping(value = "/availableTradingPairs")
    @ApiOperation(value = "Request available trading pairs by market data feed.",
            notes = "This methods returns all available trading pairs by market data feed")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Bad Request"),
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dataFeedName", defaultValue = "BITSTAMP", required = true, dataTypeClass = MarketDataFeedEnum.class, paramType = "query"),
    })
    public ApiAvailableTradingPairsDto subscriptionTradingPairsRequestPost(final ApiAvailableTradingPairsRequest availableTradingPairsRequest) {
        LOGGER.info(String.format("Post request Available Trading Pairs received: %s.", availableTradingPairsRequest));
        return marketDataApiService.getAvailableTradingPairs(availableTradingPairsRequest.getDataFeedName());
    }

    @PostMapping(value = "/availableChannels")
    @ApiOperation(value = "Request available channels for a by market data feed",
            notes = "This methods returns all available channels by market data feed.")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Bad Request"),
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dataFeedName", defaultValue = "BITSTAMP", required = true, dataTypeClass = MarketDataFeedEnum.class, paramType = "query"),
    })
    public ApiAvailableChannelsDto postSubscriptionTradingPairsRequest(final ApiAvailableChannelRequest apiAvailableChannelRequest) {
        LOGGER.info(String.format("Post request Available Trading Pairs received: %s.", apiAvailableChannelRequest));
        return marketDataApiService.getAvailableChannels(apiAvailableChannelRequest.getDataFeedName());
    }

    @PostMapping(value = "/subscribeToChannel")
    @ApiOperation(value = "Subscribe to channel.",
            notes = "This methods subscribes to a trading pair channel available on the market data feed.")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Bad Request"),
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dataFeedName", defaultValue = "BITSTAMP", required = true, dataTypeClass = MarketDataFeedEnum.class, paramType = "query"),
            @ApiImplicitParam(name = "tradingPair", defaultValue = "BTC_USD", required = true, dataTypeClass = TradingPairEnum.class, paramType = "query"),
            @ApiImplicitParam(name = "channelName", defaultValue = "LIVE_ORDERS", required = true, dataTypeClass = ChannelEnum.class, paramType = "query")
    })
    public ApiSubscriptionDto postSubscribeToChannelRequest(final ApiSubscriptionRequest subscriptionRequest) throws DeploymentException, IOException, InterruptedException {
        LOGGER.info(String.format("Post request Subscribe To Channel received: %s.", subscriptionRequest.toString()));
        return marketDataApiService.subscribeRequest(subscriptionRequest);
    }

    @PostMapping(value = "/unsubscribeToChannel")
    @ApiOperation(value = "Unsubscribe to channel.",
            notes = "This methods unsubscribes to a trading pair channel available on the market data feed.")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Bad Request"),
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dataFeedName", defaultValue = "BITSTAMP", required = true, dataTypeClass = MarketDataFeedEnum.class, paramType = "query"),
            @ApiImplicitParam(name = "tradingPair", defaultValue = "BTC_USD", required = true, dataTypeClass = TradingPairEnum.class, paramType = "query"),
            @ApiImplicitParam(name = "channelName", defaultValue = "LIVE_ORDERS", required = true, dataTypeClass = ChannelEnum.class, paramType = "query")
    })
    public ApiSubscriptionDto postUnsubscribeToChannelRequest(final ApiSubscriptionRequest subscriptionRequest) throws DeploymentException, IOException, InterruptedException {
        LOGGER.info(String.format("Post request Unsubscribe To Channel received: %s.", subscriptionRequest.toString()));
        return marketDataApiService.unsubscribeRequest(subscriptionRequest);
    }

    @PostMapping(value = "/isSubscribedToChannel")
    @ApiOperation(value = "Check if subscribes to channel.",
            notes = "This methods checks if subscribed to a channel")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Bad Request"),
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dataFeedName", defaultValue = "BITSTAMP", required = true, dataTypeClass = MarketDataFeedEnum.class, paramType = "query"),
            @ApiImplicitParam(name = "tradingPair", defaultValue = "BTC_USD", required = true, dataTypeClass = TradingPairEnum.class, paramType = "query"),
            @ApiImplicitParam(name = "channelName", defaultValue = "LIVE_ORDERS", required = true, dataTypeClass = ChannelEnum.class, paramType = "query")
    })
    public ApiSubscriptionDto postIsSubscribedRequest(final ApiSubscriptionRequest subscriptionRequest) throws DeploymentException, IOException, InterruptedException {
        LOGGER.info(String.format("Post request is subscribed: %s.", subscriptionRequest.toString()));
        return marketDataApiService.isSubscribedToChannelRequest(subscriptionRequest);
    }


    @PostMapping(value = "/createOrder")
    @ApiOperation(value = "Send basic order.",
            notes = "This methods sends a order into the system.")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Bad Request"),
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "price", value = "Price of the order", example = "100.05", required = true, dataTypeClass = Double.class, paramType = "query"),
            @ApiImplicitParam(name = "volume", value = "Volume of the order", example = "99.1", required = true, dataTypeClass = Double.class, paramType = "query"),
            @ApiImplicitParam(name = "orderType", defaultValue = "BUY", required = true, dataTypeClass = OrderTypeEnum.class, paramType = "query"),
            @ApiImplicitParam(name = "orderOperation", defaultValue = "CREATE", required = true, dataTypeClass = OrderOperationEnum.class, paramType = "query"),
            @ApiImplicitParam(name = "marketDataFeed", defaultValue = "BITSTAMP", required = true, dataTypeClass = MarketDataFeedEnum.class, paramType = "query"),
            @ApiImplicitParam(name = "tradingPair", defaultValue = "BTC_USD", required = true, dataTypeClass = TradingPairEnum.class, paramType = "query"),
            @ApiImplicitParam(name = "channel", defaultValue = "LIVE_ORDERS", required = true, dataTypeClass = ChannelEnum.class, paramType = "query"),
            @ApiImplicitParam(name = "eventDescription", defaultValue = "ORDER_CREATED", required = true, dataTypeClass = EventDescriptionEnum.class, paramType = "query"),
            @ApiImplicitParam(name = "instrumentType", defaultValue = "STOCK", required = true, dataTypeClass = InstrumentTypeEnum.class, paramType = "query")
    })
    public ApiSubscriptionDto postOrder(final ApiOrderRequest orderRequest) {
        LOGGER.info(String.format("Post request order : %s.", orderRequest.toString()));
        marketDataApiService.pushOrder(orderRequest);
        return null;
    }

    @PostMapping(value = "/createTrade")
    @ApiOperation(value = "Send basic trade.",
            notes = "This methods sends a trade into the system.")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Bad Request"),
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "price", value = "Price of the order", example = "100.05", required = true, dataTypeClass = Double.class, paramType = "query"),
            @ApiImplicitParam(name = "volume", value = "Volume of the order", example = "99.1", required = true, dataTypeClass = Double.class, paramType = "query"),
            @ApiImplicitParam(name = "bidSideAggressor", defaultValue = "true", required = true, dataTypeClass = Boolean.class, paramType = "query"),
            @ApiImplicitParam(name = "marketDataFeed", defaultValue = "BITSTAMP", required = true, dataTypeClass = MarketDataFeedEnum.class, paramType = "query"),
            @ApiImplicitParam(name = "tradingPair", defaultValue = "BTC_USD", required = true, dataTypeClass = TradingPairEnum.class, paramType = "query"),
            @ApiImplicitParam(name = "channel", defaultValue = "LIVE_ORDERS", required = true, dataTypeClass = ChannelEnum.class, paramType = "query"),
            @ApiImplicitParam(name = "eventDescription", defaultValue = "TRADE", required = true, dataTypeClass = EventDescriptionEnum.class, paramType = "query"),
            @ApiImplicitParam(name = "instrumentType", defaultValue = "STOCK", required = true, dataTypeClass = InstrumentTypeEnum.class, paramType = "query")
    })
    public ApiSubscriptionDto postTrade(final ApiTradeRequest tradeRequest) {
        LOGGER.info(String.format("Post request order : %s.", tradeRequest.toString()));
        marketDataApiService.pushTrade(tradeRequest);
        return null;
    }
}
