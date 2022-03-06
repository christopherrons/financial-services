package com.christopherrons.restapi.marketdata.controller;

import com.christopherrons.restapi.marketdata.MarketDataApiService;
import com.christopherrons.restapi.marketdata.dto.ApiAvailableChannelsDto;
import com.christopherrons.restapi.marketdata.dto.ApiAvailableMarketDataFeedDto;
import com.christopherrons.restapi.marketdata.dto.ApiAvailableTradingPairsDto;
import com.christopherrons.restapi.marketdata.dto.ApiSubscriptionDto;
import com.christopherrons.restapi.marketdata.requests.ApiAvailableChannelRequest;
import com.christopherrons.restapi.marketdata.requests.ApiAvailableTradingPairsRequest;
import com.christopherrons.restapi.marketdata.requests.ApiSubscriptionRequest;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.DeploymentException;
import java.io.IOException;
import java.util.logging.Logger;

@RestController
public class MarketDataApiController {

    private static final Logger LOGGER = Logger.getLogger(MarketDataApiController.class.getName());

    @Autowired
    private MarketDataApiService subscriptionService;

    @GetMapping("/availableDataFeeds")
    @ApiOperation(value = "Request available data feeds.",
            notes = "This method returns all available data fees.")
    public ApiAvailableMarketDataFeedDto getAvailableDataFeeds() {
        LOGGER.info("Get request available data feeds received.");
        return subscriptionService.getAvailableMarketDataFeed();
    }


    @PostMapping(value = "/availableTradingPairs")
    @ApiOperation(value = "Request available trading pairs by market data feed.",
            notes = "This methods returns all available trading pairs by market data feed")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Bad Request"),
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dataFeedName", dataTypeClass = String.class, value = "The name of the data feed", example = "bitstamp", defaultValue = "Bitstamp", required = true),
    })
    public ApiAvailableTradingPairsDto subscriptionTradingPairsRequestPost(final ApiAvailableTradingPairsRequest availableTradingPairsRequest) {
        LOGGER.info(String.format("Post request Available Trading Pairs received: %s.", availableTradingPairsRequest));
        return subscriptionService.getAvailableTradingPairs(availableTradingPairsRequest.getDataFeedName());
    }

    @PostMapping(value = "/availableChannels")
    @ApiOperation(value = "Request available channels for a by market data feed",
            notes = "This methods returns all available channels by market data feed.")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Bad Request"),
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dataFeedName", dataTypeClass = String.class, value = "The name of the data feed", example = "bitstamp", defaultValue = "Bitstamp", required = true),
    })
    public ApiAvailableChannelsDto postSubscriptionTradingPairsRequest(final ApiAvailableChannelRequest apiAvailableChannelRequest) {
        LOGGER.info(String.format("Post request Available Trading Pairs received: %s.", apiAvailableChannelRequest));
        return subscriptionService.getAvailableChannels(apiAvailableChannelRequest.getDataFeedName());
    }

    @PostMapping(value = "/subscribeToChannel")
    @ApiOperation(value = "Subscribe to channel.",
            notes = "This methods subscribes to a trading pair channel available on the market data feed.")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Bad Request"),
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dataFeedName", value = "The name of the data feed", example = "bitstamp", defaultValue = "bitstamp", required = true, dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "tradingPair", value = "The name of trading pair", example = "xrpusd", defaultValue = "xrpusd", required = true, dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "channelName", value = "The name of channel", example = "live_orders", defaultValue = "live_orders", required = true, dataTypeClass = String.class, paramType = "query")
    })
    public ApiSubscriptionDto postSubscribeToChannelRequest(final ApiSubscriptionRequest subscriptionRequest) throws DeploymentException, IOException, InterruptedException {
        LOGGER.info(String.format("Post request Subscribe To Channel received: %s.", subscriptionRequest.toString()));
        return subscriptionService.subscribeRequest(subscriptionRequest);
    }

    @PostMapping(value = "/unsubscribeToChannel")
    @ApiOperation(value = "Unsubscribe to channel.",
            notes = "This methods unsubscribes to a trading pair channel available on the market data feed.")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Bad Request"),
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dataFeedName", value = "The name of the data feed", example = "bitstamp", required = true, dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "tradingPair", value = "The name of trading pair", example = "xrpusd", required = true, dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "channelName", value = "The name of channel", example = "live_orders", defaultValue = "live_orders", required = true, dataTypeClass = String.class, paramType = "query")
    })
    public ApiSubscriptionDto postUnsubscribeToChannelRequest(final ApiSubscriptionRequest subscriptionRequest) throws DeploymentException, IOException, InterruptedException {
        LOGGER.info(String.format("Post request Unsubscribe To Channel received: %s.", subscriptionRequest.toString()));
        return subscriptionService.unsubscribeRequest(subscriptionRequest);
    }

    @PostMapping(value = "/isSubscribedToChannel")
    @ApiOperation(value = "Check if subscribes to channel.",
            notes = "This methods checks if subscribed to a channel")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Bad Request"),
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dataFeedName", value = "The name of the data ffed", example = "bitstamp", required = true, dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "tradingPair", value = "The name of trading pair", example = "xrpusd", required = true, dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "channelName", value = "The name of channel", example = "live_orders", defaultValue = "live_orders", required = true, dataTypeClass = String.class, paramType = "query")
    })
    public ApiSubscriptionDto postIsSubscribedRequest(final ApiSubscriptionRequest subscriptionRequest) throws DeploymentException, IOException, InterruptedException {
        LOGGER.info(String.format("Post request is subscribed: %s.", subscriptionRequest.toString()));
        return subscriptionService.isSubscribedToChannelRequest(subscriptionRequest);
    }
}
