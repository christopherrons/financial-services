package com.christopherrons.common.model.refdata.derivativesmargin;

import java.util.Map;

public record DerivatesMarginRefData(Map<String, CombinedCommodity> instrumentIdToCC) {

    public CombinedCommodity getCombinedCommodity(final String instrumentId) {
        return instrumentIdToCC.get(instrumentId);
    }

    public boolean isInstrumentIdMappableToCombinedCommodity(final String instrumentId) {
        return instrumentIdToCC.containsKey(instrumentId);
    }
}
