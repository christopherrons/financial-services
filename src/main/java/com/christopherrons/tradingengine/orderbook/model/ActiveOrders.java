package com.christopherrons.tradingengine.orderbook.model;

import com.christopherrons.marketdata.api.MarketDataOrder;
import com.christopherrons.marketdata.common.enums.event.OrderTypeEnum;

import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class ActiveOrders {
    private final Map<Long, MarketDataOrder> orderIdToOrder = new ConcurrentHashMap<>();
    private final TreeMap<Double, PriceLevel> bidPriceToPriceLevel = new TreeMap<>(Comparator.reverseOrder());
    private final TreeMap<Double, PriceLevel> askPriceToPriceLevel = new TreeMap<>();

    private final Comparator<? super MarketDataOrder> comparator;

    public ActiveOrders(Comparator<? super MarketDataOrder> comparator) {
        this.comparator = comparator;
    }

    public void updateOrder(final MarketDataOrder order) {
        removeOrder(order.getOrderId());
        addOrder(order);
    }

    public void addOrder(final MarketDataOrder order) {
        PriceLevel priceLevel = findOrCreatePriceLevel(order);
        priceLevel.add(order);
        orderIdToOrder.putIfAbsent(order.getOrderId(), order);
    }

    public void removeOrder(final MarketDataOrder order) {
        removeOrder(order.getOrderId());
    }

    public void removeOrder(final long orderId) {
        MarketDataOrder order = orderIdToOrder.remove(orderId);
        PriceLevel priceLevel = findOrCreatePriceLevel(order);
        priceLevel.remove(order);
        if (priceLevel.isEmpty()) {
            removePriceLevel(order);
        }
    }

    private PriceLevel findOrCreatePriceLevel(final MarketDataOrder order) {
        return switch (OrderTypeEnum.fromValue(order.getOrderType())) {
            case BUY -> bidPriceToPriceLevel.computeIfAbsent(order.getPrice(),
                    key -> new PriceLevel(order.getPrice(), comparator));
            case SELL -> askPriceToPriceLevel.computeIfAbsent(order.getPrice(),
                    key -> new PriceLevel(order.getPrice(), comparator));
            case INVALID_ORDER_TYPE -> null;
        };
    }

    private void removePriceLevel(final MarketDataOrder order) {
        switch (OrderTypeEnum.fromValue(order.getOrderType())) {
            case BUY -> bidPriceToPriceLevel.remove(order.getPrice());
            case SELL -> askPriceToPriceLevel.remove(order.getPrice());
        }
    }

    public int totalNumberOfPriceLevels() {
        return totalNumberOfBidPriceLevels() + totalNumberOfAskPriceLevels();
    }

    public int totalNumberOfBidPriceLevels() {
        return bidPriceToPriceLevel.values().size();
    }

    public int totalNumberOfAskPriceLevels() {
        return askPriceToPriceLevel.values().size();
    }

    public MarketDataOrder getOrder(final long orderId) {
        return orderIdToOrder.get(orderId);
    }

    public double getBestBidPrice() {
        return getBestBidOrder().map(MarketDataOrder::getPrice).orElse(0.0);
    }

    public double getBestAskPrice() {
        return getBestAskOrder().map(MarketDataOrder::getPrice).orElse(0.0);
    }

    public Optional<MarketDataOrder> getBestBidOrder() {
        return getBestOrder(OrderTypeEnum.BUY.getValue());
    }

    public Optional<MarketDataOrder> getBestAskOrder() {
        return getBestOrder(OrderTypeEnum.SELL.getValue());
    }

    private Optional<MarketDataOrder> getBestOrder(final int orderType) {
        return switch (OrderTypeEnum.fromValue(orderType)) {
            case BUY -> bidPriceToPriceLevel.values().stream().findFirst().map(PriceLevel::first);
            case SELL -> askPriceToPriceLevel.values().stream().findFirst().map(PriceLevel::first);
            case INVALID_ORDER_TYPE -> Optional.empty();
        };
    }

    public long totalNumberOfBidOrders() {
        return totalNumberOfSideOrders(OrderTypeEnum.BUY.getValue());
    }

    public long totalNumberOfAskOrders() {
        return totalNumberOfSideOrders(OrderTypeEnum.SELL.getValue());
    }

    private long totalNumberOfSideOrders(int orderType) {
        return switch (OrderTypeEnum.fromValue(orderType)) {
            case BUY -> bidPriceToPriceLevel.values().stream().mapToLong(PriceLevel::nrOfOrdersAtPriceLevel).sum();
            case SELL -> askPriceToPriceLevel.values().stream().mapToLong(PriceLevel::nrOfOrdersAtPriceLevel).sum();
            case INVALID_ORDER_TYPE -> 0;
        };
    }

    public long totalNumberOfActiveOrders() {
        return orderIdToOrder.size();
    }

    public double totalOrderVolume() {
        return totalBidVolume() + totalAskVolume();
    }

    public double totalBidVolume() {
        return bidPriceToPriceLevel.values().stream().mapToDouble(PriceLevel::volumeAtPriceLevel).sum();
    }

    public double totalAskVolume() {
        return askPriceToPriceLevel.values().stream().mapToDouble(PriceLevel::volumeAtPriceLevel).sum();
    }

    public double totalVolumeAtPriceLevel(int priceLevel) {
        return totalBidVolumeAtPriceLevel(priceLevel) + totalAskVolumeAtPriceLevel(priceLevel);
    }

    public double totalBidVolumeAtPriceLevel(int priceLevel) {
        return bidPriceToPriceLevel.values().stream()
                .skip(priceLevel - 1L)
                .findFirst().map(PriceLevel::volumeAtPriceLevel)
                .orElse(0.0);
    }

    public double totalAskVolumeAtPriceLevel(int priceLevel) {
        return askPriceToPriceLevel.values().stream()
                .skip(priceLevel - 1L)
                .findFirst().map(PriceLevel::volumeAtPriceLevel)
                .orElse(0.0);
    }

    public boolean hasBidAndAskOrders() {
        return bidPriceToPriceLevel.size() != 0 && askPriceToPriceLevel.size() != 0;
    }

    public void printOrderbook() {
        //TODO: For testing
        System.out.println("Bid- " + bidPriceToPriceLevel.keySet().stream().map(String::valueOf).collect(Collectors.joining(" | ")));
        System.out.println("Ask- " + askPriceToPriceLevel.keySet().stream().map(String::valueOf).collect(Collectors.joining(" | ")));
        System.out.println("");
    }

}
