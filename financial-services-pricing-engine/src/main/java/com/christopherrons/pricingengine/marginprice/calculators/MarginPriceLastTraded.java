package com.christopherrons.pricingengine.marginprice.calculators;

import com.christopherrons.common.enums.pricing.MarginPriceMethodEnum;
import com.christopherrons.common.model.pricing.MarginPrice;

import java.util.Optional;

public class MarginPriceLastTraded {

    private MarginPriceLastTraded() {
        throw new IllegalStateException("Utility class");
    }

    public static Optional<MarginPrice> createMarginPriceLastTraded(final String instrumentId, final double lastPrice) {
        if (lastPrice == 0) {
            return Optional.empty();
        }
        return Optional.of(new MarginPrice(
                instrumentId,
                lastPrice,
                MarginPriceMethodEnum.LAST_TRADED)
        );
    }
}

