package com.christopherrons.restapi.marketdata.requests;

import com.christopherrons.marketdata.common.enums.event.OrderOperationEnum;
import com.christopherrons.marketdata.common.enums.event.OrderTypeEnum;

public class ApiOrderRequest {

    private double price;
    private double volume;
    private OrderTypeEnum orderType;
    private OrderOperationEnum orderOperation;

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public OrderTypeEnum getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderTypeEnum orderType) {
        this.orderType = orderType;
    }

    public OrderOperationEnum getOrderOperation() {
        return orderOperation;
    }

    public void setOrderOperation(OrderOperationEnum orderOperation) {
        this.orderOperation = orderOperation;
    }
}
