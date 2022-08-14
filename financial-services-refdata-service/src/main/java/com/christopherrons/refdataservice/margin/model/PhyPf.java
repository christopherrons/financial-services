package com.christopherrons.refdataservice.margin.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.List;

public class PhyPf {

    @JacksonXmlProperty
    private String pfId;

    @JacksonXmlProperty
    private String pfCode;

    @JacksonXmlProperty
    private String name;

    @JacksonXmlProperty
    private String currency;

    @JacksonXmlProperty
    private double cvf;

    @JacksonXmlProperty
    private double priceDl;

    @JacksonXmlProperty
    private String priceFmt;

    @JacksonXmlProperty
    private String valueMeth;

    @JacksonXmlProperty
    private String priceMeth;

    @JacksonXmlProperty
    private String setlMeth;

    @JacksonXmlProperty
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<Phy> phy;

}
