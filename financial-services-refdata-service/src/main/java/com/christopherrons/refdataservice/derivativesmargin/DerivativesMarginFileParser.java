package com.christopherrons.refdataservice.derivativesmargin;

import com.christopherrons.common.model.refdata.derivativesmargin.DerivatesMarginRefData;
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
        return null;
    }

    private Map<String, List<Product>> mapProductFamilies(final Exchange exchange) {
        Map<String, List<Product>> productFamilyIdToProducts = new HashMap<>();
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

        return productFamilyIdToProducts;
    }
}
