package com.christopherrons.pricingengine.marginprice.model;

import com.christopherrons.pricingengine.enums.MarginPriceMethodEnum;

public record MarginPrice(String instrumentId,
                          double marginPrice,
                          MarginPriceMethodEnum marginPriceMethodEnum) {
}
