package com.christopherrons.tradingengine.orderbook.orderbook;

import com.christopherrons.common.misc.comparators.FifoOrderBookComparator;
import com.christopherrons.marketdata.api.MarketDataOrder;
import com.christopherrons.tradingengine.matchingengine.algorithms.FifoMatchingAlgorithm;
import com.christopherrons.tradingengine.matchingengine.api.MatchingAlgorithm;
import com.christopherrons.tradingengine.matchingengine.enums.MatchingAlgorithmEnum;
import com.christopherrons.tradingengine.matchingengine.model.MatchingEngineResult;
import com.christopherrons.tradingengine.orderbook.api.Orderbook;
import com.christopherrons.tradingengine.orderbook.model.ActiveOrders;

public class FifoOrderBook implements Orderbook {

    private final ActiveOrders activeOrders = new ActiveOrders(new FifoOrderBookComparator());
    private final MatchingAlgorithm matchingAlgorithm = new FifoMatchingAlgorithm(activeOrders);

    @Override
    public void updateOrder(final MarketDataOrder order) {
        activeOrders.updateOrder(order);
    }

    @Override
    public void addOrder(final MarketDataOrder order) {
        activeOrders.addOrder(order);
    }

    @Override
    public void removeOrder(final long orderId) {
        activeOrders.removeOrder(orderId);
    }

    @Override
    public void removeOrder(final MarketDataOrder order) {
        activeOrders.removeOrder(order);
    }

    @Override
    public double getBestBidPrice() {
        return activeOrders.getBestBidPrice();
    }

    @Override
    public double getBestAskPrice() {
        return activeOrders.getBestAskPrice();
    }

    @Override
    public boolean hasBidAndAskOrders() {
        return activeOrders.hasBidAndAskOrders();
    }

    @Override
    public long totalNumberOfBidOrders() {
        return activeOrders.totalNumberOfBidOrders();
    }

    @Override
    public long totalNumberOfAskOrders() {
        return activeOrders.totalNumberOfAskOrders();
    }

    @Override
    public long totalNumberOfActiveOrders() {
        return activeOrders.totalNumberOfActiveOrders();
    }

    @Override
    public double totalOrderVolume() {
        return activeOrders.totalOrderVolume();
    }

    @Override
    public double totalBidVolume() {
        return activeOrders.totalBidVolume();
    }

    @Override
    public double totalAskVolume() {
        return activeOrders.totalAskVolume();
    }

    public double totalVolumeAtPriceLevel(int priceLevel) {
        return activeOrders.totalVolumeAtPriceLevel(priceLevel);
    }

    public double totalBidVolumeAtPriceLevel(int priceLevel) {
        return activeOrders.totalBidVolumeAtPriceLevel(priceLevel);
    }

    public double totalAskVolumeAtPriceLevel(int priceLevel) {
        return activeOrders.totalAskVolumeAtPriceLevel(priceLevel);
    }

    public int totalNumberOfPriceLevels() {
        return activeOrders.totalNumberOfPriceLevels();
    }

    public int totalNumberOfBidPriceLevels() {
        return activeOrders.totalNumberOfBidPriceLevels();

    }

    public int totalNumberOfAskPriceLevels() {
        return activeOrders.totalNumberOfAskPriceLevels();
    }

    public MarketDataOrder getOrder(final long orderId) {
        return activeOrders.getOrder(orderId);
    }

    @Override
    public MatchingEngineResult runMatchingEngine() {
        activeOrders.printOrderbook();
        return matchingAlgorithm.runMatchingAlgorithm();
    }

    @Override
    public MatchingAlgorithmEnum getMatchingAlgorithm() {
        return MatchingAlgorithmEnum.FIFO;
    }

}
