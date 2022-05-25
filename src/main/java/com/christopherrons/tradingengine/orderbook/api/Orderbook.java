package com.christopherrons.tradingengine.orderbook.api;

import com.christopherrons.marketdata.api.MarketDataOrder;
import com.christopherrons.tradingengine.matchingengine.enums.MatchingAlgorithmEnum;
import com.christopherrons.tradingengine.matchingengine.model.MatchingEngineResult;

public interface Orderbook {

    String getOrderbookId();

    MatchingAlgorithmEnum getMatchingAlgorithm();

    MatchingEngineResult runMatchingEngine();

    boolean hasBidAndAskOrders();

    void addOrder(MarketDataOrder order);

    MarketDataOrder getOrder(final long orderId);

    void updateOrder(MarketDataOrder order);

    void removeOrder(MarketDataOrder order);

    void removeOrder(long orderId);

    double totalOrderVolume();

    double totalBidVolume();

    double totalAskVolume();

    double getBestBidPrice();

    double getBestAskPrice();

    long totalNumberOfBidOrders();

    long totalNumberOfAskOrders();

    long totalNumberOfActiveOrders();

    double totalVolumeAtPriceLevel(int priceLevel);

    double totalBidVolumeAtPriceLevel(int priceLevel);

    double totalAskVolumeAtPriceLevel(int priceLevel);

    int totalNumberOfPriceLevels();

    int totalNumberOfBidPriceLevels();

    int totalNumberOfAskPriceLevels();
}

