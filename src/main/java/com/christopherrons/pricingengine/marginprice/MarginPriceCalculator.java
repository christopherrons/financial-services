package com.christopherrons.pricingengine.marginprice;

import com.christopherrons.marketdata.common.enums.subscription.TradingPairEnum;
import com.christopherrons.pricingengine.enums.MarginPriceMethodEnum;
import com.christopherrons.pricingengine.marginprice.calculators.MarginPriceLastTraded;
import com.christopherrons.pricingengine.marginprice.model.MarginPrice;
import com.christopherrons.pricingengine.pricecollection.model.PriceSnapshot;

import java.util.Optional;

import static com.christopherrons.pricingengine.marginprice.calculators.MarginPriceMidBidAsk.createMarginPriceMidBidAsk;

public class MarginPriceCalculator {


    public MarginPrice calculateMarginPrice(final PriceSnapshot snapshot) {
        long orderbookId = snapshot.getOrderbookId();

        Optional<MarginPrice> marginPrice = MarginPriceLastTraded.createMarginPriceLastTraded(orderbookId, snapshot.getLastPrice());
        if (marginPrice.isPresent()) {
            return marginPrice.get();
        }

        marginPrice = createMarginPriceMidBidAsk(orderbookId, snapshot.getAskPrice(), snapshot.getBidPrice());
        //TODO: Add historical and theoretical

        return marginPrice.orElseGet(() -> new MarginPrice(
                orderbookId,
                0,
                MarginPriceMethodEnum.NOT_SET));

    }
}
