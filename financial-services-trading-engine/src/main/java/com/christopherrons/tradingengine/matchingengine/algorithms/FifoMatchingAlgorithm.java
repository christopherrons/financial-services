package com.christopherrons.tradingengine.matchingengine.algorithms;

import com.christopherrons.common.api.marketdata.MarketDataOrder;
import com.christopherrons.common.api.marketdata.MarketDataTrade;
import com.christopherrons.common.model.trading.MatchingEngineResult;
import com.christopherrons.tradingengine.matchingengine.api.MatchingAlgorithm;
import com.christopherrons.tradingengine.orderbook.model.ActiveOrders;

import static com.christopherrons.tradingengine.matchingengine.utils.MatchingEngineUtils.buildTrade;
import static com.christopherrons.tradingengine.matchingengine.utils.MatchingEngineUtils.buildUpdateOrder;

public class FifoMatchingAlgorithm implements MatchingAlgorithm {

    private final ActiveOrders activeOrders;

    public FifoMatchingAlgorithm(ActiveOrders activeOrders) {
        this.activeOrders = activeOrders;
    }

    public MatchingEngineResult runMatchingAlgorithm() {
        MatchingEngineResult matchingEngineResult = new MatchingEngineResult();

        if (!activeOrders.hasBidAndAskOrders()) {
            return matchingEngineResult;
        }

        MarketDataOrder bestBid = activeOrders.getBestBidOrder().get();
        MarketDataOrder bestAsk = activeOrders.getBestAskOrder().get();

        while (isMatch(bestBid.getPrice(), bestAsk.getPrice())) {
            runPostTrade(matchingEngineResult, bestBid, bestAsk);

            if (!activeOrders.hasBidAndAskOrders()) {
                break;
            }

            bestBid = activeOrders.getBestBidOrder().get();
            bestAsk = activeOrders.getBestAskOrder().get();
        }

        return matchingEngineResult;
    }

    private boolean isMatch(final double bidPrice, final double askPrice) {
        return bidPrice >= askPrice;
    }

    private void runPostTrade(final MatchingEngineResult matchingEngineResult,
                              final MarketDataOrder bidOrder,
                              final MarketDataOrder askOrder) {
        final double tradeVolume = Math.min(bidOrder.getCurrentVolume(), askOrder.getCurrentVolume());
        final MarketDataTrade trade = buildTrade(bidOrder, askOrder, tradeVolume);
        matchingEngineResult.addTrade(trade);

        final MarketDataOrder updateBidOrder = buildUpdateOrder(bidOrder, tradeVolume);
        final MarketDataOrder updateAskOrder = buildUpdateOrder(askOrder, tradeVolume);

        updateOrderBook(updateBidOrder);
        updateOrderBook(updateAskOrder);

        matchingEngineResult.addOrder(updateBidOrder);
        matchingEngineResult.addOrder(updateAskOrder); //TODO Send cancel updates and store the orderbook id at participant level
    }

    private void updateOrderBook(final MarketDataOrder order) {
        if (order.isFilled()) {
            activeOrders.removeOrder(order);
        } else {
            activeOrders.updateOrder(order);
        }
    }

}
