package com.christopherrons.common.model.refdata.derivativesmargin.spanfile.pointintime.clearingorg.exchange;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.stream.IntStream;

public class Phy implements Product {

    @JacksonXmlProperty
    private String cId;

    @JacksonXmlProperty
    private double d;

    @JacksonXmlProperty
    private double v;

    @JacksonXmlProperty
    private double cvf;

    @JacksonXmlProperty
    private double val;

    @JacksonXmlProperty
    private double sc;

    @Override
    public String getProductId() {
        return cId;
    }

    @Override
    public RiskArray getRiskArray() {
        return null;
    }

}
