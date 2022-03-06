package com.christopherrons.pricingengine.marginprice.calculators;

import com.christopherrons.marketdata.common.enums.subscription.TradingPairEnum;
import com.christopherrons.pricingengine.enums.MarginPriceMethodEnum;
import com.christopherrons.pricingengine.marginprice.model.MarginPrice;

import java.util.Optional;

public class MarginPriceMidBidAsk {

    private MarginPriceMidBidAsk() {
        throw new IllegalStateException("Utility class");
    }

    public static Optional<MarginPrice> createMarginPriceMidBidAsk(final TradingPairEnum tradingPair, final double askPrice, final double bidPrice) {
        if (bidPrice == 0 && askPrice == 0) {
            return Optional.empty();
        }
        return Optional.of(new MarginPrice(
                tradingPair,
                (bidPrice + askPrice) / 2,
                MarginPriceMethodEnum.MID_BID_ASK)
        );
    }
}
