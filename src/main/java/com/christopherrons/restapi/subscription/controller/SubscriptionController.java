package com.christopherrons.restapi.subscription.controller;

import com.christopherrons.restapi.subscription.request.ApiAvailableChannelRequest;
import com.christopherrons.restapi.subscription.request.ApiAvailableTradingPairsRequest;
import com.christopherrons.restapi.subscription.request.ApiSubscriptionRequest;
import com.christopherrons.restapi.subscription.response.ApiAvailableChannelsResponse;
import com.christopherrons.restapi.subscription.response.ApiAvailableExchangeResponse;
import com.christopherrons.restapi.subscription.response.ApiAvailableTradingPairsResponse;
import com.christopherrons.restapi.subscription.response.ApiSubscriptionResponse;
import com.christopherrons.restapi.subscription.service.SubscriptionService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.DeploymentException;
import java.io.IOException;
import java.util.logging.Logger;

@RestController
public class SubscriptionController {

    private static final Logger LOGGER = Logger.getLogger(SubscriptionController.class.getName());

    @Autowired
    private SubscriptionService subscriptionService;

    @GetMapping("/availableExchanges")
    @ApiOperation(value = "Request available exchanges.",
            notes = "This method returns all available exchanges.")
    public ApiAvailableExchangeResponse subscriptionExchangeRequestGet() {
        LOGGER.info("Get request Available Exchanges received.");
        return subscriptionService.getAvailableExchanges();
    }


    @PostMapping(value = "/availableTradingPairs")
    @ApiOperation(value = "Request available trading pairs for a specific exchange.",
            notes = "This methods returns all available trading pairs for the specified exchange.")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Bad Request"),
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "exchangeName", dataTypeClass = String.class, value = "The name of the exchange", example = "bitstamp", defaultValue = "Bitstamp", required = true),
    })
    public ApiAvailableTradingPairsResponse subscriptionTradingPairsRequestPost(final ApiAvailableTradingPairsRequest availableTradingPairsRequest) {
        LOGGER.info(String.format("Post request Available Trading Pairs received: %s.", availableTradingPairsRequest));
        return subscriptionService.getAvailableTradingPairs(availableTradingPairsRequest.getExchangeName());
    }

    @PostMapping(value = "/availableChannels")
    @ApiOperation(value = "Request available channels for a specific exchange.",
            notes = "This methods returns all available channels for the specified exchange.")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Bad Request"),
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "exchangeName", dataTypeClass = String.class, value = "The name of the exchange", example = "bitstamp", defaultValue = "Bitstamp", required = true),
    })
    public ApiAvailableChannelsResponse subscriptionTradingPairsRequestPost(final ApiAvailableChannelRequest apiAvailableChannelRequest) {
        LOGGER.info(String.format("Post request Available Trading Pairs received: %s.", apiAvailableChannelRequest));
        return subscriptionService.getAvailableChannels(apiAvailableChannelRequest.getExchangeName());
    }

    @PostMapping(value = "/subscribeToChannel")
    @ApiOperation(value = "Subscribe to channel.",
            notes = "This methods subscribes to a exchange trading pair channel.")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Bad Request"),
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "exchangeName", value = "The name of the exchange", example = "bitstamp", defaultValue = "bitstamp", required = true, dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "tradingPair", value = "The name of trading pair", example = "xrpusd", defaultValue = "xrpusd", required = true, dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "channelName", value = "The name of channel", example = "live_orders", defaultValue = "live_orders", required = true, dataTypeClass = String.class, paramType = "query")
    })
    public ApiSubscriptionResponse subscribeToChannelRequestPost(final ApiSubscriptionRequest subscriptionRequest) throws DeploymentException, IOException, InterruptedException {
        LOGGER.info(String.format("Post request Subscribe To Channel received: %s.", subscriptionRequest.toString()));
        return subscriptionService.subscribeRequest(subscriptionRequest);
    }

    @PostMapping(value = "/unsubscribeToChannel")
    @ApiOperation(value = "Unsubscribe to channel.",
            notes = "This methods unsubscribes to a exchange trading pair channel.")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Bad Request"),
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "exchangeName", value = "The name of the exchange", example = "bitstamp", required = true, dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "tradingPair", value = "The name of trading pair", example = "xrpusd", required = true, dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "channelName", value = "The name of channel", example = "live_orders", defaultValue = "live_orders", required = true, dataTypeClass = String.class, paramType = "query")
    })
    public ApiSubscriptionResponse unsubscribeToChannelRequestPost(final ApiSubscriptionRequest subscriptionRequest) throws DeploymentException, IOException, InterruptedException {
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
            @ApiImplicitParam(name = "exchangeName", value = "The name of the exchange", example = "bitstamp", required = true, dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "tradingPair", value = "The name of trading pair", example = "xrpusd", required = true, dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "channelName", value = "The name of channel", example = "live_orders", defaultValue = "live_orders", required = true, dataTypeClass = String.class, paramType = "query")
    })
    public ApiSubscriptionResponse isSubscribedPost(final ApiSubscriptionRequest subscriptionRequest) throws DeploymentException, IOException, InterruptedException {
        LOGGER.info(String.format("Post request is subscribed: %s.", subscriptionRequest.toString()));
        return subscriptionService.isSubscribedToChannelRequest(subscriptionRequest);
    }
}
