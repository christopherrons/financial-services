package com.christopherrons.refdataservice.derivativesmargin;

import com.christopherrons.common.model.refdata.derivativesmargin.CombinedCommodity;
import com.christopherrons.common.model.refdata.derivativesmargin.DerivatesMarginRefData;
import com.christopherrons.common.model.refdata.derivativesmargin.spanfile.pointintime.PointInTime;
import com.christopherrons.common.model.refdata.derivativesmargin.spanfile.pointintime.clearingorg.ClearingOrg;
import com.christopherrons.common.model.refdata.derivativesmargin.spanfile.pointintime.clearingorg.ccdef.CcDef;
import com.christopherrons.common.model.refdata.derivativesmargin.spanfile.pointintime.clearingorg.ccdef.PfLink;
import com.christopherrons.common.model.refdata.derivativesmargin.spanfile.pointintime.clearingorg.exchange.*;
import com.christopherrons.refdataservice.derivativesmargin.model.SpanFile;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DerivativesMarginFileParser {

    private final XmlMapper xmlMapper = new XmlMapper();

    public DerivatesMarginRefData parseSpanFile(final String spanFileString) throws JsonProcessingException {
        final SpanFile spanFile = xmlMapper.readValue(spanFileString, SpanFile.class);
        return new DerivatesMarginRefData(createCombinedCommodities(spanFile));
    }

    private Map<String, CombinedCommodity> createCombinedCommodities(final SpanFile spanFile) {
        PointInTime pointInTime = spanFile.getPointInTime();
        ClearingOrg clearingOrg = pointInTime.getClearingOrg();
        List<Exchange> exchanges = clearingOrg.getExchanges();
        var productFamilyIdToProducts = mapProductFamilies(exchanges);
        Map<String, CombinedCommodity> productIdToCombinedCommodity = new HashMap<>();
        for (CcDef ccDef : clearingOrg.getCcDef()) {
            CombinedCommodity combinedCommodity = new CombinedCommodity();
            productIdToCombinedCommodity.put(ccDef.getCombinedCommodityId(), combinedCommodity);
            for (PfLink pfLink : ccDef.getPfLink()) {
                List<Product> products = productFamilyIdToProducts.get(pfLink.getPfId());
                for (Product product: products) {
                    combinedCommodity.addProduct(product);
                    productIdToCombinedCommodity.put(product.getProductId(), combinedCommodity);
                }
            }
        }
        return productIdToCombinedCommodity;
    }

    private Map<String, List<Product>> mapProductFamilies(final List<Exchange> exchanges) {
        Map<String, List<Product>> productFamilyIdToProducts = new HashMap<>();
        for (Exchange exchange : exchanges) {
            for (FutPf futureProductFamily : exchange.getFutPf()) {
                for (Fut future : futureProductFamily.getFut()) {
                    productFamilyIdToProducts.computeIfAbsent(futureProductFamily.getPfId(), k -> new ArrayList<>()).add(future);
                }
            }

            for (PhyPf physicalProductFamily : exchange.getPhyPf()) {
                for (Phy physical : physicalProductFamily.getPhy()) {
                    productFamilyIdToProducts.computeIfAbsent(physicalProductFamily.getPfId(), k -> new ArrayList<>()).add(physical);
                }
            }

            for (OofPf optionProductFamily : exchange.getOofPf()) {
                for (Series series : optionProductFamily.getSeries()) {
                    for (Opt option : series.getOpt()) {
                        productFamilyIdToProducts.computeIfAbsent(optionProductFamily.getPfId(), k -> new ArrayList<>()).add(option);
                    }
                }
            }
        }

        return productFamilyIdToProducts;
    }
}
