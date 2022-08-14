package com.christopherrons.common.model.refdata.derivativesmargin.spanfile.pointintime.clearingorg.exchange;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.List;

public class OofPf {

    @JacksonXmlProperty
    private String pfId;

    @JacksonXmlProperty
    private String pfCode;

    @JacksonXmlProperty
    private String name;

    @JacksonXmlProperty
    private String exercise;

    @JacksonXmlProperty
    private String currency;

    @JacksonXmlProperty
    private double cvf;

    @JacksonXmlProperty
    private double priceDl;

    @JacksonXmlProperty
    private int strikeDl;

    @JacksonXmlProperty
    private String strikeFmt;

    @JacksonXmlProperty
    private double cab;

    @JacksonXmlProperty
    private String priceFmt;

    @JacksonXmlProperty
    private String valueMeth;

    @JacksonXmlProperty
    private String priceMeth;

    @JacksonXmlProperty
    private String priceModel;

    @JacksonXmlProperty
    private String setlMeth;

    @JacksonXmlProperty
    private UndPf undPf;

    @JacksonXmlProperty
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<Series> series;

    public String getPfId() {
        return pfId;
    }

    public List<Series> getSeries() {
        return series;
    }
}
