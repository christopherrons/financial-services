package com.christopherrons.common.model.refdata.derivativesmargin;

import java.util.HashMap;
import java.util.Map;

public class DerivatesMarginRefData {

    private final Map<String, CombinedCommodity> instrumentIdToCC = new HashMap<>();


    public CombinedCommodity getCombinedCommodity(final String instrumentId) {
        return instrumentIdToCC.get(instrumentId);
    }

    public boolean isInstrumentIdMappableToCombinedCommodity(final String instrumentId) {
        return instrumentIdToCC.containsKey(instrumentId);
    }
}
