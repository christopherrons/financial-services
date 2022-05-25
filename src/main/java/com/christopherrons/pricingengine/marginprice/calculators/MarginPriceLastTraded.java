package com.christopherrons.pricingengine.marginprice.calculators;

import com.christopherrons.pricingengine.enums.MarginPriceMethodEnum;
import com.christopherrons.pricingengine.marginprice.model.MarginPrice;

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

