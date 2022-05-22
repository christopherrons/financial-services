package com.christopherrons.tradingengine.orderbook.orderbook;

import com.christopherrons.marketdata.common.enums.event.MargetDataFeedEnum;
import com.christopherrons.marketdata.common.enums.event.OrderTypeEnum;
import com.christopherrons.tradingengine.matchingengine.model.MatchingEngineResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static testutils.EventCreatorUtils.buildMarketDataOrderCreate;

class FifoOrderBookTest {

    private FifoOrderBook fifoOrderBook;
    private final MargetDataFeedEnum margetDataFeedEnum = MargetDataFeedEnum.BITSTAMP;

    @BeforeEach
    void init() {
        this.fifoOrderBook = new FifoOrderBook();
    }

    @Test
    void testAddToOrderBook() {
        fifoOrderBook.addOrder(buildMarketDataOrderCreate(margetDataFeedEnum, 0,
                100, 10, OrderTypeEnum.BUY, 1));
        fifoOrderBook.addOrder(buildMarketDataOrderCreate(margetDataFeedEnum, 0,
                100, 10, OrderTypeEnum.BUY, 2));
        fifoOrderBook.addOrder(buildMarketDataOrderCreate(margetDataFeedEnum, 2,
                101, 10, OrderTypeEnum.SELL, 3));

        assertEquals(3, fifoOrderBook.totalNumberOfActiveOrders());
        assertEquals(2, fifoOrderBook.totalNumberOfBidOrders());
        assertEquals(1, fifoOrderBook.totalNumberOfAskOrders());
    }

    @Test
    void testUpdateOrderBook() {
        fifoOrderBook.addOrder(buildMarketDataOrderCreate(margetDataFeedEnum, 0,
                100, 10, OrderTypeEnum.BUY, 1));
        assertEquals(1, fifoOrderBook.totalNumberOfPriceLevels());
        assertEquals(1, fifoOrderBook.totalNumberOfBidPriceLevels());
        assertEquals(0, fifoOrderBook.totalNumberOfAskPriceLevels());
        assertEquals(10, fifoOrderBook.getOrder(1).getCurrentVolume());
        assertEquals(100, fifoOrderBook.getOrder(1).getPrice());

        fifoOrderBook.updateOrder(buildMarketDataOrderCreate(margetDataFeedEnum, 0,
                99, 9, OrderTypeEnum.BUY, 1));
        assertEquals(1, fifoOrderBook.totalNumberOfPriceLevels());
        assertEquals(1, fifoOrderBook.totalNumberOfBidPriceLevels());
        assertEquals(0, fifoOrderBook.totalNumberOfAskPriceLevels());
        assertEquals(9, fifoOrderBook.getOrder(1).getCurrentVolume());
        assertEquals(99, fifoOrderBook.getOrder(1).getPrice());
    }

    @Test
    void testBestPriceAfterInsertOrders() {
        fifoOrderBook.addOrder(buildMarketDataOrderCreate(margetDataFeedEnum, 0,
                100, 10, OrderTypeEnum.BUY, 1));
        fifoOrderBook.addOrder(buildMarketDataOrderCreate(margetDataFeedEnum, 2,
                102, 10, OrderTypeEnum.SELL, 2));
        assertEquals(100, fifoOrderBook.getBestBidPrice());
        assertEquals(102, fifoOrderBook.getBestAskPrice());

        fifoOrderBook.addOrder(buildMarketDataOrderCreate(margetDataFeedEnum, 0,
                101, 10, OrderTypeEnum.BUY, 3));
        fifoOrderBook.addOrder(buildMarketDataOrderCreate(margetDataFeedEnum, 2,
                101, 10, OrderTypeEnum.SELL, 3));
        assertEquals(101, fifoOrderBook.getBestBidPrice());
        assertEquals(101, fifoOrderBook.getBestAskPrice());
    }

    @Test
    void testRemoveFromOrderBook() {
        fifoOrderBook.addOrder(buildMarketDataOrderCreate(margetDataFeedEnum, 0,
                100, 10, OrderTypeEnum.BUY, 1));
        fifoOrderBook.addOrder(buildMarketDataOrderCreate(margetDataFeedEnum, 0,
                100, 10, OrderTypeEnum.BUY, 2));
        fifoOrderBook.addOrder(buildMarketDataOrderCreate(margetDataFeedEnum, 2,
                101, 10, OrderTypeEnum.SELL, 3));

        assertEquals(3, fifoOrderBook.totalNumberOfActiveOrders());
        assertEquals(2, fifoOrderBook.totalNumberOfBidOrders());
        assertEquals(1, fifoOrderBook.totalNumberOfAskOrders());

        fifoOrderBook.removeOrder(2);
        fifoOrderBook.removeOrder(3);
        assertEquals(1, fifoOrderBook.totalNumberOfActiveOrders());
        assertEquals(1, fifoOrderBook.totalNumberOfBidOrders());
        assertEquals(0, fifoOrderBook.totalNumberOfAskOrders());
    }

    @Test
    void testBestPriceAfterRemoveOrder() {
        fifoOrderBook.addOrder(buildMarketDataOrderCreate(margetDataFeedEnum, 0,
                100, 10, OrderTypeEnum.BUY, 1));
        fifoOrderBook.addOrder(buildMarketDataOrderCreate(margetDataFeedEnum, 2,
                102, 10, OrderTypeEnum.SELL, 2));
        fifoOrderBook.addOrder(buildMarketDataOrderCreate(margetDataFeedEnum, 0,
                99, 10, OrderTypeEnum.BUY, 3));
        fifoOrderBook.addOrder(buildMarketDataOrderCreate(margetDataFeedEnum, 2,
                103, 10, OrderTypeEnum.SELL, 4));
        assertEquals(100, fifoOrderBook.getBestBidPrice());
        assertEquals(102, fifoOrderBook.getBestAskPrice());

        fifoOrderBook.removeOrder(1);
        fifoOrderBook.removeOrder(2);
        assertEquals(99, fifoOrderBook.getBestBidPrice());
        assertEquals(103, fifoOrderBook.getBestAskPrice());
    }

    @Test
    void testVolume() {
        fifoOrderBook.addOrder(buildMarketDataOrderCreate(margetDataFeedEnum, 0,
                100, 11, OrderTypeEnum.BUY, 1));
        fifoOrderBook.addOrder(buildMarketDataOrderCreate(margetDataFeedEnum, 2,
                102, 13, OrderTypeEnum.SELL, 2));
        fifoOrderBook.addOrder(buildMarketDataOrderCreate(margetDataFeedEnum, 0,
                99, 12, OrderTypeEnum.BUY, 3));
        fifoOrderBook.addOrder(buildMarketDataOrderCreate(margetDataFeedEnum, 2,
                103, 10, OrderTypeEnum.SELL, 4));
        assertEquals(46, fifoOrderBook.totalOrderVolume());
        assertEquals(23, fifoOrderBook.totalBidVolume());
        assertEquals(23, fifoOrderBook.totalAskVolume());


        fifoOrderBook.removeOrder(1);
        fifoOrderBook.removeOrder(2);
        assertEquals(22, fifoOrderBook.totalOrderVolume());
        assertEquals(12, fifoOrderBook.totalBidVolume());
        assertEquals(10, fifoOrderBook.totalAskVolume());
    }

    @Test
    void testVolumeAtLevel() {
        fifoOrderBook.addOrder(buildMarketDataOrderCreate(margetDataFeedEnum, 0,
                100, 11, OrderTypeEnum.BUY, 1));
        fifoOrderBook.addOrder(buildMarketDataOrderCreate(margetDataFeedEnum, 2,
                102, 13, OrderTypeEnum.SELL, 2));
        fifoOrderBook.addOrder(buildMarketDataOrderCreate(margetDataFeedEnum, 0,
                99, 12, OrderTypeEnum.BUY, 3));
        fifoOrderBook.addOrder(buildMarketDataOrderCreate(margetDataFeedEnum, 2,
                103, 10, OrderTypeEnum.SELL, 4));
        fifoOrderBook.addOrder(buildMarketDataOrderCreate(margetDataFeedEnum, 0,
                100, 10, OrderTypeEnum.BUY, 5));
        assertEquals(34, fifoOrderBook.totalVolumeAtPriceLevel(1));
        assertEquals(21, fifoOrderBook.totalBidVolumeAtPriceLevel(1));
        assertEquals(13, fifoOrderBook.totalAskVolumeAtPriceLevel(1));
        assertEquals(22, fifoOrderBook.totalVolumeAtPriceLevel(2));
        assertEquals(12, fifoOrderBook.totalBidVolumeAtPriceLevel(2));
        assertEquals(10, fifoOrderBook.totalAskVolumeAtPriceLevel(2));


        fifoOrderBook.removeOrder(1);
        fifoOrderBook.removeOrder(2);
        assertEquals(20, fifoOrderBook.totalVolumeAtPriceLevel(1));
        assertEquals(10, fifoOrderBook.totalBidVolumeAtPriceLevel(1));
        assertEquals(10, fifoOrderBook.totalAskVolumeAtPriceLevel(1));
        assertEquals(12, fifoOrderBook.totalVolumeAtPriceLevel(2));
        assertEquals(12, fifoOrderBook.totalBidVolumeAtPriceLevel(2));
        assertEquals(0, fifoOrderBook.totalAskVolumeAtPriceLevel(2));
    }

    @Test
    void testMatchingAlgorithmSamePriceLevel() {
        fifoOrderBook.addOrder(buildMarketDataOrderCreate(margetDataFeedEnum, 0,
                100, 10, OrderTypeEnum.BUY, 1));
        fifoOrderBook.addOrder(buildMarketDataOrderCreate(margetDataFeedEnum, 2,
                100, 15, OrderTypeEnum.SELL, 2));

        MatchingEngineResult matchingEngineResult = fifoOrderBook.runMatchingEngine();
        assertEquals(5, fifoOrderBook.totalOrderVolume());
        assertNotEquals(0, matchingEngineResult.getTrades().get(0).getTradeId());
        assertFalse(matchingEngineResult.getTrades().get(0).isBidSideAggressor());

        fifoOrderBook.addOrder(buildMarketDataOrderCreate(margetDataFeedEnum, 3,
                100, 6, OrderTypeEnum.BUY, 3));

        matchingEngineResult = fifoOrderBook.runMatchingEngine();
        assertEquals(1, fifoOrderBook.totalOrderVolume());
        assertNotEquals(0, matchingEngineResult.getTrades().get(0).getTradeId());
        assertTrue(matchingEngineResult.getTrades().get(0).isBidSideAggressor());
    }

    @Test
    void testMatchingAlgorithmMultiplePriceLevel() {
        fifoOrderBook.addOrder(buildMarketDataOrderCreate(margetDataFeedEnum, 0,
                101, 10, OrderTypeEnum.SELL, 1));
        fifoOrderBook.addOrder(buildMarketDataOrderCreate(margetDataFeedEnum, 2,
                100, 20, OrderTypeEnum.SELL, 2));

        MatchingEngineResult matchingEngineResult = fifoOrderBook.runMatchingEngine();
        assertEquals(30, fifoOrderBook.totalOrderVolume());
        assertEquals(0, matchingEngineResult.getTrades().size());


        fifoOrderBook.addOrder(buildMarketDataOrderCreate(margetDataFeedEnum, 3,
                101, 25, OrderTypeEnum.BUY, 3));

        matchingEngineResult = fifoOrderBook.runMatchingEngine();
        assertEquals(5, fifoOrderBook.totalOrderVolume());
        assertEquals(101, fifoOrderBook.getBestAskPrice());
        assertEquals(0, fifoOrderBook.getBestBidPrice());
        assertEquals(2, matchingEngineResult.getTrades().size());
        assertEquals(3, matchingEngineResult.getEffectedOrders().size());
        assertNotEquals(matchingEngineResult.getTrades().get(0).getTradeId(), matchingEngineResult.getTrades().get(1).getTradeId());
    }

}