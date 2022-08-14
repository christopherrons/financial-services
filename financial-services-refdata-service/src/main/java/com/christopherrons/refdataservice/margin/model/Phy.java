package com.christopherrons.refdataservice.margin.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class Phy {

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
}
