package com.christopherrons.pricingengine.marginprice.calculators;

import com.christopherrons.common.enums.pricing.MarginPriceMethodEnum;
import com.christopherrons.common.model.pricing.MarginPrice;

import java.util.Optional;

public class MarginPriceMidBidAsk {

    private MarginPriceMidBidAsk() {
        throw new IllegalStateException("Utility class");
    }

    public static Optional<MarginPrice> createMarginPriceMidBidAsk(final String instrumentId, final double askPrice, final double bidPrice) {
        if (bidPrice == 0 && askPrice == 0) {
            return Optional.empty();
        }
        return Optional.of(new MarginPrice(
                instrumentId,
                (bidPrice + askPrice) / 2,
                MarginPriceMethodEnum.MID_BID_ASK)
        );
    }
}
