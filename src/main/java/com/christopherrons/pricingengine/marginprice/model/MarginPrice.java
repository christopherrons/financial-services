package com.christopherrons.pricingengine.marginprice.model;

import com.christopherrons.pricingengine.enums.MarginPriceMethodEnum;

public record MarginPrice(long orderbookId,
                          double marginPrice,
                          MarginPriceMethodEnum marginPriceMethodEnum) {
}
