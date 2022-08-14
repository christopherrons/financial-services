package com.christopherrons.refdataservice.margin.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class RiskArray {

    @JacksonXmlProperty
    private int r;

    @JacksonXmlProperty
    @JacksonXmlElementWrapper(useWrapping = false)
    private double[] a;

    @JacksonXmlProperty
    private double d;

}
