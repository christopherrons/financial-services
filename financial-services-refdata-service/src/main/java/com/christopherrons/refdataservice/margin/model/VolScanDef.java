package com.christopherrons.refdataservice.margin.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class VolScanDef {

    @JacksonXmlProperty
    private double mult;

    @JacksonXmlProperty
    private double numerator;

    @JacksonXmlProperty
    private double denominator;
}
