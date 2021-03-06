package com.christopherrons.pricingengine.marginprice;

import com.christopherrons.common.enums.pricing.MarginPriceMethodEnum;
import com.christopherrons.pricingengine.marginprice.calculators.MarginPriceLastTraded;
import com.christopherrons.common.model.pricing.MarginPrice;
import com.christopherrons.pricingengine.pricecollection.model.SnapshotPrice;

import java.util.Optional;

import static com.christopherrons.pricingengine.marginprice.calculators.MarginPriceMidBidAsk.createMarginPriceMidBidAsk;

public class MarginPriceCalculator {


    public MarginPrice calculateMarginPrice(final SnapshotPrice snapshot) {
        String instrumentId = snapshot.getInstrumentId();

        Optional<MarginPrice> marginPrice = MarginPriceLastTraded.createMarginPriceLastTraded(instrumentId, snapshot.getLastPrice());
        if (marginPrice.isPresent()) {
            return marginPrice.get();
        }

        marginPrice = createMarginPriceMidBidAsk(instrumentId, snapshot.getAskPrice(), snapshot.getBidPrice());
        //TODO: Add historical and theoretical

        return marginPrice.orElseGet(() -> new MarginPrice(
                instrumentId,
                0,
                MarginPriceMethodEnum.NOT_SET));

    }
}
