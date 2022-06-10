package com.christopherrons.common.model.pricing;

import com.christopherrons.common.enums.pricing.MarginPriceMethodEnum;

public record MarginPrice(String instrumentId,
                          double price,
                          MarginPriceMethodEnum marginPriceMethodEnum) {
}
