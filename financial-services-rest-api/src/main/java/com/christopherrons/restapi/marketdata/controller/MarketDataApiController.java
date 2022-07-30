package com.christopherrons.restapi.marketdata.controller;

import com.christopherrons.common.enums.marketdata.*;
import com.christopherrons.common.enums.refdata.InstrumentTypeEnum;
import com.christopherrons.marketdataservice.common.enums.ChannelEnum;
import com.christopherrons.restapi.marketdata.dto.ApiAvailableChannelsDto;
import com.christopherrons.restapi.marketdata.dto.ApiAvailableMarketDataFeedDto;
import com.christopherrons.restapi.marketdata.dto.ApiAvailableTradingPairsDto;
import com.christopherrons.restapi.marketdata.dto.ApiSubscriptionDto;
import com.christopherrons.restapi.marketdata.requests.*;
import com.christopherrons.restapi.marketdata.service.MarketDataApiService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.DeploymentException;
import java.io.IOException;

@RestController()
@CrossOrigin("*")
@RequestMapping("/marketData")
public class MarketDataApiController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MarketDataApiController.class);

    @Autowired
    private MarketDataApiService marketDataApiService;

    @GetMapping("/availableDataFeeds")
    @Operation(summary = "Request available data feeds.",
            description = "This method returns all available data fees.")
    public ApiAvailableMarketDataFeedDto getAvailableDataFeeds() {
        LOGGER.info("Get request available data feeds received.");
        return marketDataApiService.getAvailableMarketDataFeed();
    }

    @PostMapping(value = "/availableTradingPairs")
    @Operation(summary = "Request available trading pairs by market data feed.",
            description = "This methods returns all available trading pairs by market data feed")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Bad Request"),
    })
    @Parameters({
            @Parameter(name = "marketDataFeed", description = "market data feed", required = true, in = ParameterIn.QUERY,
                    content = @Content(schema = @Schema(implementation = MarketDataFeedEnum.class, defaultValue = "BITSTAMP"))),
    })
    public ApiAvailableTradingPairsDto subscriptionTradingPairsRequestPost(final ApiAvailableTradingPairsRequest availableTradingPairsRequest) {
        LOGGER.info(String.format("Post request Available Trading Pairs received: %s.", availableTradingPairsRequest));
        return marketDataApiService.getAvailableTradingPairs(availableTradingPairsRequest.getDataFeedName());
    }

    @PostMapping(value = "/availableChannels")
    @Operation(summary = "Request available channels for a by market data feed",
            description = "This methods returns all available channels by market data feed.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Bad Request"),
    })
    @Parameters({
            @Parameter(name = "marketDataFeed", description = "market data feed", required = true, in = ParameterIn.QUERY,
                    content = @Content(schema = @Schema(implementation = MarketDataFeedEnum.class, defaultValue = "BITSTAMP"))),
    })
    public ApiAvailableChannelsDto postSubscriptionTradingPairsRequest(final ApiAvailableChannelRequest apiAvailableChannelRequest) {
        LOGGER.info(String.format("Post request Available Trading Pairs received: %s.", apiAvailableChannelRequest));
        return marketDataApiService.getAvailableChannels(apiAvailableChannelRequest.getMarketDataFeed());
    }

    @PostMapping(value = "/subscribeToChannel")
    @Operation(summary = "Subscribe to channel.",
            description = "This methods subscribes to a trading pair channel available on the market data feed.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Bad Request"),
    })
    @Parameters({
            @Parameter(name = "marketDataFeed", description = "market data feed", required = true, in = ParameterIn.QUERY,
                    content = @Content(schema = @Schema(implementation = MarketDataFeedEnum.class, defaultValue = "BITSTAMP"))),
            @Parameter(name = "tradingPair", description = "trading pair", required = true, in = ParameterIn.QUERY,
                    content = @Content(schema = @Schema(implementation = TradingPairEnum.class, defaultValue = "BTC_USD"))),
            @Parameter(name = "channelName", description = "channel name", required = true, in = ParameterIn.QUERY,
                    content = @Content(schema = @Schema(implementation = ChannelEnum.class, defaultValue = "LIVE_ORDERS"))),
    })
    public ApiSubscriptionDto postSubscribeToChannelRequest(final ApiSubscriptionRequest subscriptionRequest) throws DeploymentException, IOException, InterruptedException {
        LOGGER.info(String.format("Post request Subscribe To Channel received: %s.", subscriptionRequest.toString()));
        return marketDataApiService.subscribeRequest(subscriptionRequest);
    }

    @PostMapping(value = "/unsubscribeToChannel")
    @Operation(summary = "Unsubscribe to channel.",
            description = "This methods unsubscribes to a trading pair channel available on the market data feed.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Bad Request"),
    })
    @Parameters({
            @Parameter(name = "marketDataFeed", description = "market data feed", required = true, in = ParameterIn.QUERY,
                    content = @Content(schema = @Schema(implementation = MarketDataFeedEnum.class, defaultValue = "BITSTAMP"))),
            @Parameter(name = "tradingPair", description = "trading pair", required = true, in = ParameterIn.QUERY,
                    content = @Content(schema = @Schema(implementation = TradingPairEnum.class, defaultValue = "BTC_USD"))),
            @Parameter(name = "channelName", description = "channel name", required = true, in = ParameterIn.QUERY,
                    content = @Content(schema = @Schema(implementation = ChannelEnum.class, defaultValue = "LIVE_ORDERS"))),
    })
    public ApiSubscriptionDto postUnsubscribeToChannelRequest(final ApiSubscriptionRequest subscriptionRequest) throws DeploymentException, IOException, InterruptedException {
        LOGGER.info(String.format("Post request Unsubscribe To Channel received: %s.", subscriptionRequest.toString()));
        return marketDataApiService.unsubscribeRequest(subscriptionRequest);
    }

    @PostMapping(value = "/isSubscribedToChannel")
    @Operation(summary = "Check if subscribes to channel.",
            description = "This methods checks if subscribed to a channel")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Bad Request"),
    })
    @Parameters({
            @Parameter(name = "marketDataFeed", description = "market data feed", required = true, in = ParameterIn.QUERY,
                    content = @Content(schema = @Schema(implementation = MarketDataFeedEnum.class, defaultValue = "BITSTAMP"))),
            @Parameter(name = "tradingPair", description = "trading pair", required = true, in = ParameterIn.QUERY,
                    content = @Content(schema = @Schema(implementation = TradingPairEnum.class, defaultValue = "BTC_USD"))),
            @Parameter(name = "channelName", description = "channel name", required = true, in = ParameterIn.QUERY,
                    content = @Content(schema = @Schema(implementation = ChannelEnum.class, defaultValue = "LIVE_ORDERS"))),
    })
    public ApiSubscriptionDto postIsSubscribedRequest(final ApiSubscriptionRequest subscriptionRequest) throws DeploymentException, IOException, InterruptedException {
        LOGGER.info(String.format("Post request is subscribed: %s.", subscriptionRequest.toString()));
        return marketDataApiService.isSubscribedToChannelRequest(subscriptionRequest);
    }


    @PostMapping(value = "/createOrder")
    @Operation(summary = "Send basic order.",
            description = "This methods sends a order into the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Bad Request"),
    })
    @Parameters({
            @Parameter(name = "price", description = "Price of the order", example = "100.05", required = true, in = ParameterIn.QUERY),
            @Parameter(name = "volume", description = "Volume of the order", example = "99.1", required = true, in = ParameterIn.QUERY),
            @Parameter(name = "orderType", description = "order type", required = true, in = ParameterIn.QUERY,
                    content = @Content(schema = @Schema(implementation = OrderTypeEnum.class, defaultValue = "BUY"))),
            @Parameter(name = "orderOperation", description = "order operation", required = true, in = ParameterIn.QUERY,
                    content = @Content(schema = @Schema(implementation = OrderOperationEnum.class, defaultValue = "CREATE"))),
            @Parameter(name = "marketDataFeed", description = "market data feed", required = true, in = ParameterIn.QUERY,
                    content = @Content(schema = @Schema(implementation = MarketDataFeedEnum.class, defaultValue = "BITSTAMP"))),
            @Parameter(name = "tradingPair", description = "trading pair", required = true, in = ParameterIn.QUERY,
                    content = @Content(schema = @Schema(implementation = TradingPairEnum.class, defaultValue = "BTC_USD"))),
            @Parameter(name = "channelName", description = "channel name", required = true, in = ParameterIn.QUERY,
                    content = @Content(schema = @Schema(implementation = ChannelEnum.class, defaultValue = "LIVE_ORDERS"))),
            @Parameter(name = "eventDescription", description = "event description", required = true, in = ParameterIn.QUERY,
                    content = @Content(schema = @Schema(implementation = EventDescriptionEnum.class, defaultValue = "order_created"))),
            @Parameter(name = "instrumentType", description = "instrument type", required = true, in = ParameterIn.QUERY,
                    content = @Content(schema = @Schema(implementation = InstrumentTypeEnum.class, defaultValue = "STOCK"))),
    })
    public ApiSubscriptionDto postOrder(final ApiOrderRequest orderRequest) {
        LOGGER.info(String.format("Post request order : %s.", orderRequest.toString()));
        marketDataApiService.pushOrder(orderRequest);
        return null;
    }

    @PostMapping(value = "/createTrade")
    @Operation(summary = "Send basic trade.",
            description = "This methods sends a trade into the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Bad Request"),
    })
    @Parameters({
            @Parameter(name = "price", description = "Price of the order", example = "100.05", required = true, in = ParameterIn.QUERY),
            @Parameter(name = "volume", description = "Volume of the order", example = "99.1", required = true, in = ParameterIn.QUERY),
            @Parameter(name = "bidSideAggressor", description = "true", required = true, in = ParameterIn.QUERY),
            @Parameter(name = "marketDataFeed", description = "market data feed", required = true, in = ParameterIn.QUERY,
                    content = @Content(schema = @Schema(implementation = MarketDataFeedEnum.class, defaultValue = "BITSTAMP"))),
            @Parameter(name = "tradingPair", description = "trading pair", required = true, in = ParameterIn.QUERY,
                    content = @Content(schema = @Schema(implementation = TradingPairEnum.class, defaultValue = "BTC_USD"))),
            @Parameter(name = "channelName", description = "channel name", required = true, in = ParameterIn.QUERY,
                    content = @Content(schema = @Schema(implementation = ChannelEnum.class, defaultValue = "LIVE_ORDERS"))),
            @Parameter(name = "eventDescription", description = "event description", required = true, in = ParameterIn.QUERY,
                    content = @Content(schema = @Schema(implementation = EventDescriptionEnum.class, defaultValue = "order_created"))),
            @Parameter(name = "instrumentType", description = "instrument type", required = true, in = ParameterIn.QUERY,
                    content = @Content(schema = @Schema(implementation = InstrumentTypeEnum.class, defaultValue = "STOCK"))),
    })
    public ApiSubscriptionDto postTrade(final ApiTradeRequest tradeRequest) {
        LOGGER.info(String.format("Post request order : %s.", tradeRequest.toString()));
        marketDataApiService.pushTrade(tradeRequest);
        return null;
    }
}
