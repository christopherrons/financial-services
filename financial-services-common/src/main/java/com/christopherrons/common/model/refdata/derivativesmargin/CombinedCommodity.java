package com.christopherrons.common.model.refdata.derivativesmargin;

import com.christopherrons.common.model.refdata.derivativesmargin.spanfile.pointintime.clearingorg.exchange.Product;
import com.christopherrons.common.model.refdata.derivativesmargin.spanfile.pointintime.clearingorg.exchange.RiskArray;

import java.util.HashMap;
import java.util.Map;

public class CombinedCommodity {

    Map<String, Product> instrumentIdToProduct = new HashMap<>();

    public void addProduct(Product product) {
        instrumentIdToProduct.put(product.getProductId(), product);
    }

    public RiskArray getRiskArray(final String instrumentId) {
        Product product = instrumentIdToProduct.get(instrumentId);
        return product.getRiskArray();
    }


}
