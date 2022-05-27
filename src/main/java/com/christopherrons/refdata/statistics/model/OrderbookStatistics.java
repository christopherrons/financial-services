package com.christopherrons.refdata.statistics.model;

import com.christopherrons.marketdata.api.MarketDataOrder;
import com.christopherrons.marketdata.api.MarketDataTrade;
import com.christopherrons.marketdata.common.enums.event.OrderOperationEnum;
import com.christopherrons.marketdata.common.enums.event.OrderTypeEnum;

public class OrderbookStatistics {

    private int totalNumberOfOrderInserts = 0;
    private int totalNumberOfTrades = 0;
    private double totalNumberOfBidOrders = 0;
    private double totalNumberOfAskOrders = 0;
    private double totalVolumeAdded = 0;
    private double totalVolumeTraded = 0;
    private double aggregatedBidPrice = 0;
    private double aggregatedAskPrice = 0;
    private double aggregatedTradePrice = 0;
    private double aggregatedTradeTurnover = 0;
    private double aggregatedOrderValue = 0;

    private final String orderbookId;

    public OrderbookStatistics(String orderbookId) {
        this.orderbookId = orderbookId;
    }

    public void update(final MarketDataTrade trade) {
        totalNumberOfTrades++;
        totalVolumeTraded = totalVolumeTraded + trade.getVolume();
        aggregatedTradePrice = aggregatedTradePrice + trade.getPrice();
        aggregatedTradeTurnover = aggregatedTradeTurnover + trade.getTurnover();
    }

    public void update(final MarketDataOrder order) {
        if (order.getOrderOperationEnum().equals(OrderOperationEnum.CREATE)) {
            totalNumberOfOrderInserts++;
            aggregatedOrderValue = aggregatedOrderValue + order.getOrderValue();
            totalVolumeAdded = totalVolumeAdded + order.getInitialVolume();
            if (order.getOrderType() == OrderTypeEnum.BUY.getValue()) {
                totalNumberOfBidOrders++;
                aggregatedBidPrice = aggregatedBidPrice + order.getPrice();

            } else {
                totalNumberOfAskOrders++;
                aggregatedAskPrice = aggregatedAskPrice + order.getPrice();
            }

        }
    }

    public double getAverageOrderValue() {
        return aggregatedOrderValue / totalNumberOfOrderInserts;
    }


    public double getAverageTurnOver() {
        return aggregatedTradeTurnover / totalNumberOfTrades;
    }

    public double getVwap() {
        return aggregatedTradeTurnover / totalVolumeAdded;
    }

    public double getAverageBidPrice() {
        return aggregatedBidPrice / totalNumberOfBidOrders;
    }

    public double getAverageAskPrice() {
        return aggregatedAskPrice / totalNumberOfAskOrders;
    }

    public int getTotalNumberOfOrderInserts() {
        return totalNumberOfOrderInserts;
    }

    public int getTotalNumberOfTrades() {
        return totalNumberOfTrades;
    }

    public double getTotalNumberOfBidOrders() {
        return totalNumberOfBidOrders;
    }

    public double getTotalNumberOfAskOrders() {
        return totalNumberOfAskOrders;
    }

    public double getTotalVolumeAdded() {
        return totalVolumeAdded;
    }

    public double getTotalVolumeTraded() {
        return totalVolumeTraded;
    }

    public double getAggregatedBidPrice() {
        return aggregatedBidPrice;
    }

    public double getAggregatedAskPrice() {
        return aggregatedAskPrice;
    }

    public double getAggregatedTradePrice() {
        return aggregatedTradePrice;
    }

    public double getAggregatedTradeTurnover() {
        return aggregatedTradeTurnover;
    }
}
