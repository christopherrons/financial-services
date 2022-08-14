package com.christopherrons.common.model.refdata.derivativesmargin;

import com.christopherrons.common.model.refdata.derivativesmargin.spanfile.pointintime.clearingorg.exchange.RiskArray;
import org.apache.commons.math3.stat.descriptive.summary.Product;

import java.util.HashMap;
import java.util.Map;

public class CombinedCommodity {

    Map<String, Product> instrumentIdToProduct = new HashMap<>();
    public RiskArray getRiskArray(final String instrumentId) {
        return null;
    }
}
